package net.GingkoTreeX.totem.features;

import net.GingkoTreeX.totem.HackFramework;
import net.GingkoTreeX.totem.features.module.*;
import net.GingkoTreeX.totem.ui.gui.ClickGui;
import net.GingkoTreeX.totem.ui.hud.Hud;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ModuleFramework extends HackFramework implements EventListener {

    private static ModuleFramework INSTANCE;

    public static ModuleFramework getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleFramework();
        }

        return INSTANCE;
    }

    private ModuleFramework() {
        super("Module", true);
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
        registerModule(new NoRepelled());
        registerModule(new Fly());
        registerModule(new Esp());
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
