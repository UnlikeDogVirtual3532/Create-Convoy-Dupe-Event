package be.convoy.create.dupeevent;

import com.mojang.brigadier.CommandDispatcher;

import be.convoy.create.dupeevent.command.DupeCommand;
import be.convoy.create.dupeevent.command.GiveDupeCommand;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandRegistry {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("dupe").requires(source -> source.hasPermissionLevel(0))
                .executes(new DupeCommand()));
                
        dispatcher.register(CommandManager.literal("givedupe").requires(source -> source.hasPermissionLevel(3))
        .then(CommandManager.argument("target", EntityArgumentType.player())
        .executes(new GiveDupeCommand())));
    }
}
