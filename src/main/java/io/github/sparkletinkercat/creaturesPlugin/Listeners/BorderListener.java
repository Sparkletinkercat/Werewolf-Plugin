package io.github.sparkletinkercat.creaturesPlugin.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.Location;

public class BorderListener implements Listener {

    public BorderListener () {}

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        BorderManager.cancelMovementToBorder(player, event);
    }
}
