package io.github.sparkletinkercat.creaturesPlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers.*;
import io.github.sparkletinkercat.creaturesPlugin.Managers.FileManager;
import io.github.sparkletinkercat.creaturesPlugin.Managers.Game;

import org.bukkit.event.server.ServerLoadEvent;



public class WerewolfPlugin extends JavaPlugin implements Listener {
  
  @Override
  public void onLoad() {}
  
  
  /**
     * Enables and registers all listeners and commands,
     * Creates the plugin folder and all resource paths.
     *
     */
  @Override
  public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

      
        
        new CommandBrigadier(this, Game.registerAllListeners(this)).registerAll();
        
        FileManager.setupGameFiles(this);
      
  }

  @EventHandler
  public void onServerLoad(ServerLoadEvent event) {}

  @Override
  public void onDisable() {}
  
}