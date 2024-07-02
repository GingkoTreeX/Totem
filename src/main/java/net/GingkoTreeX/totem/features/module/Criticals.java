package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.controller.ModuleManager;
import net.GingkoTreeX.totem.features.Category;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

public class Criticals extends ModuleManager {
    public Criticals(){
        super("Criticals", Category.COMBAT, "Causes you to hit a critical hit with every sword", GLFW.GLFW_KEY_R);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (MinecraftClient.getInstance().player != null) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                player.setSprinting(true);
                player.setOnGround(false);
                player.setSneaking(true);
            }
        }
    }

    @Override
    public void onRender() {
    }
}
