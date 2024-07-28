package net.GingkoTreeX.totem.config;

import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.ModuleHackFramework;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class ConfigManager {
    private final File configFile;

    public ConfigManager(String configPath) {
        this.configFile = new File(configPath);
    }

    public void saveConfigs() {
        List<FeatureModule> modules = ModuleHackFramework.getInstance().getModules();
        try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
            for (FeatureModule module : modules) {
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
        List<FeatureModule> modules = ModuleHackFramework.getInstance().getModules();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // 更新条件
                    String moduleName = parts[0];
                    boolean enabled = Boolean.parseBoolean(parts[1]);
                    Integer keyBind = Integer.parseInt(parts[2]);
                    double configValue = Double.parseDouble(parts[3]);
                    for (FeatureModule module : modules) {
                        System.out.println(module.getName());
                        if (module.getName().equals(moduleName)) {
                            module.setEnabled(enabled); // 设置启用状态
                            module.setKeyBind(keyBind);
                            module.setConfig(configValue);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e){
            StackTraceElement[] s = e.getStackTrace();
            JOptionPane.showMessageDialog(null,s);
        }
    }
}
