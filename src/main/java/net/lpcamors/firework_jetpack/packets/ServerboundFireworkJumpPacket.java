package net.lpcamors.firework_jetpack.packets;

import net.lpcamors.firework_jetpack.items.AbstractJumpableItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.EventNetworkChannel;

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

    public void handle(CustomPayloadEvent.Context event) {
        event.enqueueWork(() -> {
            if(event.getSender() != null) {
                ServerPlayer serverPlayer = event.getSender();
                serverPlayer.getItemBySlot(this.slot).hurtAndBreak(1, serverPlayer, entity1 -> entity1.broadcastBreakEvent(this.slot));
                ((AbstractJumpableItem) item.getItem()).spawnServerSideParticles(serverPlayer);
                serverPlayer.level().playSound(serverPlayer, serverPlayer.getOnPos(), ((AbstractJumpableItem) item.getItem()).getJumpSound(), SoundSource.PLAYERS, 1F, 0.90F);
            }
        });
        event.setPacketHandled(true);
    }

}
