package be.convoy.create.dupeevent.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import be.convoy.create.dupeevent.CreateConvoyDupeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

@Mixin(ServerPlayerEntity.class)
public class PlayerOnDeathMixin {
    
    @Inject(method="onDeath", at=@At("TAIL"))
    private void onPlayerDeath(DamageSource source, CallbackInfo ci) {
        
        if ((Object) this instanceof ServerPlayerEntity player) {

            Entity attacker = source.getAttacker();
            if (attacker instanceof ServerPlayerEntity killer && player.getUuidAsString() == CreateConvoyDupeEvent.configMap.get("currentPlayer")) {
                player.sendMessage(Text.literal("You lost /dupe!"), false);
                killer.sendMessage(Text.literal("You acquired /dupe!"), false);

                CreateConvoyDupeEvent.configMap.put("currentPlayer", killer.getUuidAsString());
                CreateConvoyDupeEvent.configMap.put("currentPlayerName", killer.getName().getString());

            } else if (player.getUuidAsString() == CreateConvoyDupeEvent.configMap.get("currentPlayer")) {
                player.sendMessage(Text.literal("Be careful, if you lose /dupe you might lose the chance to be admin!"), false);
            }
        }
    }
}
