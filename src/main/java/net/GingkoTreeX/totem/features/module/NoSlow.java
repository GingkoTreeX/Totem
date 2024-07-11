package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.Module;
import net.GingkoTreeX.totem.features.ModuleHackFramework;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;

public class NoSlow extends Module{


    public NoSlow() {
        super("NoSlow", Category.MOVEMENT, "when you eat or use your speed will as sprinting", null,null,0);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        PlayerEntity player= MinecraftClient.getInstance().player;
            if (MinecraftClient.getInstance().interactionManager != null) {
                if (player != null) {
                    MinecraftClient.getInstance().interactionManager.setGameMode(GameMode.SPECTATOR);
                }
            }
    }
    @Override
    public void onDisable() {
        super.onDisable();
        PlayerEntity player= MinecraftClient.getInstance().player;
            if (MinecraftClient.getInstance().interactionManager != null) {
                if (player != null) {
                    MinecraftClient.getInstance().interactionManager.setGameMode(GameMode.DEFAULT);
                }
            }
    }

    @Override
    public void onUpdate() {
        PlayerEntity player= MinecraftClient.getInstance().player;
        if (this.isEnabled()) {
            if (player != null) {

            }
        }
    }

    @Override
    public void onRender() {

    }
}
