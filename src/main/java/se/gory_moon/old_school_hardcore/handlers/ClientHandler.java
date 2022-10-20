package se.gory_moon.old_school_hardcore.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.gory_moon.old_school_hardcore.Config;
import se.gory_moon.old_school_hardcore.OldSchoolHardcore;
import se.gory_moon.old_school_hardcore.utils.ClientUtils;
import se.gory_moon.old_school_hardcore.gui.HardcoreDeathScreen;

@Mod.EventBusSubscriber(modid = OldSchoolHardcore.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientHandler {

    @SubscribeEvent
    public static void onWorldJoin(LevelEvent.Load event) {
        ClientUtils.INSTANCE.levelId = null;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onScreenOpen(ScreenEvent.Opening event) {
        Screen screen = event.getScreen();
        if (screen instanceof DeathScreen deathScreen && !(screen instanceof HardcoreDeathScreen)) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && ClientUtils.INSTANCE.levelId != null && Config.COMMON.enabled.get()) {
                event.setNewScreen(new HardcoreDeathScreen(deathScreen.causeOfDeath));
            }
        } else if (screen instanceof TitleScreen && ClientUtils.INSTANCE.levelId != null) {
            ClientUtils.INSTANCE.deleteWorld();
        }
    }
}
