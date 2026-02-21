package io.github.sparkletinkercat.creaturesPlugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WerewolfPlugin extends JavaPlugin implements Listener {
  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.getPlayer().sendMessage(Component.text("Hello there, " + event.getPlayer().getName() + "!"));
  }
}