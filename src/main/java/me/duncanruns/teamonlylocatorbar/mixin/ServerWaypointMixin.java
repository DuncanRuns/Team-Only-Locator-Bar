package me.duncanruns.teamonlylocatorbar.mixin;

import me.duncanruns.teamonlylocatorbar.TeamOnlyLocatorBar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.waypoint.ServerWaypoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWaypoint.class)
public interface ServerWaypointMixin {
    @Inject(method = "cannotReceive", at = @At("RETURN"), cancellable = true)
    private static void preventEnemyTracking(LivingEntity source, ServerPlayerEntity receiver, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
        if (TeamOnlyLocatorBar.SPECTATORS_IGNORE_TEAMS && receiver.isSpectator()) {
            return;
        }
        if (!(source instanceof ServerPlayerEntity sourcePlayer)) return;
        Team receiverTeam = receiver.getScoreboardTeam();
        Team sourceTeam = sourcePlayer.getScoreboardTeam();
        if (receiverTeam == null || sourceTeam == null) {
            cir.setReturnValue(true);
            return;
        }
        cir.setReturnValue(receiverTeam != sourceTeam);
    }
}