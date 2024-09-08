package be.convoy.create.dupeevent.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import be.convoy.create.dupeevent.CreateConvoyDupeEvent;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class GiveDupeCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");

        CreateConvoyDupeEvent.configMap.put("currentPlayer", target.getUuidAsString());
        CreateConvoyDupeEvent.configMap.put("currentPlayerName", target.getName().getString());
        
        return 1;
    }
}
