package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.sparkletinkercat.creaturesPlugin.Listeners.*;

public class CommandBrigadier {

    private final JavaPlugin plugin;
    private final BeaconListener beaconListener;

    public CommandBrigadier(JavaPlugin plugin, BeaconListener beaconListener) {
        this.plugin = plugin;
        this.beaconListener = beaconListener;
    }

    public void registerAll() {
        CommandsBeacon beaconManager = new CommandsBeacon(plugin);
        CommandsGame gameManager = new CommandsGame(plugin,beaconListener);
        CommandsTeam teamManager = new CommandsTeam(plugin);

        plugin.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                event -> {

                    LiteralArgumentBuilder<CommandSourceStack> root =
                            Commands.literal("admin");

                    root.then(beaconManager.getBeaconCommand());
                    root.then(gameManager.getGameCommand());
                    root.then(teamManager.getTeamCommand());

                    event.registrar().register(root.build());
                }
        );
    }
}
