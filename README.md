# LifestealClient

A client-only Fabric mod for Minecraft **1.21.11**, built with Java 21.

## Current features
- Custom top-left HUD panel with the client name and Minecraft version
- Displays the local player's current block coordinates
- Press **H** to toggle the custom HUD on or off
- Press **B** to save your current position as a waypoint
- Press **V** to print your saved waypoint list, including coordinates and dimension, in chat
- Press **N** to remove the most recently saved waypoint
- Waypoints persist between game sessions in the local Minecraft config folder
- HUD shows the most recently saved waypoint and distance when you're in the same dimension
- Keybinds can be changed in **Options → Controls → Key Binds → Lifesteal Client**
- Respects Minecraft's Hide HUD setting (F1)

Waypoint data is stored locally in `config/lifestealclient-waypoints.json`. Each entry is a position you explicitly saved; the mod does not scan hidden world data.

## Planned features
- Waypoint rename controls
- Alerts for approaching your own saved waypoints
- More client HUD widgets and customization options
- Utilities that work with blocks and information legitimately visible to the player

This project does not aim to bypass server anti-cheat or reveal hidden blocks, bases, or entities.

## Requirements
- Minecraft Java Edition 1.21.11
- Java Development Kit (JDK) 21
- Fabric Loader and Fabric API for Minecraft 1.21.11
- Gradle (the project uses Fabric Loom)

## Build
From the project root, run:

```bash
gradle build
```

The built mod JAR will be in `build/libs/`. If you use an IDE, import the repository as a Gradle project.

## Development status
Early development. The client entrypoint is `src/client/java/dev/nyroxplayz/lifestealclient/ClientMain.java`; the HUD renderer is `src/client/java/dev/nyroxplayz/lifestealclient/hud/CustomHud.java`.
