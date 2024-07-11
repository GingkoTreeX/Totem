package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Module;
import net.GingkoTreeX.totem.features.Category;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class Criticals extends Module {
    public Criticals(){
        super("Criticals", Category.COMBAT, "Causes you to hit a critical hit with every sword", null,null,0);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (MinecraftClient.getInstance().player != null) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                    player.setVelocity(0,0.1,0);
                    player.setVelocity(0,-0.1,0);
            }
        }
    }

    @Override
    public void onRender() {
    }
}
