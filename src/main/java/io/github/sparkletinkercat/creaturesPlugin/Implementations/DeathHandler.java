package io.github.sparkletinkercat.creaturesPlugin.Implementations;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;

public class DeathHandler<T extends DeathHandler<T>> extends AspectHandler<T>  {
    
    public boolean checkIfTrueDeath (Player player) {
        PluginPlayer playerData = new PluginPlayer (player);
        

        return false;
    }

    @Override
    protected String getSectionName () {return "DeathData";} 

}
