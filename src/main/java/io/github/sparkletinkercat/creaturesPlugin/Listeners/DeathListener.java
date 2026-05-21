package io.github.sparkletinkercat.creaturesPlugin.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import io.github.sparkletinkercat.creaturesPlugin.Implementations.*;
import io.github.sparkletinkercat.creaturesPlugin.DeathManagers.*;

public class DeathListener implements Listener {

    public DeathListener () {}

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        AspectHandler<DeathWeakness>  test = new DeathWeakness();
        test.storeAspectInFile(event.getPlayer());
        event.getPlayer().sendMessage("Death Occured");
    }
}