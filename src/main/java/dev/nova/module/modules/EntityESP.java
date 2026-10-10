package dev.nova.module.modules;

import dev.nova.module.Module;
import net.minecraft.client.player.AbstractClientPlayer;

/**
 * Outlines other players through walls using the vanilla glow outline.
 * Sets the client-side glowing flag every tick (no mixin needed, so it is less
 * likely to break between Minecraft versions).
 */
public class EntityESP extends Module {
    public EntityESP(int key) {
        super("ESP", "Outline other players through walls.", key);
    }

    @Override
    public void onTick() {
        if (mc.level == null) return;
        for (AbstractClientPlayer p : mc.level.players()) {
            if (p != mc.player) p.setGlowingTag(true);
        }
    }

    @Override
    protected void onDisable() {
        if (mc.level == null) return;
        for (AbstractClientPlayer p : mc.level.players()) {
            if (p != mc.player) p.setGlowingTag(false);
        }
    }
}
