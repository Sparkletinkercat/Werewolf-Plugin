package io.github.sparkletinkercat.creaturesPlugin.Listeners;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.entity.Entity;  
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;

public class BeaconListener implements Listener {
    private final JavaPlugin plugin;
    private List<Beacon.BeaconItem> beaconItems;
    private Map<String, InformationBar> beaconInfoBars;

    public BeaconListener (JavaPlugin plugin) {
        this.plugin = plugin;
        this.beaconItems = null;
        this.beaconInfoBars = null;
    }

    public void importBeaconItems (List<Beacon.BeaconItem> beaconItems) {this.beaconItems = beaconItems;}
    public void importBeaconInfoBars (Map<String, InformationBar> beaconInfoBars) {this.beaconInfoBars = beaconInfoBars;}
    
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (event.isSneaking()) {startSneakTask(player);}

    }

    public void startSneakTask(Player player) {
        Beacon beacon = new Beacon(plugin);

        Bukkit.getScheduler().runTaskTimer(plugin, task -> {
            ItemStack item = beacon.checkIfBeaconNearby(player, 5);

            // Stop if player stops crouching or moves away
            if (!player.isSneaking() || !player.isOnline() || item == null) {
                InformationBar infoBar = new InformationBar();
                infoBar.removeAllInformationBarsByList (player, beaconInfoBars);
                task.cancel();
                return;
            }

            InformationBar infoBar = beaconInfoBars.get(beacon.getBeaconMetadata(item,"name"));
            infoBar.displayInformationBar(player);

        }, 0L, 1L); // 1 tick
    }


    
}
