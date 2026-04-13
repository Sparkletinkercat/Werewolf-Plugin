package io.github.sparkletinkercat.creaturesPlugin.Managers;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.sparkletinkercat.creaturesPlugin.Listeners.BeaconListener;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.MenuListener;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.PlayerListener;

import java.util.List;
import java.util.Map;

import org.bukkit.event.Listener;

public class Game {
    private JavaPlugin plugin;
    
    public Game (JavaPlugin plugin) {
        this.plugin = plugin;
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


    public void setupAllBeacons () {
        Beacon beacon = new Beacon(plugin);
        System.out.println("here1");
        List<Beacon.BeaconItem> beaconItems = beacon.retrieveAllBeaconsFromFile();
        System.out.println("here2");
        if (beaconItems != null) {
            System.out.println("here3");
            BeaconListener.importBeaconItems(beaconItems);

            // Register all beacon Info bars
            Map<String, InformationBar> beaconInfoBars = beacon.createBeaconInformationBars (beaconItems);
            BeaconListener.importBeaconInfoBars(beaconInfoBars);
        }
    }
    
}
