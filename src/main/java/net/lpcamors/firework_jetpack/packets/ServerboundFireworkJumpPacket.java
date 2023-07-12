package net.lpcamors.firework_jetpack.packets;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundFireworkJumpPacket {

    private final ItemStack item;
    private final EquipmentSlot slot;
    public ServerboundFireworkJumpPacket(ItemStack item, EquipmentSlot slot){
        this.item = item;
        this.slot = slot;
    }

    public ServerboundFireworkJumpPacket(FriendlyByteBuf buffer){
        this(buffer.readItem(), buffer.readEnum(EquipmentSlot.class));
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItemStack(this.item, false);
        buffer.writeEnum(this.slot);
    }

    public void handle(Supplier<NetworkEvent.Context> event) {
        event.get().enqueueWork(() -> {
            if(event.get() != null && event.get().getSender() != null) {
                ServerPlayer serverPlayer = event.get().getSender();
                serverPlayer.getItemBySlot(this.slot).hurtAndBreak(1, serverPlayer, entity1 -> entity1.broadcastBreakEvent(this.slot));
                for(double f = 0; f < 2 * Math.PI; f += 2e-1 * Math.PI) {
                    ((ServerLevel) serverPlayer.level()).sendParticles(ParticleTypes.FIREWORK, serverPlayer.getX() + 0.75 * Math.cos(f), serverPlayer.getY(), serverPlayer.getZ() + 0.75 * Math.sin(f), 1,0, 0, 0, 0);
                }
                serverPlayer.level().playSound(serverPlayer, serverPlayer.getOnPos(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 1F, 0.90F);
            }
        });
        event.get().setPacketHandled(true);
    }

}
