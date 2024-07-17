package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.Module;
import net.GingkoTreeX.totem.utils.AuraUtil;
import net.minecraft.client.MinecraftClient;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;


public class KillAura extends Module {

    private static int MINIMUM_DELAY_MS = 120;
    private long lastActionTime;
    public KillAura() {
        super("KillAura", Category.COMBAT, "Automatically attacks nearby enemies.",GLFW.GLFW_KEY_R,"reach",3.4);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onUpdate() {
        double reach = this.getConfig();
        if (this.isEnabled()) {
            if ((System.currentTimeMillis() - lastActionTime) < MINIMUM_DELAY_MS) {
                return;
            }
            PlayerEntity player= MinecraftClient.getInstance().player;
            if (player != null) {
                Entity target = AuraUtil.getNearestEntityFromReach(MinecraftClient.getInstance().player, reach);
                if (MinecraftClient.getInstance().interactionManager != null && target != null) {
                    MinecraftClient.getInstance().interactionManager.attackEntity(player, target);
                    player.swingHand(Hand.MAIN_HAND); // 模拟玩家挥动手臂
                    lastActionTime=System.currentTimeMillis();
                    if (System.currentTimeMillis() - player.getLastAttackedTime() > 1000L && AuraUtil.getNearestEntityFromReach(player,reach) != null){
                        MINIMUM_DELAY_MS += 10;
                    }else {
                        MINIMUM_DELAY_MS -= 10;
                    }
                }
                player.setHeadYaw(player.headYaw + 30L);//扭头，看起来很吊
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
