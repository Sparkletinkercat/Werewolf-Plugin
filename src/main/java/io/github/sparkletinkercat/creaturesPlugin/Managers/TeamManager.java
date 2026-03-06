package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.plugin.java.JavaPlugin;
//import io.github.sparkletinkercat.creaturesPlugin.Managers.*;

public class TeamManager {
    private final JavaPlugin plugin;
    
    public TeamManager (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createNewTeam (String teamName,  Object value) {
        FileManager file = new FileManager(plugin, "teamSettings");
        file.updateFile("teams." + teamName + ".enabled", value);
    }
}
