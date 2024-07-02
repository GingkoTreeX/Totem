package net.GingkoTreeX.totem;

import net.GingkoTreeX.totem.controller.ModuleManager;
import net.GingkoTreeX.totem.features.ModuleHackFramework;
import net.GingkoTreeX.totem.gui.ClickGui;
import net.GingkoTreeX.totem.gui.hud.Hud;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod("totem")
public class Totem {
    public MinecraftClient mc=MinecraftClient.getInstance();
    public Totem() {
        // Register the setup method
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClickGui());
        MinecraftForge.EVENT_BUS.register(new Hud());
    }

    private void setup(final FMLCommonSetupEvent event) {
        System.out.println(event.getIMCStream());
        mc.getWindow().setTitle("[Totem-1.18] Debug:" + MinecraftClient.getInstance().fpsDebugString);
    }
    //处理框架逻辑实现
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ModuleManager.onUpdateAll();
        if(MinecraftClient.getInstance().player!=null){
            if (!ModuleHackFramework.getInstance().getModule(Hud.class).isEnabled())
            {
                ModuleHackFramework.getInstance().getModule(Hud.class).setEnabled(true);
            }
        }
        mc.getWindow().setTitle("[Totem-1.18] Debug:" + MinecraftClient.getInstance().fpsDebugString);
    }
    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (MinecraftClient.getInstance().player==null){return;}
        for (ModuleManager module : ModuleHackFramework.getInstance().getModules()) {
            if (module.isModuleKeyDown(module)) {
                module.setEnabled(!module.isEnabled());
            }
        }
    }
}
