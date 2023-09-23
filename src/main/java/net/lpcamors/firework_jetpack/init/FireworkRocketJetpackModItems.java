package net.lpcamors.firework_jetpack.init;

import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.lpcamors.firework_jetpack.items.BubblePropulsionJetPackItem;
import net.lpcamors.firework_jetpack.items.FireworkRocketJetpackItem;
import net.lpcamors.firework_jetpack.items.NetheriteFireworkRocketJetpackItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireworkRocketJetpackModItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FireworkRocketJetpackModMain.MODID);
    public static void register(IEventBus bus){ ITEMS.register(bus); }

    public static final RegistryObject<Item> FIREWORK_JETPACK = ITEMS.register("firework_rocket_jetpack", () -> new FireworkRocketJetpackItem(new Item.Properties().defaultDurability(256)));
    public static final RegistryObject<Item> BUBBLE_PROPULSION_JETPACK = ITEMS.register("bubble_propulsion_jetpack", () -> new BubblePropulsionJetPackItem(new Item.Properties().defaultDurability(384)));
    public static final RegistryObject<Item> STRONG_FIREWORK_JETPACK = ITEMS.register("netherite_firework_rocket_jetpack", () -> new NetheriteFireworkRocketJetpackItem(new Item.Properties().defaultDurability(512)));

}
