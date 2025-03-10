package com.hexagram2021.fiahi.common.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public final class FIAHICommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> NEVER_FROZEN_FOODS;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> NEVER_ROTTEN_FOODS;

	public static final ForgeConfigSpec.BooleanValue ENABLE_FROZEN;
	public static final ForgeConfigSpec.BooleanValue ENABLE_ROTTEN;

	public static final ForgeConfigSpec.IntValue TEMPERATURE_CHECKER_INTERVAL;
	public static final ForgeConfigSpec.IntValue TEMPERATURE_BALANCE_RATE;

	public static final ForgeConfigSpec.DoubleValue FROZEN_SPEED_MULTIPLIER;
	public static final ForgeConfigSpec.DoubleValue ROTTEN_SPEED_MULTIPLIER;

	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> STABLE_TEMPERATURE_CONTAINERS;

	private FIAHICommonConfig() {}

	static {
		BUILDER.push("fiahi-common-config");
			NEVER_FROZEN_FOODS = BUILDER.comment("Which foods will never be frozen.")
					.defineList("NEVER_FROZEN_FOODS", List.of(
							new ResourceLocation("dried_kelp").toString()
					), o -> o instanceof String str && ResourceLocation.isValidResourceLocation(str));
			NEVER_ROTTEN_FOODS = BUILDER.comment("Which foods will never be rotten.")
					.defineList("NEVER_ROTTEN_FOODS", List.of(
							new ResourceLocation("golden_apple").toString(),
							new ResourceLocation("enchanted_golden_apple").toString(),
							new ResourceLocation("golden_carrot").toString(),
							new ResourceLocation("emeraldcraft", "golden_peach").toString(),
							new ResourceLocation("emeraldcraft", "agate_apple").toString(),
							new ResourceLocation("emeraldcraft", "jade_apple").toString()
					), o -> o instanceof String str && ResourceLocation.isValidResourceLocation(str));
			ENABLE_FROZEN = BUILDER.comment("If false, foods will never be frozen.")
					.define("ENABLE_FROZEN", true);
			ENABLE_ROTTEN = BUILDER.comment("If false, foods will never be rotten.")
					.define("ENABLE_ROTTEN", true);
			TEMPERATURE_CHECKER_INTERVAL = BUILDER.comment("How many ticks after a single check will it try again to modify the temperature of the food.")
					.defineInRange("TEMPERATURE_CHECKER_INTERVAL", 120, 1, 24000);
			TEMPERATURE_BALANCE_RATE = BUILDER.comment("When trying to modify the temperature of the food each time, how many difference will be applied.")
					.defineInRange("TEMPERATURE_BALANCE_RATE", 10, 1, 100);

			FROZEN_SPEED_MULTIPLIER = BUILDER.comment("How fast will a food item get frozen. The bigger, the faster.")
					.defineInRange("FROZEN_SPEED_MULTIPLIER", 1.0D, 0.01D, 100.0D);
			ROTTEN_SPEED_MULTIPLIER = BUILDER.comment("How fast will a food item get rotten. The bigger, the faster.")
					.defineInRange("ROTTEN_SPEED_MULTIPLIER", 0.75D, 0.01D, 100.0D);
			STABLE_TEMPERATURE_CONTAINERS = BUILDER.comment("A whitelist of containers. Food items in these block entities will never be affected by temperature.")
					.defineList("STABLE_TEMPERATURE_CONTAINERS", List.of(
							new ResourceLocation("cold_sweat", "boiler").toString(),
							new ResourceLocation("cold_sweat", "icebox").toString()
					), o -> o instanceof String str && ResourceLocation.isValidResourceLocation(str));
		BUILDER.pop();
		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
