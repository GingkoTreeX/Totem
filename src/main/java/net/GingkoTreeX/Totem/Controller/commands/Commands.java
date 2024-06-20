package net.GingkoTreeX.Totem.Controller.commands;


import net.GingkoTreeX.Totem.Features.ModuleHackFramework;
import net.GingkoTreeX.Totem.Features.ModuleManager;
import net.GingkoTreeX.Totem.Utils.AddChatMessage;

public class Commands {

    public void getCommands(String message) {
        // 检查消息是否以 ".bind" 开头
        if (message.startsWith(".bind ")) {
            // 移除前缀 ".bind "
            String commandContent = message.substring(6);
            // 分割剩余内容至两个部分：模块名 和 键值
            String[] parts = commandContent.split("\\s+", 2);

            // 检查分割结果
            if (parts.length == 2) {
                String moduleName = parts[0];
                int keyValue;

                // 尝试将第二个参数转换为整数
                try {
                    keyValue = Integer.parseInt(parts[1]);
                } catch (NumberFormatException nfe) {
                    new AddChatMessage().addGreenChatMessage("Invalid key value provided.");
                    return;
                }

                // 获取对应的模块实例
                ModuleManager module = ModuleHackFramework.getInstance().getModuleByName(moduleName);

                // 检查模块是否存在
                if (module != null) {
                    // 设置模块的按键绑定
                    ModuleHackFramework.getInstance().setModuleKeyBind(module.getClass(), keyValue);
                    new AddChatMessage().addGreenChatMessage("Bound key '" + keyValue + "' to module '" + moduleName + "'.");
                } else {
                    new AddChatMessage().addGreenChatMessage("Module '" + moduleName + "' does not exist.");
                }
            } else {
                new AddChatMessage().addGreenChatMessage("Incorrect number of arguments given.");
            }
        }
    }

}
