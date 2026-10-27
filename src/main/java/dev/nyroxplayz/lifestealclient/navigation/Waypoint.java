package dev.nyroxplayz.lifestealclient.navigation;

import java.util.Objects;
import java.util.UUID;

/**
 * A player-saved location. This model stores coordinates supplied by the player;
 * it does not search for hidden locations.
 */
public record Waypoint(
        UUID id,
        String name,
        String dimension,
        double x,
        double y,
        double z
) {
    public Waypoint {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(dimension, "dimension");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Waypoint name cannot be blank");
        }
    }

    public double distanceTo(double px, double py, double pz) {
        double dx = x - px;
        double dy = y - py;
        double dz = z - pz;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
