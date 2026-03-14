package me.duncanruns.teamonlylocatorbar;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeamOnlyLocatorBar implements ModInitializer {
	public static final String MOD_ID = "team-only-locator-bar";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final boolean SPECTATORS_IGNORE_TEAMS = true;

	@Override
	public void onInitialize() {
		// todo maybe config (spectators and perhaps something else if I think of it)
	}
}