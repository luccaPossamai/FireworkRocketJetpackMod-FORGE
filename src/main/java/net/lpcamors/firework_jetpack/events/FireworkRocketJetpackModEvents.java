package net.lpcamors.firework_jetpack.events;


import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.lpcamors.firework_jetpack.models.HumanoidJumpableItemLayer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FireworkRocketJetpackModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FireworkRocketJetpackModEvents {
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event){
        LivingEntityRenderer<Player, HumanoidModel<Player>> renderer = event.getSkin(PlayerSkin.Model.WIDE);
        if(renderer != null) {
            renderer.addLayer(new HumanoidJumpableItemLayer<>(renderer, new HumanoidArmorModel<>(event.getContext().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
        }
        renderer = event.getSkin(PlayerSkin.Model.SLIM);
        if(renderer != null) {
            renderer.addLayer(new HumanoidJumpableItemLayer<>(renderer, new HumanoidArmorModel<>(event.getContext().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
        }

        LivingEntityRenderer<ArmorStand, HumanoidModel<ArmorStand>> renderer1 = event.getRenderer(EntityType.ARMOR_STAND);
        if(renderer1 != null) {
            renderer1.addLayer(new HumanoidJumpableItemLayer<>(renderer1, new HumanoidArmorModel<>(event.getContext().bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR))));
        }
    }

}
