package net.lpcamors.firework_jetpack.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NetheriteFireworkRocketJetpackItem extends AbstractJumpableItem{


    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public NetheriteFireworkRocketJetpackItem(Properties p_41383_) {
        super(p_41383_);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ForgeMod.ENTITY_GRAVITY.get(), new AttributeModifier(UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), "Entity Gravity modifier", -0.25, AttributeModifier.Operation.MULTIPLY_TOTAL));
        this.defaultModifiers = builder.build();
    }

    @Override
    public void spawnServerSideParticles(ServerPlayer serverPlayer) {
        for(double f = 0; f < 2 * Math.PI; f += 1e-1 * Math.PI) {
            ((ServerLevel) serverPlayer.level()).sendParticles(this.getJumpPartile(), serverPlayer.getX() + 0.5 * Math.cos(f), serverPlayer.getY(), serverPlayer.getZ() + 0.5 * Math.sin(f), 1,0, 0, 0, 0);
            ((ServerLevel) serverPlayer.level()).sendParticles(this.getJumpPartile(), serverPlayer.getX() + 1 * Math.cos(f), serverPlayer.getY(), serverPlayer.getZ() + 1 * Math.sin(f), 1,0, 0, 0, 0);

        }
    }

    @Override
    public void jump(LivingEntity livingEntity) {
        Vec3 dV = livingEntity.getDeltaMovement().add(0, 1, 0);
        float f = livingEntity instanceof Player player && player.isCrouching() ? 0.5F : 1F;
        livingEntity.setDeltaMovement(dV.x(), f * Math.max(0.2, Math.min(0.8, dV.y())), dV.z());
    }

    @Override
    public boolean canUse(LivingEntity livingEntity) {
        return !livingEntity.onGround() && !livingEntity.isInWater();
    }
    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String typeS = type == null ? "" : "_overlay";
        if (slot.getIndex() == 1) {
            return new ResourceLocation(FireworkRocketJetpackModMain.MODID, "textures/models/armor/netherite_firework_rocket_jetpack_layer_2"+typeS+".png").toString();
        }
        return new ResourceLocation(FireworkRocketJetpackModMain.MODID, "textures/models/armor/netherite_firework_rocket_jetpack_layer_1"+typeS+".png").toString();
    }
    @Override
    public String getName() {
        return "netherite_firework";
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
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot p_40390_) {
        return p_40390_ == this.getEquipmentSlot() ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_40390_);
    }
}
