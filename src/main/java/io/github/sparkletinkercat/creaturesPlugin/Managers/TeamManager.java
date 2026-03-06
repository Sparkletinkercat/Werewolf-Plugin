package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;
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

    public class Team {
        private boolean isEnabled = false;
        private String teamName = null;

        public Team (String teamName) {
            this.teamName = teamName;
        }

        public Team (String teamName, boolean isEnabled) {
            this.teamName = teamName;
            this.isEnabled = isEnabled;
        }

        public boolean returnIsEnabled () {return isEnabled;}
        public String returnTeamName () {return teamName;}
    }

    public List<Team> retrieveAllTeamsFromFile () {
        FileManager file = new FileManager(plugin, "teamSettings");
        ConfigurationSection section = file.returnSectionOfFile ("teams");


        List<Team> teams = new ArrayList<Team>();

        for (String name : section.getKeys(false)) {
            YamlConfiguration config = file.returnConfig();
            boolean isEnabled = config.getBoolean("teams." + name + ".enabled");

            teams.add(this.new Team(name, isEnabled));
        }
        return teams;
    }
}
