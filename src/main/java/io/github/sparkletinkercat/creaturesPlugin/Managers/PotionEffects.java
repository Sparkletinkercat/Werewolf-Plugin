package io.github.sparkletinkercat.creaturesPlugin.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.sparkletinkercat.creaturesPlugin.WerewolfPlugin;

import org.bukkit.Registry;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.kyori.adventure.key.Key;


public class PotionEffects {
    private static Map<UUID, List<PotionEffect>> storedPotionEffects = new HashMap<>();
    
    public static void givePotionEffect (Player player, String type, int amplifier, int duration) {
        PotionEffectType effectType = Registry.EFFECT.get(Key.key(type.toLowerCase()));
        if (effectType == null) {
            System.out.println("Invalid potion type: " + type);
            return;
        }

        player.addPotionEffect(
            new PotionEffect(
                effectType,
                duration,   // duration (ticks)
                amplifier,         // amplifier
                false,     // ambient
                false,     // particles OFF
                true      // icon OFF
            )
        );
    }

    public static void givePotionEffectToAll (String type, int amplifier, int duration) {
        PotionEffectType effectType = Registry.EFFECT.get(Key.key(type.toLowerCase()));
        if (effectType == null) {
            System.out.println("Invalid potion type: " + type);
            return;
        }

        List<Player> players = PluginPlayer.getAllOnlinePlayers ();
        for (Player player : players) {
            player.addPotionEffect(
                new PotionEffect(
                    effectType,
                    duration,   // duration (ticks)
                    amplifier,         // amplifier
                    false,     // ambient
                    false,     // particles OFF
                    true      // icon OFF
                )
            );
        }
        storeAllPlayerEffectsInFile ();
    }

    public static void storeAllPlayersCurrentEffects () {
        storedPotionEffects.clear();

        for (Player player : PluginPlayer.getAllOnlinePlayers()) {
            storePlayersCurrentEffects (player);
        }
    }

    public static void storePlayersCurrentEffects (Player player) {
        List<PotionEffect> effects = new ArrayList<>(player.getActivePotionEffects());
        storedPotionEffects.put(player.getUniqueId(), effects);
    }

    public static void clearAllEffectsFromPlayer (Player player) {
        player.clearActivePotionEffects();
    }

    public static void restorePlayersCurrentEffects (Player player) {
        UUID playerId = player.getUniqueId();

        retrieveAllPlayerEffectsFromFile ();
        List<PotionEffect> potionEffects = storedPotionEffects.get(playerId);
        // Apply potion effects list
        for (PotionEffect effect : potionEffects) {
            player.addPotionEffect(effect);
        }
    }

    private static void storeAllPlayerEffectsInFile () {
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();

        FileManager file = new FileManager(plugin, "trackPotionEffects");
        YamlConfiguration config = file.returnConfig();
        for (Map.Entry<UUID, List<PotionEffect>> entry : storedPotionEffects.entrySet()) {
            List<PotionEffect> potionEffects = entry.getValue();
            config.set(entry.getKey().toString(), potionEffects);

            try {config.save(file.getFile());} 
            catch (Exception e) {}
        }
    }

    private static void retrieveAllPlayerEffectsFromFile () {
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();
        FileManager file = new FileManager(plugin, "trackPotionEffects");
        List<Player> players = PluginPlayer.getAllOnlinePlayers();

        for (Player player : players) {
            YamlConfiguration config = file.returnConfig();
            List<PotionEffect> effects = config.getList(player.getUniqueId().toString()).stream()
                .map(obj -> (PotionEffect) obj)
                .toList();

            storedPotionEffects.put(player.getUniqueId(), effects);
        }
    }
}
