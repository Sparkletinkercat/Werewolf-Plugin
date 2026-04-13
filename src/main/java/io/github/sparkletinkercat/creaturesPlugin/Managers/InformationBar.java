package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class InformationBar {
    private BossBar bossBar;
    private List<Player> players;
    
    public InformationBar (String name) {
        bossBar = Bukkit.createBossBar(name,
                BarColor.RED,
                BarStyle.SEGMENTED_6
        );

        bossBar.setProgress(0);

        this.players = new ArrayList<Player> ();
    }

    public InformationBar () {}

    public void displayInformationBar (Player player) {
        bossBar.addPlayer(player);
        players.add(player);
    }

    public void removeInformationBar (Player player) {
        if (bossBar != null) {
            for (Player playerToCheck : players) {
                if (playerToCheck == player) {
                    bossBar.removePlayer(player);
                }
            }
        }
    }

    public void setBossBarPercentage (int value) {
        double newValue = value;
        bossBar.setProgress(newValue * 0.01);
    }

    public void setBossBarPercentage (double value) {
        bossBar.setProgress(value * 0.01);
    }

    public BossBar getBossBar () {return bossBar;}

    public void removeAllInformationBarsByList (Player player, Map<String, InformationBar> infoBars) {
        //List<InformationBar> values = new ArrayList<>(infoBars.values());

        if (infoBars == null) {return;}
        
        for (InformationBar bar : infoBars.values()) {
            bar.getBossBar().removePlayer(player);
        }

    }





}
