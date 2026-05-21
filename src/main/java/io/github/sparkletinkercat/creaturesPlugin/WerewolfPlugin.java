package io.github.sparkletinkercat.creaturesPlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers.*;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import io.github.sparkletinkercat.creaturesPlugin.Managers.Game;

import org.bukkit.event.server.ServerLoadEvent;



public class WerewolfPlugin extends JavaPlugin implements Listener {
      private static WerewolfPlugin instance;

      @Override
      public void onLoad() {}

      public static WerewolfPlugin getInstance() {return instance;}
  
      /**
       * Enables and registers all listeners and commands,
       * Creates the plugin folder and all resource paths.
       *
       */
      @Override
      public void onEnable() {
            instance = this;
            Bukkit.getPluginManager().registerEvents(this, this);


            Game.registerAllListeners(this);
            new CommandBrigadier(this).registerAll();
            
            FileManager.setupGameFiles(this);

            
      }

      @EventHandler
      public void onServerLoad(ServerLoadEvent event) {
            Game game = new Game (this);
            game.setupAllBeacons ();
            game.setupAllSettings();
            game.getTeams();
            game.checkBeaconGameState();
            BorderManager.getBorderSettings();
      }

      @Override
      public void onDisable() {}

}