package io.github.sparkletinkercat.creaturesPlugin.Managers;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import io.github.sparkletinkercat.creaturesPlugin.WerewolfPlugin;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.BeaconListener;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.MenuListener;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.PlayerListener;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.maven.artifact.repository.metadata.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Game {
    private JavaPlugin plugin;
    final BukkitTask[] taskHolder = new BukkitTask[1];
    
    public Game (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Game () {
        this.plugin = WerewolfPlugin.getInstance();
    }

    public static void registerAllListeners (JavaPlugin plugin) {
        // Register Listeners
        List<Listener> listeners = List.of(
            new BeaconListener(plugin),
            new PlayerListener(plugin),
            new MenuListener(plugin)
        );

        for (Listener item : listeners) {
            plugin.getServer().getPluginManager().registerEvents(item, plugin);
        }
    }

    public void pausedGame () {
        String gameState = (String) Setting.getSettingValue("gameState");
        if (gameState.equals("paused")) {

            ActionBar actionBar = new ActionBar("PAUSED", NamedTextColor.RED, 1, -1);
            taskHolder[0] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                ActionBar actionBarPrio = ActionBar.getPriorityActionBar();
                
                String gameStateUpdated = (String) Setting.getSettingValue("gameState");
                if (!gameStateUpdated.equals("paused")) {
                    actionBar.removeActionBar();
                    taskHolder[0].cancel();
                }
                
                List<Player> players = PluginPlayer.getAllOnlinePlayers();
                for (Player player : players) {
                    player.sendActionBar(
                        Component.text(actionBarPrio.getText())
                        .color(actionBarPrio.getColor())
                    );
                }
                

                }, 0L, 40L);
            };
    }

    public void setupAllBeacons () {
        Beacon beacon = new Beacon(plugin);
        List<Beacon.BeaconItem> beaconItems = beacon.retrieveAllBeaconsFromFile();
        if (beaconItems != null) {
            BeaconListener.importBeaconItems(beaconItems);

            // Register all beacon Info bars
            Map<String, InformationBar> beaconInfoBars = beacon.createBeaconInformationBars (beaconItems);
            BeaconListener.importBeaconInfoBars(beaconInfoBars);
        }
    }

    public void setupAllTeams () {
        TeamManager teamManager = new TeamManager(plugin);
        List<TeamManager.Team> teams = teamManager.retrieveAllEnabledTeamsFromFile();

        // Get the list of players online and in a list. 
        List<Player> players = PluginPlayer.getAllOnlinePlayers();
    
        // Assign the roles for each team, removing that player from the list as done so.
        for (TeamManager.Team team : teams) {
            for (int loop = 0; loop < team.getStartingNumber(); loop++) {
                if (players.size() == 0) {break;}
                
                int randomIndex = ThreadLocalRandom.current().nextInt(players.size());
                Player randomPlayer = players.get(randomIndex);
                String teamName = team.getTeamName();

                PluginPlayer.addTagToPlayer(randomPlayer,"team" + teamName.substring(0, 1).toUpperCase() + teamName.substring(1));
                players.remove(randomIndex);
            }
        }

        // Assign Remaining Players default human role
        for (Player individualPlayer : players) {PluginPlayer.addTagToPlayer(individualPlayer,"teamHuman");}
    }

    public void getTeams () {
        TeamManager teamManager = new TeamManager(plugin);
        teamManager.retrieveAllEnabledTeamsFromFile();
    }

    public void checkBeaconGameState () {
        List<Beacon.BeaconItem> beacons = BeaconListener.getBeaconItems();
        Beacon beaconManager = new Beacon(plugin);
        Map<String, Integer> controllingTeams = new HashMap<String, Integer>();
        int beaconNumber = 0;

        for (Beacon.BeaconItem beacon : beacons) {
            beaconNumber++;
            
            String controllingTeam = beaconManager.getBeaconMetadata (beaconManager.returnBeaconAtLocation (beacon.getX(), beacon.getY(), beacon.getZ()), "ControllingTeam");
            controllingTeams.put(controllingTeam, controllingTeams.getOrDefault(controllingTeam, 0) + 1);
        }


        
        boolean controlsAllBeacons = false; 
        for (Player player : PluginPlayer.getAllOnlinePlayers ()) {
            PluginPlayer playerManager = new PluginPlayer(player);
            boolean controlsABeacon = false;
            
            for (Map.Entry<String, Integer> entry : controllingTeams.entrySet()) {
                String team = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
                if (playerManager.getPlayersTagByContains (player, team) != null) {
                    controlsABeacon = true;

                    // Get team specific bonus
                    TeamManager.Team teamData = TeamManager.getTeamByName (team);
                    String attributeBonus = teamData.getAllBeaconsControlledBonus();
                    double bonusPerLevel = teamData.getbeaconControlBonusPerLevel();
                    PluginPlayer.removeAllAtributes(player,plugin);
                    Bukkit.broadcast(
                        Component.text((double) entry.getValue() * bonusPerLevel, NamedTextColor.RED)
                    );
                    PluginPlayer.setAttribute(attributeBonus, player, (double) entry.getValue() * bonusPerLevel, "bonus_beacon");
                }

                if (entry.getValue() == beaconNumber && controlsAllBeacons != true) {
                    controlsAllBeacons = true;
                    Bukkit.broadcast(
                        Component.text("All beacons are controlled by :" + entry.getKey(), NamedTextColor.RED)
                    );
                }
            }
            if (controlsABeacon == false) {
                PluginPlayer.removeAllAtributes(player,plugin);
            }
        }
    }

    public void setupAllSettings () {
        FileManager fileManager = new FileManager(plugin, "settings");
        Setting.convertToSettings (fileManager.retrieveAllSections ());
    }

    
}
