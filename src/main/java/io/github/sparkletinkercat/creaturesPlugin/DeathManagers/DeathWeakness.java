package io.github.sparkletinkercat.creaturesPlugin.DeathManagers;

import io.github.sparkletinkercat.creaturesPlugin.Implementations.*;
import io.github.sparkletinkercat.creaturesPlugin.Managers.*;

public class DeathWeakness extends DeathHandler<DeathWeakness> {
    private String deathType = "weakness";
    private int deathCount = 0;

    public String getDeathType () {return deathType;}
    public int getDeathCount () {return deathCount;}

    public void incrementDeathCount () {
        this.deathCount = deathCount + 1;
        
        // Get setting for maximum deaths for deathWeakness
        int maximumDeathCount = (int) Setting.getSettingValue("deathWeaknessMaximumCount");
        if (deathCount > maximumDeathCount) {
            this.runTrueDeath();
        }
    }
}
