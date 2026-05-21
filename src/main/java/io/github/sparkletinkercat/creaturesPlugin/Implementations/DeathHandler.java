package io.github.sparkletinkercat.creaturesPlugin.Implementations;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import java.util.UUID;
import org.bukkit.GameMode;
import java.util.concurrent.ConcurrentHashMap;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.Bukkit;

public class DeathHandler<T extends DeathHandler<T>> extends AspectHandler<T> {
    
    public void runTrueDeath () {
        Player player = Bukkit.getPlayer(this.getPlayerID());
        player.setGameMode(GameMode.SPECTATOR);
    }

    @Override
    protected String getSectionName () {return "DeathData";} 

}
