package dev.nova.module.modules;

import com.mojang.authlib.GameProfile;
import dev.nova.module.Module;
import net.minecraft.client.Options;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

/** Detaches the camera from your player so you can fly around visually. Your real player stays put. */
public class Freecam extends Module {
    private RemotePlayer cam;
    private ClientInput savedInput;
    private final double speed = 0.8; // blocks per tick

    public Freecam(int key) {
        super("Freecam", "Fly the camera around without moving your player.", key);
    }

    @Override
    protected void onEnable() {
        if (mc.player == null || mc.level == null) { setEnabled(false); return; }
        cam = new RemotePlayer(mc.level, new GameProfile(UUID.randomUUID(), "Freecam"));
        cam.setPos(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        cam.setYRot(mc.player.getYRot());
        cam.setXRot(mc.player.getXRot());
        syncPrev();
        savedInput = mc.player.input;
        mc.player.input = new ClientInput();   // blank input so the real player doesn't walk
        mc.setCameraEntity(cam);
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            if (savedInput != null) mc.player.input = savedInput;
            mc.setCameraEntity(mc.player);
        }
        cam = null;
        savedInput = null;
    }

    private void syncPrev() {
        cam.xo = cam.getX(); cam.yo = cam.getY(); cam.zo = cam.getZ();
        cam.xRotO = cam.getXRot(); cam.yRotO = cam.getYRot();
    }

    @Override
    public void onTick() {
        if (cam == null || mc.player == null) return;
        Options o = mc.options;
        float yaw = mc.player.getYRot();
        float pitch = mc.player.getXRot();

        double rad = Math.toRadians(yaw);
        Vec3 fwd = new Vec3(-Math.sin(rad), 0, Math.cos(rad));
        Vec3 right = new Vec3(-Math.cos(rad), 0, -Math.sin(rad));
        double f = (o.keyUp.isDown() ? 1 : 0) - (o.keyDown.isDown() ? 1 : 0);
        double s = (o.keyRight.isDown() ? 1 : 0) - (o.keyLeft.isDown() ? 1 : 0);
        double v = (o.keyJump.isDown() ? 1 : 0) - (o.keyShift.isDown() ? 1 : 0);

        Vec3 move = fwd.scale(f).add(right.scale(s)).add(0, v, 0);
        if (move.lengthSqr() > 0) move = move.normalize().scale(speed);

        syncPrev(); // keep last tick's values so rendering interpolates smoothly
        cam.setPos(cam.getX() + move.x, cam.getY() + move.y, cam.getZ() + move.z);
        cam.setYRot(yaw);
        cam.setXRot(pitch);
        cam.yRotO = yaw;
        cam.xRotO = pitch;
    }
}
