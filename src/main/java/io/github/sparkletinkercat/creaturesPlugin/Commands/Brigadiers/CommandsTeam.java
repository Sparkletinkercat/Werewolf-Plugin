package io.github.sparkletinkercat.creaturesPlugin.Commands.Brigadiers;

import io.github.sparkletinkercat.creaturesPlugin.Managers.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.java.JavaPlugin;
// import io.papermc.paper.command.brigadier.Commands;
// import org.bukkit.entity.Player;
// import java.util.List;

public class CommandsTeam {
    private final JavaPlugin plugin;

    public CommandsTeam (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> getTeamCommand() {

        Command command = new Command(plugin, "team");
        LiteralArgumentBuilder<CommandSourceStack> root = command.returnCommandRoot();


        

        
        command.createCommandRoot("createNewTeam", (player, name) -> {
            player.sendMessage("You created a new team.");
            TeamManager team = new TeamManager(plugin);
            team.createNewTeam(name, true);
        });

        
       
        return root;
    }

}
