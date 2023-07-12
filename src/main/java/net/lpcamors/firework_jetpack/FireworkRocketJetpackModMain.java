package net.lpcamors.firework_jetpack;

import net.lpcamors.firework_jetpack.init.FireworkRocketJetpackModItems;
import net.lpcamors.firework_jetpack.packets.FireworkRocketJetpackModNetwork;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FireworkRocketJetpackModMain.MODID)
public class FireworkRocketJetpackModMain {

    public static final String MODID = "firework_rocket_jetpack";


    public FireworkRocketJetpackModMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FireworkRocketJetpackModItems.register(modEventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }


    private void commonSetup(final FMLCommonSetupEvent event){
        event.enqueueWork(FireworkRocketJetpackModNetwork::register);
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)){
            event.accept(FireworkRocketJetpackModItems.FIREWORK_JETPACK);
        }
    }



}
