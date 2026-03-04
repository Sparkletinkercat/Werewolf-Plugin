package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.*;

import java.util.List;
import java.util.ArrayList;

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
            List<Beacon.BeaconItem> beaconItems = beacon.retrieveAllBeaconsFromFile();
            for (Beacon.BeaconItem beaconItem : beaconItems) {
                player.sendMessage(beaconItem.getName());
                
            }
            beaconListener.importBeaconItems(beaconItems);
        });
        
       
        return root;
    }


}
