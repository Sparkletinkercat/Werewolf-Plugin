package io.github.sparkletinkercat.creaturesPlugin.Listeners;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Location;

public class PlayerListener implements Listener {
    private final JavaPlugin plugin;

     public PlayerListener (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        //Add Player to players file. 
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        // Check if player is in build_mode
        Player player = event.getPlayer();
        PluginPlayer pluginPlayer = new PluginPlayer(player);
        if ((boolean) pluginPlayer.getPlayerInformation("BuildMode")) {
        Block block = event.getBlock();

        if (block.getType() == Material.BARRIER) { // Our "custom block"
            Location loc = block.getLocation();
            Beacon beacon = new Beacon (plugin);
            beacon.summonBeaconDisplay (loc);
        }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Check if player is in build_mode
        Player player = event.getPlayer();
        PluginPlayer pluginPlayer = new PluginPlayer(player);
        if ((boolean) pluginPlayer.getPlayerInformation("BuildMode")) {
        Block block = event.getBlock();
        

        if (block.getType() == Material.BARRIER) { // Our "custom block"
            Location loc = block.getLocation();
            Beacon beacon = new Beacon (plugin);
            beacon.removeBeaconDisplay (loc);
            player.sendMessage("Testing");
            
        }
        } else {
            event.getPlayer().sendMessage("You are not in build mode");
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        String gameState = (String) Setting.getSettingValue("gameState");

        if (!(event.getEntity() instanceof Player player)) return;
        else if (gameState.equals("paused")) {event.setCancelled(true);}
    }

    // @EventHandler
    // public void onChestOpen(PlayerInteractEvent event) {
    //     String gameData = (String) Setting.getSettingValue("gameData");

    //     if (event.getClickedBlock() == null) return;
    //     else if (gameData.equals("paused")) {event.setCancelled(true);}
    // }
}
