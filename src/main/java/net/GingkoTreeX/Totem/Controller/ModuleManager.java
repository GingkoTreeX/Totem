package net.GingkoTreeX.Totem.Controller;


import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Features.ModuleHackFramework;
import net.GingkoTreeX.Totem.Utils.AddChatMessage;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public abstract class ModuleManager {

    private final String name;
    private final Category category;
    private final String description;

    private boolean enabled;
    private int keyBind;

    public ModuleManager(String name, Category category, String description, int defaultKeyBind) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.keyBind = defaultKeyBind;
    }
    public static void onUpdateAll() {
        for (ModuleManager module : ModuleHackFramework.getInstance().getModules()) {
            module.onUpdate();
            module.onRender();
        }
    }

    public abstract void onUpdate();

    public abstract void onRender();

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
           AddChatMessage.addGreenChatMessage("[+]"+this.getName()+" has been Enabled!");
        } else {
            onDisable();
           AddChatMessage.addRedChatMessage("[-]"+this.getName()+" has been Disabled!");
        }
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }


    public boolean isKeyDown(ModuleManager model) {
        // 如果 keyBind 为 0，则表示未绑定按键，直接返回 false
        keyBind=model.getKeyBind();
        if (keyBind == 0) {
            return false;
        }
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return GLFW.glfwGetKey(windowHandle, keyBind) == GLFW.GLFW_PRESS;
    }


    public void onEnable() {}

    public void onDisable() {}

}
