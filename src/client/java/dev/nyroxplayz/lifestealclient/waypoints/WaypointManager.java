package dev.nyroxplayz.lifestealclient.waypoints;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Saves manually created waypoints to the local client's config directory.
 * It only stores coordinates at which the player explicitly presses the save key.
 */
public final class WaypointManager {
    public record Waypoint(String name, BlockPos position, String dimension) {
    }

    private static final List<Waypoint> WAYPOINTS = new ArrayList<>();
    private static final Path SAVE_FILE = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("lifestealclient-waypoints.json");

    static {
        load();
    }

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
        save();
        return waypoint;
    }

    public static List<Waypoint> getWaypoints() {
        return Collections.unmodifiableList(WAYPOINTS);
    }

    public static Waypoint getLastWaypoint() {
        return WAYPOINTS.isEmpty() ? null : WAYPOINTS.get(WAYPOINTS.size() - 1);
    }

    /** Removes the most recently saved waypoint and persists the updated list. */
    public static Waypoint deleteLastWaypoint() {
        if (WAYPOINTS.isEmpty()) {
            return null;
        }

        Waypoint removed = WAYPOINTS.remove(WAYPOINTS.size() - 1);
        save();
        return removed;
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

    private static void load() {
        if (!Files.exists(SAVE_FILE)) {
            return;
        }

        try (Reader reader = Files.newBufferedReader(SAVE_FILE)) {
            JsonElement root = JsonParser.parseReader(reader);
            if (!root.isJsonArray()) {
                return;
            }

            JsonArray array = root.getAsJsonArray();
            for (JsonElement element : array) {
                if (!element.isJsonObject()) {
                    continue;
                }

                JsonObject object = element.getAsJsonObject();
                if (!object.has("name") || !object.has("x") || !object.has("y")
                        || !object.has("z") || !object.has("dimension")) {
                    continue;
                }

                String name = object.get("name").getAsString();
                int x = object.get("x").getAsInt();
                int y = object.get("y").getAsInt();
                int z = object.get("z").getAsInt();
                String dimension = object.get("dimension").getAsString();
                WAYPOINTS.add(new Waypoint(name, new BlockPos(x, y, z), dimension));
            }
        } catch (Exception exception) {
            System.err.println("[LifestealClient] Could not load waypoints: " + exception.getMessage());
        }
    }

    private static void save() {
        JsonArray array = new JsonArray();
        for (Waypoint waypoint : WAYPOINTS) {
            JsonObject object = new JsonObject();
            object.addProperty("name", waypoint.name());
            object.addProperty("x", waypoint.position().getX());
            object.addProperty("y", waypoint.position().getY());
            object.addProperty("z", waypoint.position().getZ());
            object.addProperty("dimension", waypoint.dimension());
            array.add(object);
        }

        try {
            Path parent = SAVE_FILE.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (Writer writer = Files.newBufferedWriter(SAVE_FILE)) {
                com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
                gson.toJson(array, writer);
            }
        } catch (IOException exception) {
            System.err.println("[LifestealClient] Could not save waypoints: " + exception.getMessage());
        }
    }
}
