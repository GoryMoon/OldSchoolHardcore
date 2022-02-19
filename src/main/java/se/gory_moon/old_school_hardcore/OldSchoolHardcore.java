package se.gory_moon.old_school_hardcore;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import se.gory_moon.old_school_hardcore.packets.Network;

@Mod(OldSchoolHardcore.MOD_ID)
public class OldSchoolHardcore {

    public static final String MOD_ID = "old_school_hardcore";
    public static final String PROTOCOL_VERSION = "1";

    public OldSchoolHardcore() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
    }

    private void setup(final FMLCommonSetupEvent event) {
        Network.register();
    }
}
