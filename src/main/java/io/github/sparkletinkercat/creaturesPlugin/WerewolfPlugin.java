package io.github.sparkletinkercat.creaturesPlugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.NullMarked;

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

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    Set<String> tags = player.getScoreboardTags();
    String tagsString = String.join(", ", tags);

    player.sendMessage("Your tags:" + tagsString);
  }

  
}