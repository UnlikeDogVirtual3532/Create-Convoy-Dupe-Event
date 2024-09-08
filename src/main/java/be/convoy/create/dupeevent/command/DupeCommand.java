package be.convoy.create.dupeevent.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import be.convoy.create.dupeevent.CreateConvoyDupeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class DupeCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        if (context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

            if (player.getUuidAsString().equals(CreateConvoyDupeEvent.configMap.get("currentPlayer"))) {
                ItemStack playerItems = player.getMainHandStack().copy();

                player.giveItemStack(playerItems);
            } else {
                
                try {
                    context.getSource().sendError(Text.literal("You are not able to use /dupe. " + CreateConvoyDupeEvent.configMap.get("currentPlayerName") + " has dupe at this moment." ));
                } catch (Exception e) {
                    context.getSource().sendError(Text.literal("Nobody currently has dupe!"));
                }
            }
        }
        else {
            context.getSource().sendError(Text.literal("Called /dupe as non-player"));
        }
        
       return 1;
    }
}
