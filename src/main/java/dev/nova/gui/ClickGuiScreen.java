package dev.nova.gui;

import dev.nova.NovaClient;
import dev.nova.module.Module;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClickGuiScreen extends Screen {
    private static final int X = 20, Y = 20, W = 170, H = 16;

    public ClickGuiScreen() { super(Component.literal("Nova Client")); }

    @Override
    public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta) {
        super.extractRenderState(g, mouseX, mouseY, delta);
        g.fill(X, Y, X + W, Y + H, 0xFF111111);
        g.text(this.font, "Nova Client", X + 5, Y + 4, 0xFFFFFFFF);

        List<Module> mods = NovaClient.modules().all();
        for (int i = 0; i < mods.size(); i++) {
            Module m = mods.get(i);
            int y = Y + H * (i + 1);
            boolean hover = mouseX >= X && mouseX <= X + W && mouseY >= y && mouseY < y + H;
            int bg = m.isEnabled() ? (hover ? 0xFF3FA044 : 0xFF2E7D32) : (hover ? 0xFF444444 : 0xFF2A2A2A);
            g.fill(X, y, X + W, y + H, bg);
            g.text(this.font, m.name, X + 5, y + 4, 0xFFFFFFFF);
            String key = m.keyName();
            g.text(this.font, key, X + W - 5 - this.font.width(key), y + 4, 0xFFAAAAAA);
            if (hover) g.text(this.font, m.description, X + W + 8, y + 4, 0xFFDDDDDD);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        List<Module> mods = NovaClient.modules().all();
        for (int i = 0; i < mods.size(); i++) {
            int y = Y + H * (i + 1);
            if (event.x() >= X && event.x() <= X + W && event.y() >= y && event.y() < y + H) {
                if (event.button() == 0 && this.minecraft != null && this.minecraft.player != null) {
                    mods.get(i).toggle();
                }
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
