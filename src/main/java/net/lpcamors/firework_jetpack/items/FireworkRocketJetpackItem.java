package net.lpcamors.firework_jetpack.items;

import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.lpcamors.firework_jetpack.capability.FireworkRocketJetpackModCapabilities;
import net.lpcamors.firework_jetpack.packets.FireworkRocketJetpackModNetwork;
import net.lpcamors.firework_jetpack.packets.ServerboundBreakItemPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireworkRocketJetpackItem extends ArmorItem {

    private static final ArmorMaterial JETPACK_MATERIAL = new ArmorMaterial() {

        @Override
        public int getDurabilityForType(@NotNull Type p_266807_) { return 128; }

        @Override
        public int getDefenseForType(@NotNull Type p_267168_) { return 1; }

        @Override
        public int getEnchantmentValue() { return 15; }

        @Override
        public @NotNull SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_LEATHER; }

        @Override
        public @NotNull Ingredient getRepairIngredient() { return Ingredient.of(Tags.Items.GUNPOWDER); }

        @Override
        public @NotNull String getName() { return "firework_rocket_jetpack"; }

        @Override
        public float getToughness() { return 0;}

        @Override
        public float getKnockbackResistance() { return 0;}

    };

    public FireworkRocketJetpackItem(Properties p_40388_) {
        super(JETPACK_MATERIAL, Type.CHESTPLATE, p_40388_);
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
        return 128;
    }

    public static boolean canUse(ItemStack p_41141_) {
        return p_41141_.getDamageValue() < p_41141_.getMaxDamage() - 1;
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        if(canUse(stack) && !entity.onGround() && !entity.isInWater()) {
            if(entity instanceof Player player){
                player.getCapability(FireworkRocketJetpackModCapabilities.FIREWORK_ABILITY).ifPresent(fireworkAbilityCapabilty -> {
                    if(fireworkAbilityCapabilty.canUse(player.tickCount)) {
                        this.jump(entity);
                    }
                });
            } else {
                this.jump(entity);
            }
            stack.hurtAndBreak(1, entity, entity1 -> entity1.broadcastBreakEvent(EquipmentSlot.CHEST));
            FireworkRocketJetpackModNetwork.CHANNEL.sendToServer(new ServerboundBreakItemPacket(stack, EquipmentSlot.CHEST));
        }
        return false;
    }


    private void jump(LivingEntity entity){
        Vec3 dV = entity.getDeltaMovement().add(0, 1, 0);
        entity.setDeltaMovement(dV.x(), Math.max(0.2, Math.min(0.6, dV.y())), dV.z());
        this.addParticles(entity);
        this.playSound(entity);
    }
    private void addParticles(LivingEntity entity){
        for(double f = 0; f < 2 * Math.PI; f += 2e-1 * Math.PI) {
            entity.level().addParticle(ParticleTypes.FIREWORK, entity.getX() + 0.75 * Math.cos(f), entity.getY(), entity.getZ() + 0.75 * Math.sin(f), 0, 0, 0);
        }
    }
    private void playSound(LivingEntity entity){
        entity.level().playSound(entity, entity.getOnPos(), SoundEvents.FIREWORK_ROCKET_LAUNCH, entity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE, 1F, 0.90F);
    }
}
