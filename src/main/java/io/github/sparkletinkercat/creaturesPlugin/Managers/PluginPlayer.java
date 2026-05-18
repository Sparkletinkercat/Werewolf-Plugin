package io.github.sparkletinkercat.creaturesPlugin.Managers;


import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.sparkletinkercat.creaturesPlugin.WerewolfPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Set;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffectType;
import net.kyori.adventure.key.Key;

public class PluginPlayer {
    String teamName = "";
    private Player player;
    private HashMap<String, Object> playerInformation;
    
    public PluginPlayer (Player player) {
        this.player = player;
        this.playerInformation = this.getPlayersTags();

    }

    private HashMap<String, Object> getPlayersTags () {
        Set<String> tags = this.player.getScoreboardTags();
        
        // Get existing tags and assign to hashMap based on keys
        HashMap<String, Object> playerInformation = new HashMap<String,Object>();
        playerInformation.put("Team", null);
        playerInformation.put("BuildMode", false);

        for (String tag : tags) {
            if (tag.toLowerCase().contains("team")) {playerInformation.replace("Team", tag);}
            if (tag.contains("permissionBuildModeEnabled")) {playerInformation.replace("BuildMode", true);}
        }



        return playerInformation;
    }

    // Return all the players information as a hashmap for ease of access
    public HashMap<String, Object> getAllPlayerInformation () {return this.playerInformation;}

    public Object getPlayerInformation (String key) {return this.playerInformation.get(key);}

    public static List<Player> getAllOnlinePlayers () {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        return players;
    }

    public static void removeAllNonPermissionTags (Player player) {
        Set<String> tags = player.getScoreboardTags();

        for (String tag : tags) {
            if (!tag.contains("permission")) {
                player.removeScoreboardTag(tag);
            }
        }
    }

    public static void addTagToPlayer (Player player, String tagName) {player.addScoreboardTag(tagName);}

    public String getPlayersTagByContains (Player player, String contains) {
        for (String tag : player.getScoreboardTags()) {
            if (tag.contains(contains)) {return tag;}
        }

        return null;
    }

    public static void setAllPlayersAttributesByName (String attributeName, double attributeBonus, String keyName) {
        List<Player> players = getAllOnlinePlayers();
        for (Player player : players) {
            setAttribute(attributeName, player, attributeBonus, keyName);
        }
    }

    public static void setAttribute (String attributeName, Player player, double attributeBonus, String keyName) {
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();
        
        Attribute attribute = Registry.ATTRIBUTE.get(
            NamespacedKey.fromString(attributeName.toLowerCase())
        );

        // 2. Get instance from player
        AttributeInstance instance = player.getAttribute(attribute);

        if (instance != null) {
            NamespacedKey key = new NamespacedKey(plugin, keyName);
            instance.removeModifier(key);

            AttributeModifier bonus = new AttributeModifier(
                key,
                attributeBonus, // +10 hearts (since 2 HP = 1 heart)
                Operation.ADD_NUMBER
            );

            instance.addModifier(bonus);
        }
    }

    public static void removeAllPlayersAttributesByName (String attributeName, String keyName) {
        List<Player> players = getAllOnlinePlayers();
        for (Player player : players) {
            removeAttribute(attributeName, player,  keyName);
        }
    }

    public static void removeAttribute (String attributeName, Player player, String keyName) {
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();
        
        Attribute attribute = Registry.ATTRIBUTE.get(
            NamespacedKey.fromString(attributeName.toLowerCase())
        );

        // 2. Get instance from player
        AttributeInstance instance = player.getAttribute(attribute);

        if (instance != null) {
            NamespacedKey key = new NamespacedKey(plugin, keyName);
            instance.removeModifier(key);
        }
    }

    public static void removeAllAtributes (Player player, JavaPlugin plugin) {
        String targetPrefix = "bonus_";

        for (Attribute attribute : Registry.ATTRIBUTE) {
            AttributeInstance instance = player.getAttribute(attribute);

            if (instance == null) continue;


            for (AttributeModifier modifier : instance.getModifiers()) {
                NamespacedKey key = modifier.getKey();

                if (key.getNamespace().equals(plugin.getName().toLowerCase()) && key.getKey().startsWith(targetPrefix)) {
                    
                    instance.removeModifier(modifier);
                }
            }
        }
    }

    
}
