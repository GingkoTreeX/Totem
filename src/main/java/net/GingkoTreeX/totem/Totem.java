package net.GingkoTreeX.totem;

import net.GingkoTreeX.totem.commands.Commands;
import net.GingkoTreeX.totem.config.ConfigManager;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.ModuleHackFramework;
import net.GingkoTreeX.totem.gui.ClickGui;
import net.GingkoTreeX.totem.gui.hud.Hud;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.swing.*;

@Mod("totem")
public class Totem {
    private final MinecraftClient mc=MinecraftClient.getInstance();
    private boolean i = true;
    private int b = 0;
    private static final String PATH = "Totem_Module_Setting.txt";
    public Totem() {
        System.setProperty("java.awt.headless", "false");
        // Register the setup method
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClickGui());
        MinecraftForge.EVENT_BUS.register(new Hud());
    }


    private void setup(final FMLCommonSetupEvent event) {
        System.out.println(event.getIMCStream());
    }


    //处理框架逻辑实现
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (i){
            setConfig();
            setTitle();
            i=false;
        }
     
        try {
            FeatureModule.onUpdateAll();
            setTitle();
        }catch (Exception e){
            StackTraceElement[] s = e.getStackTrace();
            JOptionPane.showMessageDialog(null,"功能抛出异常 已关闭所有功能 " +
                                            "以下为异常椎栈\n" +s);
            for (FeatureModule module : ModuleHackFramework.getInstance().getModules()) {
                module.setEnabled(false);
            }
        }
    }
    @SubscribeEvent
    public void onChat(ClientChatEvent event){
        String message= event.getMessage();
        Commands.onChat(message,event);
    }

    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (MinecraftClient.getInstance().player==null){return;}
        for (FeatureModule module : ModuleHackFramework.getInstance().getModules()) {
            if (module.isModuleKeyDown(module)) {
                module.setEnabled(!module.isEnabled());
            }
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> new ConfigManager(PATH).saveConfigs()));
    }


    private void setConfig(){
        //获取配置信息
        ConfigManager configManager = new ConfigManager(PATH);
        configManager.loadConfigs();
        //自动启动hud（可删除）
        if(MinecraftClient.getInstance().player!=null){
            if (!ModuleHackFramework.getInstance().getModule(Hud.class).isEnabled())
            {
                ModuleHackFramework.getInstance().getModule(Hud.class).setEnabled(true);
            }
        }
    }
    private void setTitle(){
        mc.getWindow().setTitle("[Totem] " + MinecraftClient.getInstance().getGameVersion());
    }
}
