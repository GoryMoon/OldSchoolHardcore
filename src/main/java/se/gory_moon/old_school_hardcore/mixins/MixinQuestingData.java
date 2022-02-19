package se.gory_moon.old_school_hardcore.mixins;

import hardcorequesting.common.forge.quests.QuestingData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.gory_moon.old_school_hardcore.utils.CommonUtils;
import se.gory_moon.old_school_hardcore.Config;

@Mixin(QuestingData.class)
public class MixinQuestingData {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;displayClientMessage(Lnet/minecraft/network/chat/Component;Z)V", remap = true), method = "outOfLives", remap = false, cancellable = true)
    private void outOfLives(Player player, CallbackInfo info) {
        if (Config.COMMON.enabled.get()) {
            info.cancel();

            CommonUtils.setPlayerHardcoreDead(player);
            CommonUtils.sendDeathPacket((ServerPlayer) player);
        }
    }

}
