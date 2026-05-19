package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import net.kyori.adventure.text.format.NamedTextColor;
import java.util.concurrent.ThreadLocalRandom;

public class CommandsGame {
    private final JavaPlugin plugin;

    public CommandsGame (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> getGameCommand() {

        Command command = new Command(plugin, "game");
        LiteralArgumentBuilder<CommandSourceStack> root = command.returnCommandRoot();
        
        command.createCommandRoot("startGame", player -> {
            player.sendMessage("You started the game.");


            Game game = new Game (plugin);
            game.setupAllBeacons ();
            game.setupAllTeams();
            game.setupAllSettings();
            

        });

        command.createCommandRoot("endGame", player -> {
            player.sendMessage("You ended the game.");
        });

        command.createCommandRoot("newGame", player -> {
            player.sendMessage("You created a new game.");

            InventoryDisplay display = new InventoryDisplay(plugin);
            display.createMenu(27, "Start a new game");
            display.createMenuButton (Material.GREEN_WOOL, "Yes", "Start the game with the current game settings.", NamedTextColor.GREEN, 12);
            display.createMenuButton (Material.RED_WOOL, "No", "", NamedTextColor.RED, 14);
            display.openInventory(player);

            for (Player individualPlayer : Bukkit.getOnlinePlayers()) {
                PluginPlayer.removeAllNonPermissionTags(individualPlayer);
            }
            

        });

        command.createCommandRoot("pauseGame", player -> {
            player.sendMessage("You have paused the game.");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tick freeze");
            PotionEffects.storePlayersCurrentEffects (player);
            PotionEffects.clearAllEffectsFromPlayer(player);
            PotionEffects.givePotionEffectToAll("SATURATION", 255, -1);
            PluginPlayer.setAllPlayersAttributesByName("BLOCK_INTERACTION_RANGE", -100, "pausedPlayerBlockInteractionDisabled");
            PluginPlayer.setAllPlayersAttributesByName("ENTITY_INTERACTION_RANGE", -100, "pausedPlayerEntityInteractionDisabled");
            Setting setting = Setting.getSettingByName("gameState");
            setting.updateSetting("paused");
            Game game = new Game();
            game.pausedGame();
        });

        command.createCommandRoot("resumeGame", player -> {
            player.sendMessage("You have resumed the game.");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tick unfreeze");
            PotionEffects.clearAllEffectsFromPlayer(player);
            PotionEffects.restorePlayersCurrentEffects (player);
            PluginPlayer.removeAllPlayersAttributesByName("BLOCK_INTERACTION_RANGE", "pausedPlayerBlockInteractionDisabled");
            PluginPlayer.removeAllPlayersAttributesByName("ENTITY_INTERACTION_RANGE",  "pausedPlayerEntityInteractionDisabled");
            Setting setting = Setting.getSettingByName("gameState");
            setting.updateSetting("active");
        });
        
       
        return root;
    }


}
