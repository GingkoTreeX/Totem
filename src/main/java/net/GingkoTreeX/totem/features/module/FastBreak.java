package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class FastBreak extends Module {

    public FastBreak() {
        super("FastBreak",Category.MOVEMENT,"很逆天",null,"挖掘速度",2.0);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()){
            if (MinecraftClient.getInstance().player != null) {
                PlayerEntity player=  MinecraftClient.getInstance().player;
                if (MinecraftClient.getInstance().interactionManager != null) {
                    MinecraftClient.getInstance().interactionManager.cancelBlockBreaking();
                }
            }
        }
    }

    @Override
    public void onRender() {

    }
}
