package net.lpcamors.firework_jetpack.items;

import net.lpcamors.firework_jetpack.capability.FireworkRocketJetpackModCapabilities;
import net.lpcamors.firework_jetpack.packets.FireworkRocketJetpackModNetwork;
import net.lpcamors.firework_jetpack.packets.ServerboundFireworkJumpPacket;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractJumpableItem extends Item implements Equipable {


    public AbstractJumpableItem(Properties p_41383_) {
        super(p_41383_);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static boolean isBroke(ItemStack itemStack){
        return itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1;
    }

    public static boolean canUseItem(ItemStack p_41141_, LivingEntity livingEntity) {
        return !isBroke(p_41141_) && ((AbstractJumpableItem)p_41141_.getItem()).canUse(livingEntity);
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41137_, @NotNull Player p_41138_, @NotNull InteractionHand p_41139_) {
        return this.swapWithEquipmentSlot(this, p_41137_, p_41138_, p_41139_);
    }
    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        if(canUseItem(stack, entity)) {
            if(entity instanceof Player player){
                player.getCapability(FireworkRocketJetpackModCapabilities.FIREWORK_ABILITY).ifPresent(fireworkAbilityCapabilty -> {
                    if(fireworkAbilityCapabilty.canUse(player.tickCount)) {
                        FireworkRocketJetpackModNetwork.sendToServer(new ServerboundFireworkJumpPacket(stack, EquipmentSlot.CHEST));
                        this.prepareJump(entity);
                    }
                });
            } else {
                this.prepareJump(entity);
            }
        }
        return false;
    }

    public void prepareJump(LivingEntity livingEntity){
        this.jump(livingEntity);
        livingEntity.level().playSound(livingEntity, livingEntity.getOnPos(), this.getJumpSound(), livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE, 1F, 0.90F);
    }
    public abstract void spawnServerSideParticles(ServerPlayer serverPlayer);
    public abstract void jump(LivingEntity livingEntity);
    public abstract boolean canUse(LivingEntity livingEntity);
    public abstract String getName();
    public abstract SoundEvent getJumpSound();

    public abstract ParticleOptions getJumpPartile();

}

