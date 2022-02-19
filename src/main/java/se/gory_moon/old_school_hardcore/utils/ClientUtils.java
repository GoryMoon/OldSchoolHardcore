package se.gory_moon.old_school_hardcore.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;

public class ClientUtils {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final ClientUtils INSTANCE = new ClientUtils();

    @Nullable
    public String levelId = null;

    public void setPlayerHardcoreDead() {
        Minecraft instance = Minecraft.getInstance();
        if (instance.isLocalServer()) {
            IntegratedServer server = instance.getSingleplayerServer();
            if (server != null) {
                levelId = server.storageSource.getLevelId();
            }
        }
    }

    public void deleteWorld() {
        if (levelId == null)
            return;

        Minecraft instance = Minecraft.getInstance();
        LevelStorageSource levelSource = instance.getLevelSource();
        try {
            LevelStorageSource.LevelStorageAccess storageAccess = levelSource.createAccess(levelId);
            try {
                storageAccess.deleteLevel();
            } catch (Throwable throwable) {
                try {
                    storageAccess.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }

            storageAccess.close();
        } catch (IOException ioexception) {
            SystemToast.onWorldDeleteFailure(instance, levelId);
            LOGGER.error("Failed to delete world {}", levelId, ioexception);
        }
        this.levelId = null;
    }
}
