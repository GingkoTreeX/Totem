package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Module;
import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.ModuleHackFramework;
import net.minecraft.client.MinecraftClient;

public class AntiTpaura extends Module {

    public AntiTpaura() {
        super("AntiTpaura",Category.MISC, "为了防止傻逼的tpaura暴虐你的killaura 所以请打开此项来薄纱tpaura的父母", null,null,0);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (MinecraftClient.getInstance().player != null) {
                if (MinecraftClient.getInstance().player.getLastAttackedTime() < 1000L) {
                    if (MinecraftClient.getInstance().player.getAttacker()!=null&&MinecraftClient.getInstance().player.distanceTo(MinecraftClient.getInstance().player.getAttacker()) > 12) {
                        if (!ModuleHackFramework.getInstance().getModule(AutoPursuit.class).isEnabled()) {
                            ModuleHackFramework.getInstance().getModule(AutoPursuit.class).setEnabled(true);
                        }
                    }
                } else {
                    if (ModuleHackFramework.getInstance().getModule(AutoPursuit.class).isEnabled()) {
                        ModuleHackFramework.getInstance().getModule(AutoPursuit.class).setEnabled(false);
                    }
                }
            } else {
                if (ModuleHackFramework.getInstance().getModule(AutoPursuit.class).isEnabled()) {
                    ModuleHackFramework.getInstance().getModule(AutoPursuit.class).setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onRender() {
    }

}
