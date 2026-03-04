package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import java.util.List;

public class CommandsBeacon {
    private final JavaPlugin plugin;

    public CommandsBeacon (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> getBeaconCommand() {

        Command command = new Command(plugin, "beacon");
        LiteralArgumentBuilder<CommandSourceStack> root = command.returnCommandRoot();

        // Register larger tree
        List<String> beaconTypes = List.of("Neutral", "Holy", "Evil");

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

                                // Replace with your logic to update the beacon model
                                Beacon beacon = new Beacon(plugin);
                                
                                if (type == "Holy") {beacon.changeBeaconDisplay (ctx.getSource(), 664);}
                                else if (type == "Neutral") {beacon.changeBeaconDisplay (ctx.getSource(), 665);}
                                else if (type == "Evil") {beacon.changeBeaconDisplay (ctx.getSource(), 666);}

                                return 1;
                            })
            );
        }

        

        
        command.createCommandRoot("registerBeacon", player -> {
            player.sendMessage("You just registered a beacon!");
            Beacon beacon = new Beacon(plugin);
            beacon.registerBeacon (player, "Oakhurst Town");
        });

        root.then(changeTypeCommand);
        
       
        return root;
    }

}
