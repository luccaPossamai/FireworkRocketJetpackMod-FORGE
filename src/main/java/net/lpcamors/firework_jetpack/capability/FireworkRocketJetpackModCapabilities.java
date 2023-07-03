package net.lpcamors.firework_jetpack.capability;

import net.lpcamors.firework_jetpack.capability.firework_ability.FireworkAbilityCapabilty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FireworkRocketJetpackModCapabilities {


    public static final Capability<FireworkAbilityCapabilty> FIREWORK_ABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public void registerCap(RegisterCapabilitiesEvent event){
        event.register(FireworkAbilityCapabilty.class);
    }

}
