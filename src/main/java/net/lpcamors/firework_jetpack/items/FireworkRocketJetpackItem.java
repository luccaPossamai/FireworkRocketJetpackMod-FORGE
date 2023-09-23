package net.lpcamors.firework_jetpack.items;

import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireworkRocketJetpackItem extends AbstractJumpableItem {

    public FireworkRocketJetpackItem(Properties p_40388_) {
        super(p_40388_);
    }



    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String typeS = type == null ? "" : "_overlay";
        if (slot.getIndex() == 1) {
            return new ResourceLocation(FireworkRocketJetpackModMain.MODID, "textures/models/armor/firework_rocket_jetpack_layer_2"+typeS+".png").toString();
        }
        return new ResourceLocation(FireworkRocketJetpackModMain.MODID, "textures/models/armor/firework_rocket_jetpack_layer_1"+typeS+".png").toString();
    }


    @Override
    public int getMaxDamage(ItemStack stack) {
        return 256;
    }


    @Override
    public void jump(LivingEntity entity){
        Vec3 dV = entity.getDeltaMovement().add(0, 1, 0);
        entity.setDeltaMovement(dV.x(), Math.max(0.2, Math.min(0.6, dV.y())), dV.z());
    }

    @Override
    public boolean canUse(LivingEntity livingEntity) {
        return !livingEntity.onGround() && !livingEntity.isInWater();
    }

    @Override
    public String getName() {
        return "firework";
    }

    @Override
    public SoundEvent getJumpSound() {
        return SoundEvents.FIREWORK_ROCKET_LAUNCH;
    }

    @Override
    public ParticleOptions getJumpPartile() {
        return ParticleTypes.FIREWORK;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public void spawnServerSideParticles(ServerPlayer serverPlayer) {
        for(double f = 0; f < 2 * Math.PI; f += 2e-1 * Math.PI) {
            ((ServerLevel) serverPlayer.level()).sendParticles(this.getJumpPartile(), serverPlayer.getX() + 0.75 * Math.cos(f), serverPlayer.getY(), serverPlayer.getZ() + 0.75 * Math.sin(f), 1,0, 0, 0, 0);
        }
    }
}
