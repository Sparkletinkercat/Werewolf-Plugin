package io.github.sparkletinkercat.creaturesPlugin.Listeners;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.block.Action;
import org.bukkit.block.data.Openable;

public class PlayerListener implements Listener {
    private final JavaPlugin plugin;

     public PlayerListener (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        //Add Player to players file. 
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            String gameState = (String) Setting.getSettingValue("gameState");
            if (!gameState.equals("paused")) return;

            openNearbyDoors(player, 5);

        }, 0L, 20L);
    }

    public void openNearbyDoors(Player player, int radius) {
    Location loc = player.getLocation();
    World world = player.getWorld();

    int px = loc.getBlockX();
    int py = loc.getBlockY();
    int pz = loc.getBlockZ();

    for (int x = -radius; x <= radius; x++) {
        for (int y = -1; y <= 2; y++) {
            for (int z = -radius; z <= radius; z++) {

                Block block = world.getBlockAt(px + x, py + y, pz + z);
                Material type = block.getType();

                if (type.name().endsWith("_DOOR") || type.name().endsWith("_TRAPDOOR")) {
                    if (block.getBlockData() instanceof Openable openable) {
                        openable.setOpen(true);
                        block.setBlockData(openable);
                    }
                }
            }
        }
    }
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

    @EventHandler
    public void onPressurePlate(PlayerInteractEvent event) {
        String gameState = (String) Setting.getSettingValue("gameState");

        if (event.getAction() != Action.PHYSICAL) return;
        else if (gameState.equals("paused")) {event.setCancelled(true);}
    }

    @EventHandler
    public void onEntityTripwire(EntityInteractEvent event) {
        String gameState = (String) Setting.getSettingValue("gameState");

        if (gameState.equals("paused")) {event.setCancelled(true);}
    }


    // @EventHandler
    // public void onChestOpen(PlayerInteractEvent event) {
    //     String gameData = (String) Setting.getSettingValue("gameData");

    //     if (event.getClickedBlock() == null) return;
    //     else if (gameData.equals("paused")) {event.setCancelled(true);}
    // }
}
