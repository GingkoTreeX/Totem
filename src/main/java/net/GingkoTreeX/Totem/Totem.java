package net.GingkoTreeX.Totem;

import com.mojang.logging.LogUtils;
import net.GingkoTreeX.Totem.Features.ModuleHackFramework;
import net.GingkoTreeX.Totem.Controller.ModuleManager;
import net.GingkoTreeX.Totem.Gui.ClickGui;
import net.GingkoTreeX.Totem.Gui.Hud.Hud;
import net.GingkoTreeX.Totem.Utils.WindowUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod("totem")
public class Totem {
    private static final Logger LOGGER = LogUtils.getLogger();
    public Totem() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClickGui());
        MinecraftForge.EVENT_BUS.register(new Hud());
    }

    private void setup(final FMLCommonSetupEvent event) {
        System.out.println(event.getIMCStream());;
        WindowUtils.setTotemWindow();
    }
    //处理框架逻辑实现
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ModuleManager.onUpdateAll();
        WindowUtils.setTotemWindow();
    }
    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (MinecraftClient.getInstance().player==null){return;}
        for (ModuleManager module : ModuleHackFramework.getInstance().getModules()) {
            if (module.isKeyDown(module)) {
                module.setEnabled(!module.isEnabled());
            }
        }
    }
}
