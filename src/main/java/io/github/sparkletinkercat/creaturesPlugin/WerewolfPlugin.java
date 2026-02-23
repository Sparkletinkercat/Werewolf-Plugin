package io.github.sparkletinkercat.creaturesPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.sparkletinkercat.creaturesPlugin.Commands.Werewolf;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import io.papermc.paper.command.brigadier.BasicCommand;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Location;

import java.util.Set;

public class WerewolfPlugin extends JavaPlugin implements Listener {
  
  @Override
  public void onLoad() {}
  
  @Override
  public void onEnable() {
      Bukkit.getPluginManager().registerEvents(this, this);

      //Register a command
      BasicCommand yourCommand = new Werewolf();
      registerCommand("werewolf", yourCommand);
  }

  @Override
  public void onDisable() {}


  // -------------------------------------------------------
  // EVENT HANDLERS
  // -------------------------------------------------------

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    Set<String> tags = player.getScoreboardTags();
    String tagsString = String.join(", ", tags);

    player.sendMessage("Your tags:" + tagsString);

    Beacon beacon = new Beacon (this);
    beacon.summonBeaconDisplay (82.00,92.00,459.00);
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
          Beacon beacon = new Beacon (this);
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
          Beacon beacon = new Beacon (this);
          beacon.removeBeaconDisplay (loc);
          event.getPlayer().sendMessage("You broke a block in build mode");
          
      }
    } else {
      event.getPlayer().sendMessage("You are not in build mode");
    }
  }

  
}