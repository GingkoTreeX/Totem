package net.GingkoTreeX.totem.features;

import net.GingkoTreeX.totem.HackFramework;
import net.GingkoTreeX.totem.features.module.*;
import net.GingkoTreeX.totem.gui.ClickGui;
import net.GingkoTreeX.totem.gui.hud.Hud;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ModuleHackFramework extends HackFramework implements EventListener {

    private static ModuleHackFramework INSTANCE;

    public static ModuleHackFramework getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleHackFramework();
        }

        return INSTANCE;
    }

    private ModuleHackFramework() {
        super("Module",Category.COMBAT, true);
        // 在此处注册您的模块
        registerModule(new Hud());
        registerModule(new ClickGui());
        registerModule(new KillAura());
        registerModule(new FastUse());
        registerModule(new AutoEat());
        registerModule(new Criticals());
        registerModule(new AutoPursuit());
        registerModule(new AirJump());
        registerModule(new FastSneak());
        registerModule(new Lighting());
        registerModule(new SlowKillAura());
        registerModule(new GhostHand());
        registerModule(new Speed());
    }


    public FeatureModule getModuleByName(String name) {
        List<FeatureModule> modules=getModules();
        for (FeatureModule module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }


    public List<FeatureModule> getAllModule(){
       return super.getModules();
    }

    public List<FeatureModule> getCategoryModules(Category category) {
        List<FeatureModule> categoryModules = new ArrayList<>();
        for (FeatureModule module : getModules()) {
            if (module.getCategory() == category) {
                categoryModules.add(module);
            }
        }
        return categoryModules;
    }
}
