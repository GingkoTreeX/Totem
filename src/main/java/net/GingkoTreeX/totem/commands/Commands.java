package net.GingkoTreeX.totem.commands;

import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.ModuleHackFramework;
import net.GingkoTreeX.totem.gui.ClickGui;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

public class Commands {
    public static void onChat(String message, ClientChatEvent event) {
        if (message.startsWith(".")) {
            String[] parts = message.substring(1).split(" ");

            if ("bind".equals(parts[0])) {
                if (parts.length >= 3) {
                    String moduleName = parts[1];
                        int key = mapCharacterToGLFWKey(parts[2].charAt(0));
                        FeatureModule moduleToBind = findModuleByName(moduleName);
                        if (moduleToBind != null) {
                            if (parts[2].equalsIgnoreCase("None")){
                                moduleToBind.setKeyBind(null);
                                MessageUtils.addPinkChatMessage(moduleName+" is bind to "+ parts[2]);
                                return;
                            }
                            bind(moduleToBind, key);
                            MessageUtils.addPinkChatMessage(moduleName+" is bind to "+ parts[2]);
                        } else {
                            MessageUtils.addPinkChatMessage("Module Not Found");
                        }
                } else {
                    MessageUtils.addPinkChatMessage(".bind command requires at least two arguments: <Module> <key>");
                }
                event.setCanceled(true);
                event.setMessage(" ");
            }
        }
    }

    private static FeatureModule findModuleByName(String moduleName) {
       if (moduleName!=null){
           return ModuleHackFramework.getInstance().getModuleByName(moduleName);
       }
        return null;
    }

    private static void bind(FeatureModule module, int key){
      Class<? extends FeatureModule> moduleClass=module.getClass();
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
                break;
            }
        }
        return key;
    }
}
