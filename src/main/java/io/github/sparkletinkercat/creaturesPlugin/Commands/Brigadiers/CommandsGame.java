package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.*;
import java.util.Map;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import net.kyori.adventure.text.format.NamedTextColor;
import java.util.concurrent.ThreadLocalRandom;

public class CommandsGame {
    private final JavaPlugin plugin;
    private final BeaconListener beaconListener;

    public CommandsGame (JavaPlugin plugin, BeaconListener beaconListener) {
        this.plugin = plugin;
        this.beaconListener = beaconListener;
    }

    public LiteralArgumentBuilder<CommandSourceStack> getGameCommand() {

        Command command = new Command(plugin, "game");
        LiteralArgumentBuilder<CommandSourceStack> root = command.returnCommandRoot();
        
        command.createCommandRoot("startGame", player -> {
            player.sendMessage("You started the game.");

            Beacon beacon = new Beacon(plugin);

            // -------------------------------------------
            // Get all registered beacon types
            // -------------------------------------------

            Beacon.BeaconType beaconTypes = beacon.new BeaconType ();
            List<Beacon.BeaconType> allBeaconTypes = beaconTypes.getBeaconTypes();

            for (Beacon.BeaconType type : allBeaconTypes) {
                player.sendMessage (type.getName());
            }
            

            // -------------------------------------------
            // Add in all registered beacons
            // -------------------------------------------

            
            List<Beacon.BeaconItem> beaconItems = beacon.retrieveAllBeaconsFromFile();
            if (beaconItems != null) {
                for (Beacon.BeaconItem beaconItem : beaconItems) {
                    player.sendMessage(beaconItem.getName());
                    
                }
                beaconListener.importBeaconItems(beaconItems);

                // Register all beacon Info bars
                Map<String, InformationBar> beaconInfoBars = beacon.createBeaconInformationBars (beaconItems);
                beaconListener.importBeaconInfoBars(beaconInfoBars);
            }
            

            // -------------------------------------------
            // Add in all registered teams
            // -------------------------------------------

            // Open teamSettings.yml
            // If team is enabled then grab it and put it into the team class
            TeamManager teamManager = new TeamManager(plugin);
            List<TeamManager.Team> teams = teamManager.retrieveAllEnabledTeamsFromFile();

            // Get the list of players online and in a list. 
            PlayerManager playerManager = new PlayerManager(plugin);
            List<Player> players = playerManager.getAllOnlinePlayers();

            for (Player playertest : players) {
                player.sendMessage(playertest.getName());
            }

            // Assign the roles for each team, removing that player from the list as done so.
            for (TeamManager.Team team : teams) {
                for (int loop = 0; loop < team.getStartingNumber(); loop++) {
                    if (players.size() == 0) {
                        player.sendMessage("Too few players for the requested team number. Finishing setup without remaining team numbers");
                        break;
                    }
                    
                    int randomIndex = ThreadLocalRandom.current().nextInt(players.size());
                    Player randomPlayer = players.get(randomIndex);
                    String teamName = team.getTeamName();

                    playerManager.addTagToPlayer(randomPlayer,"team" + teamName.substring(0, 1).toUpperCase() + teamName.substring(1));
                    players.remove(randomIndex);
                }
            }

            // Assign Remaining Players default human role
            for (Player individualPlayer : players) {playerManager.addTagToPlayer(individualPlayer,"teamHuman");}

        });

        command.createCommandRoot("endGame", player -> {
            player.sendMessage("You ended the game.");

            
            
        });

        command.createCommandRoot("newGame", player -> {
            player.sendMessage("You created a new game.");

            InventoryDisplay display = new InventoryDisplay(plugin);
            display.createMenu(27, "Start a new game");
            display.createMenuButton (Material.GREEN_WOOL, "Yes", "Start the game with the current game settings.", NamedTextColor.GREEN, 12);
            display.createMenuButton (Material.RED_WOOL, "No", "", NamedTextColor.RED, 14);
            display.openInventory(player);

            PlayerManager playerManager = new PlayerManager(plugin);
            for (Player individualPlayer : Bukkit.getOnlinePlayers()) {
                playerManager.removeAllNonPermissionTags(individualPlayer);
            }
            

        });
        
       
        return root;
    }


}
