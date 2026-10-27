package dev.nyroxplayz.lifestealclient.waypoints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Stores waypoints for the current game session only.
 * Waypoints are based on coordinates the player can already see.
 */
public final class WaypointManager {
    public record Waypoint(String name, BlockPos position, String dimension) {
    }

    private static final List<Waypoint> WAYPOINTS = new ArrayList<>();

    private WaypointManager() {
    }

    public static Waypoint saveCurrentPosition(Minecraft minecraft) {
        if (minecraft.player == null || minecraft.level == null) {
            return null;
        }

        BlockPos position = minecraft.player.blockPosition();
        ResourceKey<Level> dimensionKey = minecraft.level.dimension();
        Waypoint waypoint = new Waypoint(
                "Waypoint " + (WAYPOINTS.size() + 1),
                position,
                dimensionKey.location().toString()
        );
        WAYPOINTS.add(waypoint);
        return waypoint;
    }

    public static List<Waypoint> getWaypoints() {
        return Collections.unmodifiableList(WAYPOINTS);
    }

    public static Waypoint getLastWaypoint() {
        return WAYPOINTS.isEmpty() ? null : WAYPOINTS.get(WAYPOINTS.size() - 1);
    }

    public static double distanceToLastWaypoint(Minecraft minecraft) {
        Waypoint waypoint = getLastWaypoint();
        if (waypoint == null || minecraft.player == null || minecraft.level == null) {
            return -1;
        }

        String currentDimension = minecraft.level.dimension().location().toString();
        if (!waypoint.dimension().equals(currentDimension)) {
            return -1;
        }

        return minecraft.player.position().distanceTo(waypoint.position().getCenter());
    }
}
