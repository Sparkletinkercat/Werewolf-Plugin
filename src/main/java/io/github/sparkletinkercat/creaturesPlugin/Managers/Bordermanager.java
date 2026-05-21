package io.github.sparkletinkercat.creaturesPlugin.Managers;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public class BorderManager {
    private static int borderCenterX = 0;
    private static int borderCenterZ = 0;
    private static int borderRadius = 0;

    public BorderManager () {}

    public static void getBorderSettings () {
        Setting borderCenterXValue = Setting.getSettingByName("borderCenterX");
        borderCenterX = (int) borderCenterXValue.getValue();

        Setting borderCenterZValue = Setting.getSettingByName("borderCenterZ");
        borderCenterZ = (int) borderCenterZValue.getValue();

        Setting borderRadiusValue = Setting.getSettingByName("borderRadius");
        borderRadius = (int) borderRadiusValue.getValue();
    }

    public static int getNorthBorder () {return borderCenterZ - borderRadius;}
    public static int getEastBorder () {return borderCenterX + borderRadius;}
    public static int getSouthBorder () {return borderCenterZ + borderRadius;}
    public static int getWestBorder () {return borderCenterX - borderRadius;}

    public static boolean isWithinBorder (Player player) {
        Location loc = player.getLocation();

        int x = (int) loc.getX();
        int z = (int) loc.getZ();
        
        if (x > getEastBorder() || x < getWestBorder()) {return false;} 
        else if (z > getSouthBorder() || z < getNorthBorder()) {return false;}

        return true;
    }

    public static void cancelMovementToBorder (Player player, PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null) {return;}

        double dX = to.getX() - from.getX();
        double dZ = to.getZ() - from.getZ();

        boolean headingOutsideBorder = false;
        if (dX > 0 && (int) to.getX() > getEastBorder()) {headingOutsideBorder = true;}
        else if (dX < 0 && (int) to.getX() < getWestBorder()) {headingOutsideBorder = true;}
        else if (dZ > 0 && (int) to.getZ() > getSouthBorder()) {headingOutsideBorder = true;}
        else if (dZ < 0 && (int) to.getZ() < getNorthBorder()) {headingOutsideBorder = true;}
        
        if (headingOutsideBorder == true) {
            event.setTo(from);
            player.sendMessage("The barrier is still up");
        }
    }
}
