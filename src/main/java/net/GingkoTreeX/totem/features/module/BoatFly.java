package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import org.lwjgl.glfw.GLFW;


public class BoatFly extends FeatureModule {
    public BoatFly() {
        super("BoatFly", Category.MOVEMENT , "我操高雅啊", GLFW.GLFW_KEY_F ,null,0);
    }
    MinecraftClient mc = MinecraftClient.getInstance();
    private ClientPlayerEntity player = mc.player;


    KeyBinding forwardKey = mc.options.forwardKey;
    KeyBinding backwardKey = mc.options.backKey;
    KeyBinding leftKey = mc.options.leftKey;
    KeyBinding rightKey = mc.options.rightKey;
    KeyBinding jumpKey = mc.options.jumpKey;
    KeyBinding sneakKey = mc.options.sneakKey;



    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {



    }

    @Override
    public void onUpdate() {
        /*
         X轴：正值向右，负值向左
         Y轴：正值向上，负值向下
         Z轴：正值向前，负值向后
        */
        Entity entity = player.getVehicle();
        if (player != null && entity != null && player.isAlive()) {
            double x = entity.getPos().x;
            double y = entity.getPos().y;
            double z = entity.getPos().z;

            if (forwardKey.isPressed()) {
                z += 0.1;
            } else if (backwardKey.isPressed()) {
                z -= 0.1;
            }

            if (rightKey.isPressed()) {
                x += 0.1;
            } else if (leftKey.isPressed()) {
                x -= 0.1;
            }

            if (jumpKey.isPressed()) {
                y += 0.1;
            } else if (sneakKey.isPressed()) {
                y -= 0.1;
            }
            entity.updatePosition(x,y,z);
        }


    }

    @Override
    public void onRender() {

    }
}
