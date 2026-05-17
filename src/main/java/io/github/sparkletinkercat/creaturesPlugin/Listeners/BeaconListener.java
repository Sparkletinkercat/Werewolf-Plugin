package io.github.sparkletinkercat.creaturesPlugin.Listeners;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;

public class BeaconListener implements Listener {
    private final JavaPlugin plugin;
    private static List<Beacon.BeaconItem> beaconItems = new ArrayList<Beacon.BeaconItem>();
    private static Map<String, InformationBar> beaconInfoBars = new HashMap<String, InformationBar>();

    public BeaconListener (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void importBeaconItems (List<Beacon.BeaconItem> importedBeaconItems) {
        beaconItems.clear();
        for (Beacon.BeaconItem beaconItem : importedBeaconItems) {beaconItems.add(beaconItem);}
    }

    public static void importBeaconInfoBars (Map<String, InformationBar> importedBeaconInfoBars) {
        beaconInfoBars.clear();
        beaconInfoBars.putAll(importedBeaconInfoBars);
    }

    public static List<Beacon.BeaconItem> getBeaconItems() {return beaconItems;}
    public static Map<String, InformationBar> getBeaconInforBars() {return beaconInfoBars;}
    
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

            // Get beacons controlling team and increament infoBar
            int conversionTime = 0;
            try {
                conversionTime = (int)Setting.getSettingValue("beaconConversionTimeInTicks");
            } catch (Exception e) {
                conversionTime = 1000;
            }
            beacon.consecrateBeacon(player, 5, conversionTime, item, infoBar);

        }, 0L, 1L); // 1 tick
    }


    
}
