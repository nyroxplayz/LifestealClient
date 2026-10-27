package dev.nyroxplayz.lifestealclient.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import net.minecraft.core.BlockPos;

/**
 * Small, client-side information panel. It only displays the local player's
 * current coordinates and does not inspect hidden world data.
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
        int width = 154;
        int height = 56;

        graphics.fill(left, top, left + width, top + height, PANEL_BACKGROUND);
        graphics.fill(left, top, left + 3, top + height, ACCENT);

        graphics.drawString(minecraft.font, "LIFESTEAL CLIENT", left + 10, top + 7, ACCENT, false);
        graphics.drawString(minecraft.font, "Fabric  |  MC 1.21.11", left + 10, top + 20, SECONDARY_TEXT, false);

        BlockPos pos = minecraft.player.blockPosition();
        String coordinates = "XYZ  " + pos.getX() + "  " + pos.getY() + "  " + pos.getZ();
        graphics.drawString(minecraft.font, coordinates, left + 10, top + 37, PRIMARY_TEXT, false);
    }
}
