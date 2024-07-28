package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class Speed extends FeatureModule {

    public Speed() {
        super("Speed", Category.MOVEMENT,"加速", GLFW.GLFW_KEY_F,"Speed(倍率)", 1.5);
    }

    @Override
    public void onEnable() {
        if (this.isEnabled()) {
            PlayerEntity player = mc.player;
            if (player != null && !player.isOnGround()) {
                player.setVelocity(
                        player.getVelocity().getX() * getConfig(),
                        player.getVelocity().getY(),
                        player.getVelocity().getZ() * getConfig());
            }
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {

    }
}
