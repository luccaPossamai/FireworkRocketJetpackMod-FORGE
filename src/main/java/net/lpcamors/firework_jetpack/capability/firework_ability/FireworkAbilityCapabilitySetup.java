package net.lpcamors.firework_jetpack.capability.firework_ability;

import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.lpcamors.firework_jetpack.capability.FireworkRocketJetpackModCapabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FireworkAbilityCapabilitySetup {

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(FireworkRocketJetpackModMain.MODID, "firework_ability"), new FireworkAbilityCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void ticking(TickEvent.PlayerTickEvent event){
        if(event.player.onGround()){
            event.player.getCapability(FireworkRocketJetpackModCapabilities.FIREWORK_ABILITY).ifPresent(fireworkAbilityCapabilty -> {
                fireworkAbilityCapabilty.setLastTick(event.player.tickCount - FireworkAbilityCapabilty.TIME);
            });
        }
    }

}
