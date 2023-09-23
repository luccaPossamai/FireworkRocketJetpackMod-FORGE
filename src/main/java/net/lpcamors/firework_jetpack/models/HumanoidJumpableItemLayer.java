package net.lpcamors.firework_jetpack.models;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.lpcamors.firework_jetpack.items.AbstractJumpableItem;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

public class HumanoidJumpableItemLayer <T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
    private final M outerModel;

    public HumanoidJumpableItemLayer(RenderLayerParent<T, M> p_267286_, M p_267150_) {
        super(p_267286_);
        this.outerModel = p_267150_;
    }

    @Override
    public void render(@NotNull PoseStack p_117096_, @NotNull MultiBufferSource p_117097_, int p_117098_, @NotNull T p_117099_, float p_117100_, float p_117101_, float p_117102_, float p_117103_, float p_117104_, float p_117105_) {
        this.renderEquipedItem(p_117096_, p_117097_, p_117099_, p_117098_, this.outerModel);
    }

    private void renderEquipedItem(PoseStack p_117119_, MultiBufferSource p_117120_, T p_117121_, int p_117123_, M p_117124_) {
        EquipmentSlot slot = EquipmentSlot.CHEST;
        ItemStack itemstack = p_117121_.getItemBySlot(slot);
        Item $$9 = itemstack.getItem();
        if ($$9 instanceof AbstractJumpableItem jumpableItem) {
            if (jumpableItem.getEquipmentSlot() == slot) {
                this.getParentModel().copyPropertiesTo(p_117124_);
                this.setPartVisibility(p_117124_, slot);
                net.minecraft.client.model.Model model = getArmorModelHook(p_117121_, itemstack, slot, p_117124_);
                this.renderModel(p_117119_, p_117120_, p_117123_, model,  1.0F, 1.0F, 1.0F, this.getArmorResource(p_117121_, itemstack, slot, null));
                if (itemstack.hasFoil()) {
                    this.renderGlint(p_117119_, p_117120_, p_117123_, model);
                }

            }
        }
    }

    protected void setPartVisibility(M p_117126_, EquipmentSlot p_117127_) {
        p_117126_.setAllVisible(false);
        switch (p_117127_) {
            case HEAD -> {
                p_117126_.head.visible = true;
                p_117126_.hat.visible = true;
            }
            case CHEST -> {
                p_117126_.body.visible = true;
                p_117126_.rightArm.visible = true;
                p_117126_.leftArm.visible = true;
            }
            case LEGS -> {
                p_117126_.body.visible = true;
                p_117126_.rightLeg.visible = true;
                p_117126_.leftLeg.visible = true;
            }
            case FEET -> {
                p_117126_.rightLeg.visible = true;
                p_117126_.leftLeg.visible = true;
            }
        }

    }

    private void renderModel(PoseStack p_289664_, MultiBufferSource p_289689_, int p_289681_, net.minecraft.client.model.Model p_289658_, float p_289678_, float p_289674_, float p_289693_, ResourceLocation armorResource) {
        VertexConsumer vertexconsumer = p_289689_.getBuffer(RenderType.armorCutoutNoCull(armorResource));
        p_289658_.renderToBuffer(p_289664_, vertexconsumer, p_289681_, OverlayTexture.NO_OVERLAY, p_289678_, p_289674_, p_289693_, 1.0F);
    }
    private void renderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, net.minecraft.client.model.Model p_289659_) {
        p_289659_.renderToBuffer(p_289673_, p_289654_.getBuffer(RenderType.armorEntityGlint()), p_289649_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private boolean usesInnerModel(EquipmentSlot p_117129_) {
        return p_117129_ == EquipmentSlot.LEGS;
    }

    public ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        AbstractJumpableItem item = (AbstractJumpableItem)stack.getItem();
        String texture = item.getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, 1, type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));

        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
        }

        return resourcelocation;
    }
    protected net.minecraft.client.model.Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, M model) {
        return net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
    }


}
