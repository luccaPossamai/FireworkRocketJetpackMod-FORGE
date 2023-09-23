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

public class BubblePropulsionJetPackItem extends AbstractJumpableItem {

    public BubblePropulsionJetPackItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String typeS = type == null ? "" : "_overlay";
        if (slot.getIndex() == 1) {
            return new ResourceLocation(FireworkRocketJetpackModMain.MODID, "textures/models/armor/bubble_propulsion_jetpack_layer_2"+typeS+".png").toString();
        }
        return new ResourceLocation(FireworkRocketJetpackModMain.MODID, "textures/models/armor/bubble_propulsion_jetpack_layer_1"+typeS+".png").toString();
    }

    @Override
    public void jump(LivingEntity livingEntity) {
        Vec3 dV = livingEntity.getDeltaMovement().multiply(4, 4, 4);
        livingEntity.setDeltaMovement(dV);
    }

    @Override
    public boolean canUse(LivingEntity livingEntity) {
        return livingEntity.isInWater() && livingEntity.isSwimming();
    }

    @Override
    public String getName() {
        return "bubble_propulsion";
    }

    @Override
    public SoundEvent getJumpSound() {
        return SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE;
    }

    @Override
    public void spawnServerSideParticles(ServerPlayer serverPlayer) {
        for(int i = 0; i < 20; i += 1) {
            ((ServerLevel) serverPlayer.level()).sendParticles(this.getJumpPartile(), serverPlayer.getX() + (serverPlayer.getRandom().nextFloat() - 0.5) * 0.5, serverPlayer.getY() + 0.2 + (serverPlayer.getRandom().nextFloat() - 0.5) * 0.5, serverPlayer.getZ() + (serverPlayer.getRandom().nextFloat() - 0.5) * 0.5, 1,0, 0, 0, 0);
        }
    }

    @Override
    public ParticleOptions getJumpPartile() {
        return ParticleTypes.BUBBLE;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }
}
