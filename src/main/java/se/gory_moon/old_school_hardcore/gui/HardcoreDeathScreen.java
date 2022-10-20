package se.gory_moon.old_school_hardcore.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import se.gory_moon.old_school_hardcore.utils.ClientUtils;

import javax.annotation.Nullable;
import java.util.List;

public class HardcoreDeathScreen extends DeathScreen {

    private int delayTicker;
    private final List<Button> exitButtons = Lists.newArrayList();

    public HardcoreDeathScreen(@Nullable Component pCauseOfDeath) {
        super(pCauseOfDeath, true);
    }

    @Override
    protected void init() {
        super.init();
        delayTicker = 0;

        if (minecraft != null) {
            clearWidgets();

            exitButtons.clear();
            exitButtons.add(this.addRenderableWidget(new Button(width / 2 - 100, height / 4 + 72, 200, 20, Component.translatable("deathScreen.spectate"), (button) -> {
                if (minecraft.player != null) {
                    minecraft.player.respawn();
                    minecraft.setScreen(null);
                }
            })));
            exitButtons.add(this.addRenderableWidget(new Button(width / 2 - 100, height / 4 + 96, 200, 20, Component.translatable("old_school_hardcore.deathScreen.delete"), (button) ->
                    this.exitToTitleScreen())));

            for (Button button : this.exitButtons) {
                button.active = false;
            }
        }
    }

    private void exitToTitleScreen() {
        if (minecraft != null) {
            if (minecraft.level != null) {
                minecraft.level.disconnect();
            }

            minecraft.clearLevel(new GenericDirtMessageScreen(Component.translatable("old_school_hardcore.menu.savingLevel")));
            minecraft.setScreen(new TitleScreen());

            ClientUtils.INSTANCE.deleteWorld();
        }
    }

    @Override
    public void tick() {
        super.tick();
        ++delayTicker;
        if (delayTicker == 20) {
            for(Button button : exitButtons) {
                button.active = true;
            }
        }
    }
}
