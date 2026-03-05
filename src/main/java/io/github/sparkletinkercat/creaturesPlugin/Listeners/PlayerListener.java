package io.github.sparkletinkercat.creaturesPlugin.Listeners;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.entity.Player;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.entity.Entity;  
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.Set;

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
}
