# Nova Client (Fabric, Minecraft 26.2)

Minecraft 26.x is unobfuscated and uses Mojang's own class names, and it needs Java 25.

## Build / run
1. Install JDK 25.
2. Generate a fresh project at https://fabricmc.net/develop/template/ (pick 26.2). It includes the Gradle wrapper (Gradle 9.3+).
3. Replace its `src/`, `build.gradle`, `gradle.properties`, `settings.gradle` with the ones here.
4. `./gradlew runClient` to test, `./gradlew build` for the jar (build/libs/novaclient-0.1.0.jar).

## Use
- Right Shift: click GUI
- Freecam: F6. ESP: unbound, set it in Options > Controls > Key Binds > Nova Client

## Add a module
Extend `dev.nova.module.Module`, override onEnable/onDisable/onTick, then add one
`MODULES.register(new YourModule(GLFW.GLFW_KEY_UNKNOWN));` line in NovaClient.java.
