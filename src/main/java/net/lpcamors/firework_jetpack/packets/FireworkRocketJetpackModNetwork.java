package net.lpcamors.firework_jetpack.packets;

import net.lpcamors.firework_jetpack.FireworkRocketJetpackModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;

public class FireworkRocketJetpackModNetwork {

    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }
    public static void register() {
        SimpleChannel net = ChannelBuilder
                .named(new ResourceLocation(FireworkRocketJetpackModMain.MODID, "messages"))
                .networkProtocolVersion(1)
                .clientAcceptedVersions((status, version)  -> true)
                .serverAcceptedVersions((status, version) -> true)
                .simpleChannel();


        INSTANCE = net;

        net.messageBuilder(ServerboundFireworkJumpPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .consumerMainThread(ServerboundFireworkJumpPacket::handle)
                .encoder(ServerboundFireworkJumpPacket::encode)
                .decoder(ServerboundFireworkJumpPacket::new).add();
    }


    public static <MSG> void sendToServer(MSG message){
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <MSG> void sendToClientPlayer(ServerPlayer serverPlayer, MSG message){
        INSTANCE.send(message, PacketDistributor.PLAYER.with(serverPlayer));
    }
}

