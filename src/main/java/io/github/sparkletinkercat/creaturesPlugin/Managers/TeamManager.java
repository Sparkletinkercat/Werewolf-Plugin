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
        file.updateFile("teams." + teamName + ".startingNumber", 0);
        file.updateFile("teams." + teamName + ".beaconType", "desecrated");
    }

    public class Team {
        private boolean isEnabled = false;
        private String teamName = null;
        private int startingNumber = 0;
        private String beaconType = null;

        public Team (String teamName) {
            this.teamName = teamName;
        }

        public Team (String teamName, boolean isEnabled) {
            this.teamName = teamName;
            this.isEnabled = isEnabled;
        }

        public Team (String teamName, boolean isEnabled, int startingNumber, String beaconType) {
            this.teamName = teamName;
            this.isEnabled = isEnabled;
            this.startingNumber = startingNumber;
            this.beaconType = beaconType;
        }

        public void setStartingNumber (int startingNumber) {this.startingNumber = startingNumber;}

        public boolean getIsEnabled () {return isEnabled;}
        public int getStartingNumber () {return startingNumber;}
        public String getTeamName () {return teamName;}
        public String getBeaconTypeName () {return beaconType;}
    }

    public List<Team> retrieveAllTeamsFromFile () {
        FileManager file = new FileManager(plugin, "teamSettings");
        ConfigurationSection section = file.returnSectionOfFile ("teams");


        List<Team> teams = new ArrayList<Team>();

        for (String name : section.getKeys(false)) {
            YamlConfiguration config = file.returnConfig();
            boolean isEnabled = config.getBoolean("teams." + name + ".enabled");
            int startingNumber = config.getInt("teams." + name + ".startingNumber");
            String beaconType = config.getString("teams." + name + ".beaconType");

            teams.add(this.new Team(name, isEnabled,startingNumber,beaconType));
        }
        return teams;
    }

    public List<Team> retrieveAllEnabledTeamsFromFile () {
        List<Team> teams = this.retrieveAllTeamsFromFile ();
        List<Team> enabledTeams = new ArrayList<Team>();

        for (Team team : teams) {
            if (team.getIsEnabled()) {enabledTeams.add(team);}
        }

        return teams;
    }
}
