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
import java.util.HashMap;

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
        ItemStack item = checkIfBeaconNearby(player, 5);
        Beacon beacon = new Beacon(plugin);

        if (item != null && event.isSneaking()) {
            InformationBar infoBar = beaconInfoBars.get(beacon.getBeaconMetadata(item,"name"));
            if (beaconInfoBars != null && infoBar != null) {
                infoBar.displayInformationBar(player);
            }
        } else if (item == null || !event.isSneaking()) {
            InformationBar infoBar = beaconInfoBars.get(beacon.getBeaconMetadata(item,"name"));
            if (beaconInfoBars != null && infoBar != null) {
                infoBar.removeInformationBar(player);
            }
        }











        // InformationBar infoBar = new InformationBar ("Concencrating");
        

        // if (event.isSneaking()) {player.sendMessage("Crouched");} 
        // else {player.sendMessage("No longer crouched");}

        // if (checkIfBeaconNearby(player, 5) && event.isSneaking()) {
        //     infoBar.displayInformationBar(player);
        // } else if (!checkIfBeaconNearby(player, 5) || !event.isSneaking()) {
        //     infoBar.removeInformationBar(player);
        //     player.sendMessage("Removed bossbar");
        // }
    }


    public ItemStack checkIfBeaconNearby(Player player, int radius) {
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof ItemDisplay display) {
                ItemStack item = display.getItemStack();

                if (item != null && item.getType() == Material.CARVED_PUMPKIN) {return item;}
            }
        }
        return null;
    }
}
