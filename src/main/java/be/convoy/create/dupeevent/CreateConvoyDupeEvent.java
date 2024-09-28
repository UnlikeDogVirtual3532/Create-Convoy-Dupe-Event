package be.convoy.create.dupeevent;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.convoy.create.dupeevent.config.ConfigManager;

public class CreateConvoyDupeEvent implements ModInitializer {
	public static final String MOD_ID = "create-convoy-dupe-event";
	public static final String MOD_NAME = "Create Convoy Dupe Event";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static String configFileLocation = "config/";
    public static String configFileName = "DupeEvent.yaml";
    public static String defaultConfigFilePath = "assets/create-convoy-dupe-event/default_config.yaml";
    public static Map<String, Object> configMap;

	@Override
	public void onInitialize() {
		ConfigManager configManager = new ConfigManager(configFileLocation, configFileName, defaultConfigFilePath);

		ServerLifecycleEvents.SERVER_STARTING.register((MinecraftServer server) -> {
			try {
				configMap = configManager.getConfig();
			} catch (Exception e) {
				configMap = configManager.getDefaultConfig();
			}
	
			if (configMap == null) {configMap = configManager.getDefaultConfig();}
		});

		ServerLifecycleEvents.BEFORE_SAVE.register((MinecraftServer server, boolean flush, boolean force) -> {
			configManager.saveConfig(configMap);
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAcces, enviroment) -> {
			CommandRegistry.register(dispatcher);
		});
	}
}