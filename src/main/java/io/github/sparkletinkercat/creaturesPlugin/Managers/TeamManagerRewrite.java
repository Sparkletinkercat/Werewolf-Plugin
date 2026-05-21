package io.github.sparkletinkercat.creaturesPlugin.Managers;

import io.github.sparkletinkercat.creaturesPlugin.Implementations.*;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;

public class TeamManagerRewrite extends TypeHandler<TeamManagerRewrite> {
    private static List<TeamManagerRewrite> teamInformation = new ArrayList<TeamManagerRewrite> ();

    private boolean isEnabled = false;
    private String teamName = null;
    private int startingNumber = 0;
    private String beaconType = null;
    private String beaconControlBonus = "MAX_HEALTH";
    private double beaconControlBonusPerLevel = 0;

    public TeamManagerRewrite () {}

    public TeamManagerRewrite (String teamName, boolean isEnabled) {
        this.teamName = teamName;
        this.isEnabled = isEnabled;
    }

    public TeamManagerRewrite (String teamName, boolean isEnabled, int startingNumber, String beaconType, String beaconControlBonus, double beaconControlBonusPerLevel) {
        this.teamName = teamName;
        this.isEnabled = isEnabled;
        this.startingNumber = startingNumber;
        this.beaconType = beaconType;
        this.beaconControlBonus = beaconControlBonus;
        this.beaconControlBonusPerLevel = beaconControlBonusPerLevel;
    }

    public void setTeamName (String teamName) {this.teamName = teamName;}

    public boolean getIsEnabled () {return isEnabled;}
    public int getStartingNumber () {return startingNumber;}
    public String getTeamName () {return teamName;}
    public String getBeaconTypeName () {return beaconType;}
    public String getAllBeaconsControlledBonus () {return beaconControlBonus;}
    public double getbeaconControlBonusPerLevel  () {return beaconControlBonusPerLevel; }

    @Override
    protected String getSectionName () {return teamName;} 

    @Override
    protected String getFileName () {return "teamSettings";}

    @Override
    public List<TypeHandler<?>> retrieveAllAspectsFromFile () {
        List<TypeHandler<?>> teams = super.retrieveAllAspectsFromFile();
        List<TeamManagerRewrite> teamInformation = new ArrayList<>();

        for (TypeHandler<?> handler : teams) {
            if (handler instanceof TeamManagerRewrite team) {
                teamInformation.add(team);
            }
        }
        return teams;
    }

    public List<TeamManagerRewrite> retrieveAllEnabledTeamsFromFile () {
        this.retrieveAllAspectsFromFile();
        List<TeamManagerRewrite> enabledTeams = new ArrayList<TeamManagerRewrite>();
        for (TeamManagerRewrite team : teamInformation) {
            if (team.getIsEnabled() == true) {
                enabledTeams.add(team);
            }
        }
        return enabledTeams;
    }

    public List<TeamManagerRewrite> retrieveAllTeams () {return teamInformation;}

    public TeamManagerRewrite retrieveTeamByName (String name) {
        this.retrieveAllAspectsFromFile();
        for (TeamManagerRewrite team : teamInformation) {
            if (team.getTeamName().toLowerCase().equals(name.toLowerCase())) {
                return team;
            }
        }

        return null;
    }

    public static TeamManagerRewrite getTeamByName (String name) {
        for (TeamManagerRewrite team : teamInformation) {
            if (team.getTeamName().toLowerCase().equals(name.toLowerCase())) {
                return team;
            }
        }

        return null;
    }

}
