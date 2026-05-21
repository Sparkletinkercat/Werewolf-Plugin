package io.github.sparkletinkercat.creaturesPlugin.Managers;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import java.util.ArrayList;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ActionBar {
    private static Map<UUID,List<ActionBar>> allActionBars = new ConcurrentHashMap<>();

    private int priority = 0;
    private int durationInTicks = 40;
    private long endTime = -1;
    private String text = "";
    private NamedTextColor color = NamedTextColor.RED;
    
    
    public ActionBar (String text, NamedTextColor color, int priority, int duractionInTicks) {
        this.color = color;
        this.text = text;
        this.priority = priority;
        this.durationInTicks = duractionInTicks;

        World world = Bukkit.getWorld("world");
        this.endTime = world.getFullTime() + duractionInTicks;

        for (Player player : PluginPlayer.getAllOnlinePlayers()) {
            // Does the entry already exist?
            if (!allActionBars.containsKey(player.getUniqueId())) {
                allActionBars.put(player.getUniqueId(),new ArrayList<ActionBar> ());
            }
            List<ActionBar> actionBars = allActionBars.get(player.getUniqueId());
            actionBars.add(this);
        }
        
    }

    public ActionBar (String text, NamedTextColor color, int priority, int duractionInTicks, List<Player> players) {
        this.color = color;
        this.text = text;
        this.priority = priority;
        this.durationInTicks = duractionInTicks;

        World world = Bukkit.getWorld("world");
        this.endTime = world.getFullTime() + duractionInTicks;

        for (Player player : players) {
            // Does the entry already exist?
            if (!allActionBars.containsKey(player.getUniqueId())) {
                allActionBars.put(player.getUniqueId(),new ArrayList<ActionBar> ());
            }
            List<ActionBar> actionBars = allActionBars.get(player.getUniqueId());
            actionBars.add(this);
            
        }
    }

    public void removeActionBar() {
        for (var entry : allActionBars.entrySet()) {
            List<ActionBar> actionBars = entry.getValue();

            actionBars.removeIf(actionBar -> actionBar == this);
        }
    }

    // public void removeActionBar () {
    //     for (var entry : allActionBars.entrySet()) {
    //         List<ActionBar> actionBars = entry.getValue();
    //         for (ActionBar actionBar : actionBars) {
    //             if (actionBar == this) {
    //                 actionBars.remove(this);
    //             }
    //         }
    //     }
    // }

    public static ActionBar getPriorityActionBar (Player player) {
        World world = Bukkit.getWorld("world");
        List<ActionBar> actionBars = allActionBars.get(player.getUniqueId());
        ActionBar priorityActionBar = null;
        int priority = -1;
        int position = 0;
        for (ActionBar actionBar : actionBars) {
            if (actionBar.endTime >= world.getFullTime()) {
                if (actionBar.priority > priority) {
                    priorityActionBar = actionBar;
                    priority = actionBar.priority;
                }
            } 
            else {
                if (actionBar.durationInTicks != -1) {
                    actionBars.remove(position);
                }
                else {
                    if (actionBar.priority > priority) {
                        priorityActionBar = actionBar;
                        priority = actionBar.priority;
                    }
                }
            }
            position++;
        }
        return priorityActionBar;
    }

    public static void displayPriorityActionBar () {
        List<Player> players = PluginPlayer.getAllOnlinePlayers();
        for (Player player : players) {
            ActionBar actionBar = getPriorityActionBar (player);
            if (actionBar != null) {
                player.sendActionBar(
                    Component.text(actionBar.getText())
                    .color(actionBar.getColor())
                );
            }
        }
    }

    public static void displayAllLists () {
        for (var entry : allActionBars.entrySet()) {
            List<ActionBar> actionBars = entry.getValue();
            System.out.println("UUID : " + entry.getKey());
            for (ActionBar actionBar : actionBars) {
                System.out.print(actionBar.text);
            }
        }
    }

    public NamedTextColor getColor() {return color;}
    public String getText() {return text;}
    public int getDurationInticks() {return durationInTicks;}
}
