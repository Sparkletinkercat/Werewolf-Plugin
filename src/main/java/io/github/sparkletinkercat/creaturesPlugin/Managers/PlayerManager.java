package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import java.util.Set;

public class PlayerManager {
    private Player player;
    private final JavaPlugin plugin;

    
    public PlayerManager (JavaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void removeAllNonPermissionTags () {
        Set<String> tags = player.getScoreboardTags();

        for (String tag : tags) {
            if (!tag.contains("permission")) {
                player.removeScoreboardTag(tag);
            }
        }
    }
}
