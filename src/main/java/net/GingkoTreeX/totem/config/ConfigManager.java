package net.GingkoTreeX.totem.config;

import net.GingkoTreeX.totem.features.Module;
import net.GingkoTreeX.totem.features.ModuleHackFramework;

import java.io.*;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class ConfigManager {
    private final File configFile;

    public ConfigManager(String configPath) {
        this.configFile = new File(configPath);
    }

    public void saveConfigs() {
        List<Module> modules = ModuleHackFramework.getInstance().getModules();
        try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
            for (Module module : modules) {
                writer.println(
                        module.getName() + "," +
                                module.isEnabled() + "," + // 添加启用状态
                                module.getKeyBind() + "," +
                                module.getConfig()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfigs() {
        List<Module> modules = ModuleHackFramework.getInstance().getModules();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // 更新条件，现在有四个部分
                    String moduleName = parts[0];
                    boolean enabled = Boolean.parseBoolean(parts[1]);
                    int keyBind = Integer.parseInt(parts[2]);
                    double configValue = Double.parseDouble(parts[3]);

                    for (Module module : modules) {
                        if (module.getName().equals(moduleName)) {
                            module.setEnabled(enabled); // 设置启用状态
                            module.setKeyBind(keyBind);
                            module.setConfig(configValue);
                            break;
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
