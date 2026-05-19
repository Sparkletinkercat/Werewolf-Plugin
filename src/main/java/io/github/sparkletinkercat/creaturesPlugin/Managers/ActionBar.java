package io.github.sparkletinkercat.creaturesPlugin.Managers;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import java.util.ArrayList;
import net.kyori.adventure.text.format.NamedTextColor;


public class ActionBar {
    private static List<ActionBar> actionBars = new ArrayList<ActionBar> ();
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

        actionBars.add(this);
        //System.out.println("Index of item = " + actionBars.indexOf(this) + " : Ticks in duration : " + actionBars.get(actionBars.indexOf(this)).getDurationInticks());
    }

    public static ActionBar getPriorityActionBar () {
        World world = Bukkit.getWorld("world");
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

    public void removeActionBar () {
        System.out.print("Size: " + actionBars.size());
        actionBars.remove(this);
        System.out.print("Size: " + actionBars.size());
    }

    public NamedTextColor getColor() {return color;}
    public String getText() {return text;}
    public int getDurationInticks() {return durationInTicks;}
}
