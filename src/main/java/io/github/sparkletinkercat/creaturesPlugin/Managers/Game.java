package io.github.sparkletinkercat.creaturesPlugin.Managers;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.sparkletinkercat.creaturesPlugin.Listeners.BeaconListener;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.MenuListener;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.PlayerListener;

import java.util.List;

import org.bukkit.event.Listener;

public class Game {
    private JavaPlugin plugin;
    
    public Game (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static BeaconListener registerAllListeners (JavaPlugin plugin) {
        // Register Listeners
        List<Listener> listeners = List.of(
            new BeaconListener(plugin),
            new PlayerListener(plugin),
            new MenuListener(plugin)
        );

        for (Listener item : listeners) {
            plugin.getServer().getPluginManager().registerEvents(item, plugin);
        }

        return (BeaconListener) listeners.get(0);
    }
    
}
