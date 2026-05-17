package io.github.sparkletinkercat.creaturesPlugin.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setting {
    private static List<Setting> settings = new ArrayList<Setting> ();

    String name = null;
    String path = null;
    Object value = null;
    Object defaultValue = null;

    public Setting (String path, Object value) {
        this.value = value;
        this.path = path;
        this.name = path.substring(path.indexOf('.') + 1);
    }

    public static void convertToSettings (HashMap<String,Object> fileSections) {
        settings.clear();

        for (Map.Entry<String, Object> entry : fileSections.entrySet()) {
            settings.add(new Setting(entry.getKey(),entry.getValue()));
        }
    }

    public String getName () {return name;}
    public Object getValue () {return value;}

    public static Object getSettingValue (String settingName) {
        for (Setting setting : settings) {
            if (settingName.equals(setting.getName())) {
                return setting.getValue();
            }
        }
        return null;
    }
}
