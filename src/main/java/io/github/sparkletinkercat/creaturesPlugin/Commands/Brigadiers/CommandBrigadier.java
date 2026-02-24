package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandBrigadier {

    private final JavaPlugin plugin;

    public CommandBrigadier(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerAll() {
        CommandsBeacon beaconManager = new CommandsBeacon(plugin);

        plugin.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                event -> {

                    LiteralArgumentBuilder<CommandSourceStack> root =
                            Commands.literal("spark");

                    root.then(beaconManager.getBeaconCommand());

                    root.then(
                            Commands.literal("tphere")
                                    .executes(ctx -> {
                                        ctx.getSource().getSender()
                                                .sendMessage("Teleporting...");
                                        return 1;
                                    })
                    );

                    root.then(
                            Commands.literal("killall")
                                    .executes(ctx -> {
                                        ctx.getSource().getSender()
                                                .sendMessage("Killed everything!");
                                        return 1;
                                    })
                    );

                    event.registrar().register(root.build());
                }
        );
    }
}
