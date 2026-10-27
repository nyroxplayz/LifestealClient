# LifestealClient

A client-only Fabric mod for Minecraft **1.21.11**, built with Java 21.

## Planned features
- Lightweight client HUD and cooldown display
- Saved waypoints with distance and dimension information
- Alerts for approaching your own saved waypoints
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
Early starter scaffold. The client entrypoint is in `src/client/java/dev/nyroxplayz/lifestealclient/ClientMain.java`. Features will be added incrementally and tested against Minecraft 1.21.11.
