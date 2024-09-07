package be.convoy.create.dupeevent;

import net.fabricmc.api.ModInitializer;

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
    public static Map<String, Map<String, String>> configMap;

	@Override
	public void onInitialize() {
		ConfigManager configManager = new ConfigManager(configFileLocation, configFileName, defaultConfigFilePath);
	}
}