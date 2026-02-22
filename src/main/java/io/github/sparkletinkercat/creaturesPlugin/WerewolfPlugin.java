package io.github.sparkletinkercat.creaturesPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.sparkletinkercat.creaturesPlugin.Commands.Werewolf;
import io.papermc.paper.command.brigadier.BasicCommand;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

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
  }

  @EventHandler
public void onBlockPlace(BlockPlaceEvent event) {
    Block block = event.getBlock();

    if (block.getType() == Material.BARRIER) { // Our "custom block"
        event.getPlayer().sendMessage("You placed a custom block!");

        // Add custom behavior, e.g., break differently
    }
}

@EventHandler
public void onBlockBreak(BlockBreakEvent event) {
    Block block = event.getBlock();

    if (block.getType() == Material.BARRIER) {
        event.setDropItems(false); // maybe drop custom item instead
        event.getBlock().getWorld().dropItemNaturally(block.getLocation(),
            new ItemStack(Material.DIAMOND)); // example
    }
}

  
}