package net.GingkoTreeX.Totem;

import com.mojang.logging.LogUtils;
import net.GingkoTreeX.Totem.Controller.commands.Commands;
import net.GingkoTreeX.Totem.Features.ModuleHackFramework;
import net.GingkoTreeX.Totem.Features.ModuleManager;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
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
    }
    private void setup(final FMLCommonSetupEvent event) {
        System.out.println(event.getIMCStream());;
    }
    //处理框架逻辑实现
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
            ModuleManager.onUpdateAll();
    }
    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        for (ModuleManager module : ModuleHackFramework.getInstance().getModules()) {
            if (module.isKeyDown(module)) { // 更具描述性的方法名
                module.setEnabled(!module.isEnabled());
            }
        }
    }



    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String rawMessage = event.getMessage().getString();
        if (!rawMessage.startsWith(".") || event.isCanceled()) {
            return;
        }
        new Commands().getCommands(rawMessage);
    }


}
