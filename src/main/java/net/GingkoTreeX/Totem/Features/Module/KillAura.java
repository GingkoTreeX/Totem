package net.GingkoTreeX.Totem.Features.Module;

import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Controller.ModuleManager;
import net.GingkoTreeX.Totem.Utils.AuraUtil;
import net.minecraft.client.MinecraftClient;

import org.lwjgl.glfw.GLFW;


public class KillAura extends ModuleManager {

    public KillAura() {
        super("KillAura", Category.COMBAT, "Automatically attacks nearby enemies.",GLFW.GLFW_KEY_R);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (MinecraftClient.getInstance().player != null) {
                AuraUtil.attackNearestEntity(MinecraftClient.getInstance().player, 16);
                MinecraftClient.getInstance().player.setSprinting(true);
            }

        }
    }
    @Override
    public void onRender() {
    }



    @Override
    public void onDisable() {
        super.onDisable();
    }

}
