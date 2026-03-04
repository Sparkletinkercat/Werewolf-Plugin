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

public class BeaconListener implements Listener {
    private final JavaPlugin plugin;
    private List<Beacon.BeaconItem> beaconItems;

    public BeaconListener (JavaPlugin plugin) {
        this.plugin = plugin;
        this.beaconItems = null;
    }

    public void importBeaconItems (List<Beacon.BeaconItem> beaconItems) {this.beaconItems = beaconItems;}
    
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        InformationBar infoBar = new InformationBar ("Concencrating");
        

        if (event.isSneaking()) {player.sendMessage("Crouched");} 
        else {player.sendMessage("No longer crouched");}

        if (checkIfBeaconNearby(player, 5) && event.isSneaking()) {
            infoBar.displayInformationBar(player);
        } else if (!checkIfBeaconNearby(player, 5) || !event.isSneaking()) {
            infoBar.removeInformationBar(player);
            player.sendMessage("Removed bossbar");
        }
    }

    public boolean checkIfBeaconNearby(Player player, int radius) {
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof ItemDisplay display) {
                ItemStack item = display.getItemStack();

                if (item != null && item.getType() == Material.CARVED_PUMPKIN) {return true;}
            }
        }
        return false;
    }
}
