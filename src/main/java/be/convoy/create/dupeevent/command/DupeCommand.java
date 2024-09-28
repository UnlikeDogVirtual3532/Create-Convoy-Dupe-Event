package be.convoy.create.dupeevent.command;

import java.util.concurrent.TimeUnit;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import be.convoy.create.dupeevent.CreateConvoyDupeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class DupeCommand implements Command<ServerCommandSource> {
    private long dupeTimout;
    private static long lastDupeUse; 

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        dupeTimout = TimeUnit.SECONDS.toMillis(Long.parseLong(CreateConvoyDupeEvent.configMap.get("dupeTimeOut").toString()));

        if (context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

            if (player.getUuidAsString().equals((String)CreateConvoyDupeEvent.configMap.get("currentPlayer"))) {

                long currentTime = System.currentTimeMillis();

                if (currentTime - lastDupeUse > dupeTimout) {
                    ItemStack playerItems = player.getMainHandStack().copy();

                    player.giveItemStack(playerItems);

                    lastDupeUse = System.currentTimeMillis();
                } else {
                    context.getSource().sendError(Text.literal("You have to wait " +  TimeUnit.MILLISECONDS.toSeconds(dupeTimout - (currentTime - lastDupeUse)) + " to use dupe again."));
                }
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
