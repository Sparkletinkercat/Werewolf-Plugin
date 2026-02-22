package io.github.sparkletinkercat.creaturesPlugin.Managers;


import org.bukkit.entity.Player;
import java.util.Set;
import java.util.HashMap;

public class PluginPlayer {
    String teamName = "";
    private Player player;
    private HashMap<String, String> playerInformation;
    
    public PluginPlayer (Player player) {
        this.player = player;
        this.playerInformation = this.getPlayersTags();

    }

    private HashMap<String, String> getPlayersTags () {
        Set<String> tags = this.player.getScoreboardTags();
        
        // Get existing tags and assign to hashMap based on keys
        HashMap<String, String> playerInformation = new HashMap<String,String>();
        for (String tag : tags) {
            if (tag.toLowerCase().contains("team")) {playerInformation.put("Team", tag);}
            if (tag.toLowerCase().contains("permission")) {playerInformation.put("Team", tag);}
        }

        return playerInformation;
    }

    // Return all the players information as a hashmap for ease of access
    public HashMap<String, String> getAllPlayerInformation () {return this.playerInformation;}

    public String getPlayerInformation (String key) {return this.playerInformation.get(key);}

}
