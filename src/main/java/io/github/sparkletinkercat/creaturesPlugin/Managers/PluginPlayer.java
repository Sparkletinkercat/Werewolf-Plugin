package io.github.sparkletinkercat.creaturesPlugin.Managers;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
}
