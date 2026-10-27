package dev.nyroxplayz.lifestealclient.hud;

import dev.nyroxplayz.lifestealclient.waypoints.WaypointManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import net.minecraft.core.BlockPos;

/**
 * Small, client-side information panel. It only displays the local player's
 * current coordinates and information about manually saved waypoints.
 */
public final class CustomHud {
    private static final int PANEL_BACKGROUND = 0xC0101420;
    private static final int ACCENT = 0xFF54E6C1;
    private static final int PRIMARY_TEXT = 0xFFF2F5FA;
    private static final int SECONDARY_TEXT = 0xFFB7C2D4;
    private static boolean enabled = true;

    private CustomHud() {
    }

    public static boolean toggleEnabled() {
        enabled = !enabled;
        return enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void render(GuiGraphics graphics, DeltaTracker tickCounter) {
        Minecraft minecraft = Minecraft.getInstance();

        if (!enabled || minecraft.options.hideGui || minecraft.player == null || minecraft.level == null) {
            return;
        }

        int left = 8;
        int top = 8;
        int width = 190;
        int height = 72;

        graphics.fill(left, top, left + width, top + height, PANEL_BACKGROUND);
        graphics.fill(left, top, left + 3, top + height, ACCENT);

        graphics.drawString(minecraft.font, "LIFESTEAL CLIENT", left + 10, top + 7, ACCENT, false);
        graphics.drawString(minecraft.font, "Fabric  |  MC 1.21.11", left + 10, top + 20, SECONDARY_TEXT, false);

        BlockPos pos = minecraft.player.blockPosition();
        String coordinates = "XYZ  " + pos.getX() + "  " + pos.getY() + "  " + pos.getZ();
        graphics.drawString(minecraft.font, coordinates, left + 10, top + 36, PRIMARY_TEXT, false);

        WaypointManager.Waypoint waypoint = WaypointManager.getLastWaypoint();
        if (waypoint == null) {
            graphics.drawString(minecraft.font, "Waypoint: press B to save", left + 10, top + 52, SECONDARY_TEXT, false);
        } else {
            double distance = WaypointManager.distanceToLastWaypoint(minecraft);
            String waypointLine = distance < 0
                    ? "Last: " + waypoint.name() + " (other dimension)"
                    : "Last: " + waypoint.name() + " - " + Math.round(distance) + "m";
            graphics.drawString(minecraft.font, waypointLine, left + 10, top + 52, SECONDARY_TEXT, false);
        }
    }
}
