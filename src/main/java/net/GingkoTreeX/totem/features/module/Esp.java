package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;


public class Esp extends FeatureModule {
    public Esp() {
        super("Esp", Category.RENDER, "",null, null, 0);
    }



    @Override
    public void onEnable() {
        MinecraftClient.getInstance().getEntityRenderDispatcher().setRenderHitboxes(true);
    }

    @Override
    public void onDisable() {
        MinecraftClient.getInstance().getEntityRenderDispatcher().setRenderHitboxes(false);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null) {
            StatusEffectInstance glowingVisionEffect = new StatusEffectInstance(StatusEffects.GLOWING, 180);
            // 创建一个新的列表来存储PlayerEntity实例
            List<Entity> entities = WorldUtils.getEntityFromWorld(mc.player);
            List<PlayerEntity> playerEntities = new ArrayList<>();
            for (Entity entity : entities) {
                // 检查当前实体是否是PlayerEntity类型
                if (entity instanceof PlayerEntity) {
                    // 强制转换并添加到playerEntities列表中
                    playerEntities.add((PlayerEntity) entity);
                }
            }
            for (PlayerEntity playerentity: playerEntities){
                playerentity.addStatusEffect(glowingVisionEffect);
                playerentity.setCustomNameVisible(true);
                playerentity.setInvisible(false);
            }
        }
    }

    @Override
    public void onRender() {

    }
}
