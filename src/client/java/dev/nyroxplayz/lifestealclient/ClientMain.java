package dev.nyroxplayz.lifestealclient;

import dev.nyroxplayz.lifestealclient.hud.CustomHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
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
        LOGGER.info("LifestealClient HUD registered.");
    }
}
