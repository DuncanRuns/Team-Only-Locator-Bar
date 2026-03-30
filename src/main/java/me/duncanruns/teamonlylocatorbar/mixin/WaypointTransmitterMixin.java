package me.duncanruns.teamonlylocatorbar.mixin;

import me.duncanruns.teamonlylocatorbar.TeamOnlyLocatorBar;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.waypoints.WaypointTransmitter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WaypointTransmitter.class)
public interface WaypointTransmitterMixin {
    @Inject(method = "doesSourceIgnoreReceiver", at = @At("RETURN"), cancellable = true)
    private static void preventEnemyTracking(LivingEntity source, ServerPlayer receiver, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
        if (TeamOnlyLocatorBar.SPECTATORS_IGNORE_TEAMS && receiver.isSpectator()) {
            return;
        }
        if (!(source instanceof ServerPlayer sourcePlayer)) return;
        PlayerTeam receiverTeam = receiver.getTeam();
        PlayerTeam sourceTeam = sourcePlayer.getTeam();
        if (receiverTeam == null || sourceTeam == null) {
            cir.setReturnValue(true);
            return;
        }
        cir.setReturnValue(receiverTeam != sourceTeam);
    }
}