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
        Beacon beacon = new Beacon(plugin);
        ItemStack item = beacon.checkIfBeaconNearby(player, 5);
        
        //If event is sneaking, start sneak task

        if (item != null && event.isSneaking()) {
            InformationBar infoBar = beaconInfoBars.get(beacon.getBeaconMetadata(item,"name"));
            if (beaconInfoBars != null && infoBar != null) {
                infoBar.displayInformationBar(player);
                startSneakTask(player);
            }
        } else if (item == null || !event.isSneaking()) {
            InformationBar infoBar = new InformationBar();
            infoBar.removeAllInformationBarsByList (player, beaconInfoBars);
        }

    }

    public void startSneakTask(Player player) {

        Bukkit.getScheduler().runTaskTimer(plugin, task -> {

            // Stop if player stops crouching
            if (!player.isSneaking() || !player.isOnline()) {
                task.cancel();
                return;
            }

            player.sendMessage("Cosencrating");

        }, 0L, 1L); // 1 tick
    }


    
}
