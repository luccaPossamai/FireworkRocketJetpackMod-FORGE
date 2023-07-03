package net.lpcamors.firework_jetpack.init;

import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.lpcamors.firework_jetpack.items.FireworkRocketJetpackItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireworkRocketJetpackModItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FireworkRocketJetpackModMain.MODID);
    public static void register(IEventBus bus){ ITEMS.register(bus); }

    public static final RegistryObject<Item> FIREWORK_JETPACK = ITEMS.register("firework_rocket_jetpack", () -> new FireworkRocketJetpackItem(new Item.Properties().defaultDurability(128)));

}
