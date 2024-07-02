package net.GingkoTreeX.totem.controller;


import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.ModuleHackFramework;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public abstract class ModuleManager {

    private final String name;
    private final Category category;
    private final String description;
    private int level;
    private boolean enabled;
    private int keyBind;

    public ModuleManager(String name, Category category, String description, @Nullable Integer defaultKeyBind) {
        this.name = name;
        this.category = category;
        this.description = description;
        if (defaultKeyBind!=null) {
            this.keyBind = defaultKeyBind;
        }
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
           MessageUtils.addGreenChatMessage("[+]"+this.getName()+" has been Enabled!");
        } else {
            onDisable();
           MessageUtils.addRedChatMessage("[-]"+this.getName()+" has been Disabled!");
        }
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public int getModuleLevel() {
        return this.level;
    }

    public void setModuleLevel(int moduleLevel) {
        this.level=moduleLevel;
    }


    public boolean isModuleKeyDown(ModuleManager model) {
        // 如果 keyBind 为 0，则表示未绑定按键，直接返回 false
            keyBind = model.getKeyBind();
            if (keyBind == 0) {
                return false;
            }
            long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
            return GLFW.glfwGetKey(windowHandle, keyBind) == GLFW.GLFW_PRESS;
    }
    public boolean isKeyDown(int key) {
        keyBind = key;
        if (keyBind == 0) {
            return false;
        }
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return GLFW.glfwGetKey(windowHandle, keyBind) == GLFW.GLFW_PRESS;
    }

    public void onEnable() {}

    public void onDisable() {}

}
