package dev.nyroxplayz.lifestealclient;

import com.mojang.blaze3d.platform.InputConstants;
import dev.nyroxplayz.lifestealclient.hud.CustomHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client-only entrypoint for LifestealClient.
 */
public final class ClientMain implements ClientModInitializer {
    public static final String MOD_ID = "lifestealclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(
                Identifier.fromNamespaceAndPath(MOD_ID, "custom_hud"),
                CustomHud::render
        );

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(MOD_ID, "main")
        );

        KeyMapping toggleHudKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.lifestealclient.toggle_hud",
                        InputConstants.Type.KEYSYM,
                        InputConstants.KEY_H,
                        category
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleHudKey.consumeClick()) {
                boolean enabled = CustomHud.toggleEnabled();
                if (client.player != null) {
                    client.player.displayClientMessage(
                            Component.literal("Lifesteal HUD: " + (enabled ? "ON" : "OFF")),
                            true
                    );
                }
            }
        });

        LOGGER.info("LifestealClient HUD and toggle key registered.");
    }
}
