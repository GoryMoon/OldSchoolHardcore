package se.gory_moon.old_school_hardcore.handlers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.gory_moon.old_school_hardcore.Config;
import se.gory_moon.old_school_hardcore.OldSchoolHardcore;
import se.gory_moon.old_school_hardcore.utils.CommonUtils;

@Mod.EventBusSubscriber(modid = OldSchoolHardcore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonHandler {

    private static boolean isEnabled() {
        return Config.COMMON.enabled.get();
    }

    private static boolean allowGameModeChange() {
        return !isEnabled() || !Config.COMMON.forceSpectator.get();
    }

    private static boolean checkPlayerDeath(Entity entity) {
        return entity instanceof ServerPlayer player && player.server.isSingleplayer() && CommonUtils.isPlayerHardcoreDead(player);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void playerDied(LivingDeathEvent event) {

        // Vanilla hardcore handling
        if (isEnabled() && !CommonUtils.isHQMLoaded() &&
                event.getEntity() instanceof ServerPlayer player &&
                player.server.isSingleplayer() && player.server.isHardcore()) {
            CommonUtils.setPlayerHardcoreDead(player);
            CommonUtils.sendDeathPacket(player);
        }
    }

    @SubscribeEvent
    public static void handlePlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        ServerPlayer player = (ServerPlayer) event.getEntity();
        if (isEnabled() && checkPlayerDeath(player)) {
            CommonUtils.sendDeathPacket(player);

            player.setGameMode(GameType.SPECTATOR);
            player.level().getGameRules().getRule(GameRules.RULE_SPECTATORSGENERATECHUNKS).set(false, player.server);
        }
    }

    @SubscribeEvent
    public static void handlePlayerJoinWorld(EntityJoinLevelEvent event) {
        if (allowGameModeChange() || !event.loadedFromDisk())
            return;

        if (!event.getLevel().isClientSide() && checkPlayerDeath(event.getEntity())) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            CommonUtils.sendDeathPacket(player);

            if (player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
                player.setGameMode(GameType.SPECTATOR);
            }
        }
    }

    @SubscribeEvent
    public static void handleGameModeChange(PlayerEvent.PlayerChangeGameModeEvent event) {
        if (event.getNewGameMode() == GameType.SPECTATOR)
            return;
        if (allowGameModeChange())
            return;

        if (!event.getEntity().level().isClientSide() && checkPlayerDeath(event.getEntity())) {
            ServerPlayer player = (ServerPlayer) event.getEntity();

            CommonUtils.sendDeathPacket(player);
            player.setGameMode(GameType.SPECTATOR);
            player.level().getGameRules().getRule(GameRules.RULE_SPECTATORSGENERATECHUNKS).set(false, player.server);

            event.setCanceled(true);
        }
    }

}
