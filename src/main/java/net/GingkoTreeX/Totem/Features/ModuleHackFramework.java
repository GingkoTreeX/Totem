package net.GingkoTreeX.Totem.Features;

import net.GingkoTreeX.Totem.Controller.HackFramework;
import net.GingkoTreeX.Totem.Controller.ModuleManager;
import net.GingkoTreeX.Totem.Features.Module.AutoEat;
import net.GingkoTreeX.Totem.Features.Module.FastUse;
import net.GingkoTreeX.Totem.Features.Module.KillAura;
import net.GingkoTreeX.Totem.Gui.ClickGui;
import net.GingkoTreeX.Totem.Gui.Hud.Hud;

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
    }


    public ModuleManager getModuleByName(String name) {
        for (ModuleManager module : getModules()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
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
