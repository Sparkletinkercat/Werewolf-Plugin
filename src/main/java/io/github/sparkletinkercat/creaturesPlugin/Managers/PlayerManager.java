package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import java.util.Set;
import org.bukkit.Bukkit;
import java.util.List;
import java.util.ArrayList;


public class PlayerManager {
    private final JavaPlugin plugin;

    
    public PlayerManager (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void removeAllNonPermissionTags (Player player) {
        Set<String> tags = player.getScoreboardTags();

        for (String tag : tags) {
            if (!tag.contains("permission")) {
                player.removeScoreboardTag(tag);
            }
        }
    }

    public List<Player> getAllOnlinePlayers () {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        return players;
    }

    public void addTagToPlayer (Player player, String tagName) {player.addScoreboardTag(tagName);}

}
