package net.lpcamors.firework_jetpack.packets;

import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class FireworkRocketJetpackModNetwork {

    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }
    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(FireworkRocketJetpackModMain.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();


        INSTANCE = net;

        net.messageBuilder(ServerboundFireworkJumpPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .consumerMainThread(ServerboundFireworkJumpPacket::handle)
                .encoder(ServerboundFireworkJumpPacket::encode)
                .decoder(ServerboundFireworkJumpPacket::new).add();
    }


    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(ServerPlayer serverPlayer, MSG message){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }
}

