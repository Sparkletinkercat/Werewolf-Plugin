package io.github.sparkletinkercat.creaturesPlugin.Implementations;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import io.github.sparkletinkercat.creaturesPlugin.WerewolfPlugin;
import java.lang.reflect.Field;

public class AspectHandler<T> {
    public String sectionName = "Aspect";
    public String fileName = "players";
    protected UUID playerID;

    public AspectHandler() {}
    
    public void storeAspectInFile (Player player) {
        this.playerID = player.getUniqueId();
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();

        FileManager file = new FileManager(plugin, this.getFileName());
        YamlConfiguration config = file.returnConfig();

        UUID playerID = player.getUniqueId();
        config.set(playerID.toString() + "." + this.getSectionName(), toMap());

        try {config.save(file.getFile());} 
        catch (Exception e) {
            player.sendMessage("Failed");
        }
    }

    public void retrieveAspectFromFile (Player player) {
        this.playerID = player.getUniqueId();
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();
        UUID playerID = player.getUniqueId();
        String sectionName = this.getSectionName();

        FileManager file = new FileManager(plugin, "players");
        YamlConfiguration config = file.returnConfig();

        Class<?> clazz = this.getClass();
        try {
            //Object instance = clazz.getDeclaredConstructor().newInstance();
        

            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);

                    try {
                        String name = field.getName();
                        if (!name.equals("sectionName")) {
                            Object value = config.get(playerID + "." + sectionName + "." + name);
                            if (value != null) {
                                field.set(this, value); 
                            }
                        }
                        

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                clazz = clazz.getSuperclass();
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    private Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        Class<?> clazz = this.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                try {
                    Object value = field.get(this);
                    String name = field.getName();
                    if (!name.equals("sectionName") && !name.equals("fileName") && !name.equals("playerID")) {
                        map.put(name, value);
                    }
                    

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            clazz = clazz.getSuperclass(); // 👈 move up the chain
        }

        return map;
    }

    protected String getSectionName () {return this.sectionName;} 
    protected String getFileName () {return this.fileName;} 
    protected UUID getPlayerID () {return this.playerID;}
}
