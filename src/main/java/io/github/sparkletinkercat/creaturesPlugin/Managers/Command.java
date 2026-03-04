package io.github.sparkletinkercat.creaturesPlugin.Managers;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;


public class Command {
    private final JavaPlugin plugin;
    private LiteralArgumentBuilder<CommandSourceStack> commandRoot;

    public Command (JavaPlugin plugin, String commandRootName ) {
        this.plugin = plugin;
        commandRoot = Commands.literal(commandRootName);
    }

    public void createCommandRoot (String commandName, java.util.function.Consumer<Player> commandFunction) {
        LiteralArgumentBuilder<CommandSourceStack> subCommand =
                Commands.literal(commandName)
                        .executes(ctx -> {
                            if (!(ctx.getSource().getExecutor() instanceof Player player)) {
                                ctx.getSource().getSender()
                                        .sendMessage("Only players can run this command!");
                                return 0;
                            }

                            commandFunction.accept((Player)ctx.getSource().getExecutor());
                            return 1;
                        });
        commandRoot.then(subCommand);
    }

    public LiteralArgumentBuilder<CommandSourceStack> returnCommandRoot () {
        return commandRoot;
    }
}
