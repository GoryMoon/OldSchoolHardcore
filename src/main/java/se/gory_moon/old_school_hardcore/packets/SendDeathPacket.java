package se.gory_moon.old_school_hardcore.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import se.gory_moon.old_school_hardcore.utils.ClientUtils;

import java.util.function.Supplier;

public class SendDeathPacket {

    public SendDeathPacket() {}

    public SendDeathPacket(FriendlyByteBuf buf) {}

    public void encode(FriendlyByteBuf buf) {}

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //noinspection Convert2MethodRef
            ClientUtils.INSTANCE.setPlayerHardcoreDead();
        });
        ctx.get().setPacketHandled(true);
    }
}