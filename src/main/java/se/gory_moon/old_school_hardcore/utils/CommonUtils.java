package se.gory_moon.old_school_hardcore.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;
import se.gory_moon.old_school_hardcore.handlers.HQMUtils;
import se.gory_moon.old_school_hardcore.packets.Network;
import se.gory_moon.old_school_hardcore.packets.SendDeathPacket;

public class CommonUtils {

    public static boolean isHQMLoaded() {
        return ModList.get().isLoaded("hardcorequesting") && HQMUtils.isHQMHardcoreActive();
    }

    public static boolean isPlayerHardcoreDead(Player player) {
        return player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getBoolean("hardcore_death");
    }

    public static void setPlayerHardcoreDead(Player player) {
        CompoundTag compound = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        compound.putBoolean("hardcore_death", true);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, compound);
    }

    public static void sendDeathPacket(ServerPlayer player) {
        Network.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SendDeathPacket());
    }
}
