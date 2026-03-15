package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.ArrayList;

public class CommandsBeacon {
    private final JavaPlugin plugin;

    public CommandsBeacon (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> getBeaconCommand() {

        Command command = new Command(plugin, "beacon");
        LiteralArgumentBuilder<CommandSourceStack> root = command.returnCommandRoot();


        Beacon beacon = new Beacon (plugin);
        Beacon.BeaconType beaconDisplays = beacon.new BeaconType();
        List<String> beaconTypes = beaconDisplays.getAllBeaconTypeNames();


        LiteralArgumentBuilder<CommandSourceStack> changeTypeCommand =
                Commands.literal("changeBeaconType");

        for (String type : beaconTypes) {
            changeTypeCommand.then(
                    Commands.literal(type)
                            .executes(ctx -> {
                                if (!(ctx.getSource().getExecutor() instanceof Player player)) {
                                    ctx.getSource().getSender()
                                            .sendMessage("Only players can run this command!");
                                    return 0;
                                }

                                beacon.changeBeaconDisplay (ctx.getSource(), beaconDisplays.getTypeByBeaconTypeName (type));

                                return 1;
                            })
            );
        }

        

        
        command.createCommandRoot("registerBeacon", (player, name) -> {
            beacon.registerBeacon (player, name);
        });

        root.then(changeTypeCommand);
        
       
        return root;
    }

}
