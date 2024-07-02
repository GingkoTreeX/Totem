package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.controller.ModuleManager;
import net.GingkoTreeX.totem.utils.AuraUtil;
import net.minecraft.client.MinecraftClient;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
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
            PlayerEntity player= MinecraftClient.getInstance().player;
            if (player != null) {
                PlayerEntity target=AuraUtil.attackNearestEntity(MinecraftClient.getInstance().player, 10);
                if (MinecraftClient.getInstance().interactionManager != null  &&  target!=null) {
                    MinecraftClient.getInstance().interactionManager.attackEntity(player, target);
                    player.resetLastAttackedTicks();
                    MinecraftClient.getInstance().interactionManager.attackEntity(player, target);
                    player.swingHand(Hand.MAIN_HAND); // 模拟玩家挥动手臂
                }
                player.setHeadYaw(player.headYaw + 30L);//扭头，很吊，看起来帅
                if (target != null) {
                    PlayerInteractEntityC2SPacket.attack(target, true);
                }

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
