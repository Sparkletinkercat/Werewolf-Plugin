package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;

public class FileManager {
    private final JavaPlugin plugin;
    private File file;
    private YamlConfiguration config;
    private String fileName;

    public FileManager (JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = checkIfFileExists ();
        this.config = YamlConfiguration.loadConfiguration(this.file);
        
    }

    private File checkIfFileExists () {
        File file = new File(plugin.getDataFolder(), fileName + ".yml");

        if (!file.exists()) {
            plugin.getLogger().warning(fileName + ".yml does not exist!");
            return null;
        }
        return file;
    }

    public void updateFile (String path, Object value) {
        config.set(path, value);
        try {config.save(file);} 
        catch (IOException e) {e.printStackTrace();}
    }

    public ConfigurationSection returnSectionOfFile (String sectionName) {
        if (!config.contains(sectionName)) {return null;}
        ConfigurationSection section = config.getConfigurationSection(sectionName);

        return section;
    }

    public void removeSectionOfFile(String mainSection, String sectionName) {
        String path = mainSection + "." + sectionName;
        updateFile (path, null);
    }

    public void removeSectionOfFile(String section) {
        String path = section;
        updateFile (path, null);
    }

    public YamlConfiguration returnConfig () {return config;}

    public static void setupGameFiles (JavaPlugin plugin) {
        // Create Plugin Folder and required files
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {folder.mkdirs();}

        List<String> files = List.of(
            "beacons.yml",
            "teamSettings.yml",
            "settings.yml",
            "trackPotionEffects.yml",
            "players.yml"
        );

        for (String item : files) {plugin.saveResource(item, false); }
    }

    public HashMap<String, Object> retrieveAllSections () {
        File file = new File(plugin.getDataFolder(), fileName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        Set<String> keys = config.getKeys(true); // true = include nested
        HashMap<String, Object> settingData = new HashMap<String, Object>();

        for (String key : keys) {
            System.out.println(key);
            Object value = config.get(key); // <- change here
            System.out.println(value);
            settingData.put(key, value);
        }
        return settingData;
    }

    public File getFile () {return this.file;}
}
