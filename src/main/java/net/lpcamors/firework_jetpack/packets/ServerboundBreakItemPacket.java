package net.lpcamors.firework_jetpack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundBreakItemPacket {

    private final ItemStack item;
    private final EquipmentSlot slot;
    public ServerboundBreakItemPacket(ItemStack item, EquipmentSlot slot){
        this.item = item;
        this.slot = slot;
    }

    public ServerboundBreakItemPacket(FriendlyByteBuf buffer){
        this(buffer.readItem(), buffer.readEnum(EquipmentSlot.class));
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItemStack(this.item, false);
        buffer.writeEnum(this.slot);
    }

    public void handle(Supplier<NetworkEvent.Context> event) {
        event.get().enqueueWork(() -> {
            if(event.get().getSender() != null) {
                this.item.hurtAndBreak(1, event.get().getSender(), entity1 -> entity1.broadcastBreakEvent(this.slot));
            }
        });
        event.get().setPacketHandled(true);
    }

}
