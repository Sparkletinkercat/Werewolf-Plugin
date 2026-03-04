package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

public class InformationBar {
    private BossBar bossBar;
    
    public InformationBar (String name) {
        bossBar = Bukkit.createBossBar(name,
                BarColor.RED,
                BarStyle.SEGMENTED_6
        );

        bossBar.setProgress(0);
    }

    public void displayInformationBar (Player player) {bossBar.addPlayer(player);}

    public void removeInformationBar (Player player) {
        if (bossBar != null) {
            player.sendMessage("here");
            bossBar.removePlayer(player);
        }
    }



}
