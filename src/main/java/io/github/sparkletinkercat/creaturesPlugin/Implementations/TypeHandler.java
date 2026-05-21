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

public class TypeHandler<T> {
    public String sectionName = "Type";
    public String fileName = "players";

    public void storeAspectInFile () {
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();

        FileManager file = new FileManager(plugin, this.getFileName());
        YamlConfiguration config = file.returnConfig();

        config.set(this.getSectionName(), toMap());

        try {config.save(file.getFile());} 
        catch (Exception e) {}
    }

    public void retrieveAspectFromFile () {
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();
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
                            Object value = config.get(this.getSectionName() + "." + name);
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

    public List<TypeHandler<?>> retrieveAllAspectsFromFile () {
        WerewolfPlugin plugin = WerewolfPlugin.getInstance();

        FileManager file = new FileManager(plugin, "players");
        YamlConfiguration config = file.returnConfig();
        ConfigurationSection section = config.getConfigurationSection("");
        List<String> names = new ArrayList<>(section.getKeys(false));

        List<TypeHandler<?>> classes = new ArrayList<> ();

        for (String sectionName : names) {
            Class<?> clazz = this.getClass();
            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();
            

                while (clazz != null) {
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);

                        try {
                            String name = field.getName();
                            if (!name.equals("sectionName")) {
                                Object value = config.get(sectionName + "." + name);
                                if (value != null) {
                                    field.set(instance, value); 
                                }
                            }
                            

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    clazz = clazz.getSuperclass();
                }
                classes.add((TypeHandler<?>) instance);
            } catch (Exception e) {e.printStackTrace();}
        }
        return classes;
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
                    if (!name.equals("sectionName") && !name.equals("fileName") && !name.equals("teamSettings")) {
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
}
