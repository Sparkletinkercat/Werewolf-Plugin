package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

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
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
