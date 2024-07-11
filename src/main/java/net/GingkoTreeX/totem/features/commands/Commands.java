package net.GingkoTreeX.totem.features.commands;

import net.GingkoTreeX.totem.features.Module;
import net.GingkoTreeX.totem.features.ModuleHackFramework;
import net.GingkoTreeX.totem.gui.ClickGui;
import net.GingkoTreeX.totem.utils.MessageUtils;
import org.lwjgl.glfw.GLFW;

public class Commands {
    public static void onChat(String message) {
        if (message.startsWith(".")) {
            String[] parts = message.substring(1).split(" "); // 移除开头的句点并按空格分割命令

            if ("bind".equals(parts[0])) {
                // 检查第一个部分是否为"bind"
                if (parts.length >= 3) { // 确保有足够的参数
                    String moduleName = parts[1];
                        int key = mapCharacterToGLFWKey(parts[2].charAt(0)); // 尝试将第二个参数转换为整数作为键位
                        Module moduleToBind = findModuleByName(moduleName);
                        if (moduleToBind != null) {
                            bind(moduleToBind, key);
                            MessageUtils.addPinkChatMessage(moduleName+" is bind to "+ parts[2]);
                        } else {
                            MessageUtils.addPinkChatMessage("Module Not Found");
                        }
                } else {
                    MessageUtils.addPinkChatMessage(".bind command requires at least two arguments: <Module> <key>");
                }
            }
        }
    }

    // 假设的模块查找方法，实际应用中需要根据你的Mod架构实现
    private static Module findModuleByName(String moduleName) {
       if (moduleName!=null){
           return ModuleHackFramework.getInstance().getModuleByName(moduleName);
       }
        return null;
    }

    private static void bind(Module module, int key){
      Class<? extends Module> moduleClass=module.getClass();
      ModuleHackFramework.getInstance().setModuleKeyBind(moduleClass,key);
    }
    private void openGui(boolean enable){
        ModuleHackFramework.getInstance().getModule(ClickGui.class).setEnabled(enable);
    }

    private static int mapCharacterToGLFWKey(char character) {
        int key = 0;
        char mappedChar = ' ';
        for (int keyCode = GLFW.GLFW_KEY_SPACE; keyCode <= GLFW.GLFW_KEY_LAST; keyCode++) {
            String mappedStr = GLFW.glfwGetKeyName(keyCode, GLFW.glfwGetKeyScancode(keyCode));

            if (mappedStr != null && !mappedStr.isEmpty()) {
                mappedChar = mappedStr.charAt(0);
            }

            key = keyCode;
            if (Character.toUpperCase(mappedChar) == Character.toUpperCase(character)) {
                break; // 只需找到第一个匹配项
            }
        }
        return key;
    }
}
