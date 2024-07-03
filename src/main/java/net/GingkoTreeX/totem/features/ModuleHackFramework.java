package net.GingkoTreeX.totem.features;

import net.GingkoTreeX.totem.controller.HackFramework;
import net.GingkoTreeX.totem.controller.ModuleManager;
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
    }


    public ModuleManager getModuleByName(String name) {
        for (ModuleManager module : getModules()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }


    public void setAllModulesLevel(int level) {
        for (ModuleManager module : ModuleHackFramework.getInstance().getAllModules()) {
            module.setModuleLevel(level);
        }
    }
    public void resetAllModulesLevel() {
        for (ModuleManager module : ModuleHackFramework.getInstance().getAllModules()) {
            module.setModuleLevel(1);
        }
    }

    public List<ModuleManager> getAllModules(){
       return super.getModules();
    }

    public List<ModuleManager> getCategoryModules(Category category) {
        List<ModuleManager> categoryModules = new ArrayList<>();
        for (ModuleManager module : getModules()) {
            if (module.getCategory() == category) {
                categoryModules.add(module);
            }
        }
        return categoryModules;
    }
}
