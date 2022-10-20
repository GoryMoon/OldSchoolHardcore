package se.gory_moon.old_school_hardcore.packets;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import se.gory_moon.old_school_hardcore.OldSchoolHardcore;

public class Network {

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(OldSchoolHardcore.MOD_ID, "main"),
            () -> OldSchoolHardcore.PROTOCOL_VERSION,
            OldSchoolHardcore.PROTOCOL_VERSION::equals,
            OldSchoolHardcore.PROTOCOL_VERSION::equals
    );

    public static void register() {
        CHANNEL.messageBuilder(SendDeathPacket.class, 0, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SendDeathPacket::encode)
                .decoder(SendDeathPacket::new)
                .consumerMainThread(SendDeathPacket::handle)
                .add();
    }

}
