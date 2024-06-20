package net.GingkoTreeX.Totem.Controller;

import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Features.ModuleManager;
import net.minecraft.network.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public abstract class HackFramework {

    private final String name;   // 框架的名称
    private final Category category;   // 框架的类型
    private final boolean visible;   // 框架是否可见

    private final Map<Class<? extends ModuleManager>, ModuleManager> modules = new HashMap<>();   // 模块列表

    // 构造函数，初始化 HackFramework 的各个成员变量
    public HackFramework(String name, Category category, boolean visible) {
        this.name = name;
        this.category = category;
        this.visible = visible;
    }
    // 获取框架的类型
    public Category getCategory() {
        return category;
    }

    // 获取框架是否可见
    public boolean isVisible() {
        return visible;
    }


    // 获取指定类型的模块
    public ModuleManager getModule(Class<? extends ModuleManager> moduleClass) {
        return modules.get(moduleClass);
    }

    // 获取所有模块
    public List<ModuleManager> getModules() {
        return new ArrayList<>(modules.values());
    }

    // 判断指定类型的模块是否启用
    public boolean isModuleEnabled(Class<? extends ModuleManager> moduleClass) {
        ModuleManager module = getModule(moduleClass);
        return module != null && module.isEnabled();
    }

    // 框架启用时的回调方法
    public void onEnable() {}

    // 框架禁用时的回调方法
    public void onDisable() {}

    // 框架更新时的回调方法
    public void onUpdate() {}

    // 框架渲染时的回调方法
    public void onRender() {}

    // 收到聊天消息时的回调方法
    public void onChatMessage(String message) {}

    // 收到网络数据包时的回调方法
    public void onPacketReceived(Packet<?> packet) {}

    // 发送网络数据包时的回调方法
    public void onPacketSent(Packet<?> packet) {}

    // 注册一个模块
    public void registerModule(ModuleManager module) {
        modules.put(module.getClass(), module);
    }

    // 注销一个模块
    public void unregisterModule(ModuleManager module) {
        modules.remove(module.getClass());
    }

    // 开关一个模块
    public void toggleModule(Class<? extends ModuleManager> moduleClass) {
        ModuleManager module = getModule(moduleClass);
        if (module != null) {
            module.setEnabled(!module.isEnabled());
        }
    }

    // 给模块设置按键绑定
    public void setModuleKeyBind(Class<? extends ModuleManager> moduleClass, int key) {
        ModuleManager module = getModule(moduleClass);
        if (module != null) {
            module.setKeyBind(key);
        }
    }

    // 获取框架的名称
    public String getName() {
        return name;
    }



}
