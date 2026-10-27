package dev.nyroxplayz.lifestealclient;

import net.fabricmc.api.ClientModInitializer;
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
        LOGGER.info("LifestealClient initialized.");
    }
}
