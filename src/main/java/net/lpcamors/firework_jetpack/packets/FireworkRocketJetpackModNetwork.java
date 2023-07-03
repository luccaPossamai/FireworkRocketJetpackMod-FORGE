package net.lpcamors.firework_jetpack.packets;

import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class FireworkRocketJetpackModNetwork {

    public static final String VERSION = "0.1";

    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(FireworkRocketJetpackModMain.MODID, "network"),() -> VERSION,
                    version -> version.equals(VERSION), version -> version.equals(VERSION));

    public static void init(){
        int index = 0;
        CHANNEL.messageBuilder(ServerboundBreakItemPacket.class, index++ , NetworkDirection.PLAY_TO_SERVER)
                .consumerMainThread(ServerboundBreakItemPacket::handle)
                .encoder(ServerboundBreakItemPacket::encode)
                .decoder(ServerboundBreakItemPacket::new).add();

    }
}

