package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class Lighting extends FeatureModule {

    public Lighting() {
        super("Lighting", Category.RENDER, "Gives the player night vision", null, null,0);
    }

    @Override
    public void onUpdate() {
        // 给予玩家夜视效果
        if (this.isEnabled() && mc.player != null) {
            StatusEffectInstance nightVisionEffect = new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1800);
            mc.player.addStatusEffect(nightVisionEffect);
        }
    }

    @Override
    public void onRender() {
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {}
}
