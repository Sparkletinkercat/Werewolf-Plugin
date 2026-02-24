
package io.github.sparkletinkercat.creaturesPlugin.Commands;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.NullMarked;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Material;


@NullMarked
public class UpdateBeaconDisplay {
    private final JavaPlugin plugin;

    public UpdateBeaconDisplay(JavaPlugin plugin,CommandSourceStack source, int type) {
        this.plugin = plugin;
        Beacon beacon = new Beacon (this.plugin);

        Entity entity = source.getExecutor();
        Player player = (Player) entity;
    
        Block target = player.getTargetBlockExact(20);
        if (target != null && target.getType() == Material.BARRIER) {
            beacon.changeBeaconDisplay(type, target.getX(),target.getY(),target.getZ());
        } else {
            player.sendMessage("No beacon block in range");
        }
    }

}