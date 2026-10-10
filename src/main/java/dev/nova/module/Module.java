package dev.nova.module;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public abstract class Module {
    public final String name;
    public final String description;
    protected final Minecraft mc = Minecraft.getInstance();
    KeyMapping keyMapping; // set by ModuleManager
    private boolean enabled;
    private final int defaultKey;

    protected Module(String name, String description, int defaultKey) {
        this.name = name;
        this.description = description;
        this.defaultKey = defaultKey;
    }

    int defaultKey() { return defaultKey; }
    public boolean isEnabled() { return enabled; }
    public void toggle() { setEnabled(!enabled); }

    public void setEnabled(boolean value) {
        if (value == enabled) return;
        enabled = value;
        if (value) onEnable(); else onDisable();
    }

    public String keyName() {
        return keyMapping == null ? "" : keyMapping.getTranslatedKeyMessage().getString();
    }

    protected void onEnable() {}
    protected void onDisable() {}
    /** Called every client tick while enabled and in a world. */
    public void onTick() {}
}
