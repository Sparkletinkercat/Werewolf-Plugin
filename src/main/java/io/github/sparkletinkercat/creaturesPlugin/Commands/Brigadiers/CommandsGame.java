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
import org.bukkit.inventory.Inventory;

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

            // -------------------------------------------
            // Add in all registered beacons
            // -------------------------------------------

            Beacon beacon = new Beacon(plugin);
            List<Beacon.BeaconItem> beaconItems = beacon.retrieveAllBeaconsFromFile();
            for (Beacon.BeaconItem beaconItem : beaconItems) {
                player.sendMessage(beaconItem.getName());
                
            }
            beaconListener.importBeaconItems(beaconItems);

            // Register all beacon Info bars
            Map<String, InformationBar> beaconInfoBars = beacon.createBeaconInformationBars (beaconItems);
            beaconListener.importBeaconInfoBars(beaconInfoBars);

            // -------------------------------------------
            // Add in all registered teams
            // -------------------------------------------

            // Open teamSettings.yml
            // If team is enabled then grab it and put it into the team class
            // Get the list of players online and in a list. 
            // Assign the roles for each team, removing that player from the list as done so.
            

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

            for (Player individualPlayer : Bukkit.getOnlinePlayers()) {
                PlayerManager playerManager = new PlayerManager(plugin,individualPlayer);
                playerManager.removeAllNonPermissionTags();
            }
            

        });
        
       
        return root;
    }


}
