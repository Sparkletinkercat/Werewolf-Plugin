package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.sparkletinkercat.creaturesPlugin.Commands.*;

public class CommandsBeacon {
    private final JavaPlugin plugin;

    public CommandsBeacon (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> getBeaconCommand() {

        LiteralArgumentBuilder<CommandSourceStack> beaconRoot =
                Commands.literal("beacon");

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
                                System.out.println("Here");
                                
                                if (type == "Holy") {new UpdateBeaconDisplay (plugin,ctx.getSource(), 664);}
                                else if (type == "Neutral") {new UpdateBeaconDisplay (plugin,ctx.getSource(), 665);}
                                else if (type == "Evil") {new UpdateBeaconDisplay (plugin,ctx.getSource(), 666);}

                                return 1;
                            })
            );
        }

        beaconRoot.then(changeTypeCommand);

        return beaconRoot;
    }

}
