package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class Criticals extends Module {
    public Criticals(){
        super("Criticals", Category.COMBAT, "Causes you to hit a critical hit with every sword", null,"jumpHeight",0.22);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            PlayerEntity player = MinecraftClient.getInstance().player;
                if (player != null && player.isOnGround()) {
                    player.setPos(player.getX() + 0.01, player.getY() + getConfig(), player.getZ());
                }
        }
    }

    @Override
    public void onRender() {
    }
}
