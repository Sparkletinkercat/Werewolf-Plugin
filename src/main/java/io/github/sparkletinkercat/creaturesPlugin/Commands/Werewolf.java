package io.github.sparkletinkercat.creaturesPlugin.Commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.NullMarked;
import org.bukkit.entity.Entity;


@NullMarked
public class Werewolf implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        Entity entity = source.getExecutor();
        entity.addScoreboardTag("werewolf");
    }
}