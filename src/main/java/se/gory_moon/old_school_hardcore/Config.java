package se.gory_moon.old_school_hardcore;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static final Common COMMON;
    public static final ForgeConfigSpec commonSpec;

    static {
        Pair<Common, ForgeConfigSpec> configSpecPairCommon = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = configSpecPairCommon.getRight();
        COMMON = configSpecPairCommon.getLeft();
    }

    public static class Common {

        public ForgeConfigSpec.BooleanValue enabled;
        public ForgeConfigSpec.BooleanValue forceSpectator;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Old School Hardcore configs")
                    .push("common");

            this.enabled = builder
                    .comment("If this mod should be enabled and delete the world on death")
                    .define("enabled", true);

            this.forceSpectator = builder
                    .comment("If gamemode changing should be blocked when the player enters the world as spectator after death")
                    .define("forceSpectator", true);

            builder.pop();
        }

    }

}
