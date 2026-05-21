package io.github.sparkletinkercat.creaturesPlugin.Managers;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;

public class Bordermanager {
    private static int borderCenterX = 0;
    private static int borderCenterZ = 0;
    private static int borderRadius = 0;

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
}
