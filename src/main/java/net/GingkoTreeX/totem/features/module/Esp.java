package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.AuraUtil;
import net.GingkoTreeX.totem.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;


public class Esp extends FeatureModule {
    public Esp() {
        super("Esp", Category.RENDER, "",null, null, 0);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            if (this.isEnabled()) {
                if (mc.player != null) {
                    List<PlayerEntity> entities = AuraUtil.getPlayerFromWorld(mc.player);
                    for (PlayerEntity entity : entities) {
                        RenderUtil.renderEsp(event.getMatrixStack(), null ,entity,10L);
                    }
                }
            }
        }
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

    }

    @Override
    public void onRender() {

    }
}
