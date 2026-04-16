package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;
//import io.github.sparkletinkercat.creaturesPlugin.Managers.*;

public class TeamManager {
    private final JavaPlugin plugin;
    private static List<Team> teamInformation = new ArrayList<Team> ();
    
    public TeamManager (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createNewTeam (String teamName,  Object value) {
        FileManager file = new FileManager(plugin, "teamSettings");
        file.updateFile("teams." + teamName + ".enabled", value);
        file.updateFile("teams." + teamName + ".startingNumber", 0);
        file.updateFile("teams." + teamName + ".beaconType", "desecrated");
        file.updateFile("teams." + teamName + ".beaconControlBonus", "MAX_HEALTH");
        file.updateFile("teams." + teamName + ".beaconControlBonusPerLevel", 1);
    }

    public class Team {
        private boolean isEnabled = false;
        private String teamName = null;
        private int startingNumber = 0;
        private String beaconType = null;
        private String beaconControlBonus = null;
        private double beaconControlBonusPerLevel = 0;

        public Team (String teamName) {
            this.teamName = teamName;
        }

        public Team (String teamName, boolean isEnabled) {
            this.teamName = teamName;
            this.isEnabled = isEnabled;
        }

        public Team (String teamName, boolean isEnabled, int startingNumber, String beaconType, String beaconControlBonus, double beaconControlBonusPerLevel) {
            this.teamName = teamName;
            this.isEnabled = isEnabled;
            this.startingNumber = startingNumber;
            this.beaconType = beaconType;
            this.beaconControlBonus = beaconControlBonus;
            this.beaconControlBonusPerLevel = beaconControlBonusPerLevel;
        }

        public void setStartingNumber (int startingNumber) {this.startingNumber = startingNumber;}

        public boolean getIsEnabled () {return isEnabled;}
        public int getStartingNumber () {return startingNumber;}
        public String getTeamName () {return teamName;}
        public String getBeaconTypeName () {return beaconType;}
        public String getAllBeaconsControlledBonus () {return beaconControlBonus;}
        public double getbeaconControlBonusPerLevel  () {return beaconControlBonusPerLevel; }
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
            String beaconControlBonus = config.getString("teams." + name + ".beaconControlBonus");
            double beaconControlBonusPerLevel = config.getDouble("teams." + name + ".beaconControlBonusPerLevel");

            Team team = this.new Team(name, isEnabled,startingNumber,beaconType,beaconControlBonus, beaconControlBonusPerLevel);
            teams.add(team);

            // Store teams in main class for later use. 
            TeamManager.teamInformation.add(team);

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

    public List<Team> retrieveAllTeams () {return teamInformation;}

    public Team retrieveTeamByName (String name) {
        name = name.toLowerCase();

        for (Team team : teamInformation) {
            if (team.getTeamName().toLowerCase().equals(name)) {return team;}
        }

        return null;
    }

    public static Team getTeamByName (String name) {
        name = name.toLowerCase();

        for (Team team : teamInformation) {
            if (team.getTeamName().toLowerCase().equals(name)) {return team;}
        }

        return null;
    }
}
