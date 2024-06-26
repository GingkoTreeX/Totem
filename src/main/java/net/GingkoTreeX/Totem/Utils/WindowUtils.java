package net.GingkoTreeX.Totem.Utils;


import net.minecraft.client.MinecraftClient;

import java.util.Locale;

public class WindowUtils {
    public static void setTotemWindow() {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc != null && mc.getWindow() != null) {
            mc.getWindow().setTitle("[Totem-1.18] Debug:"+MinecraftClient.getInstance().fpsDebugString);
/*
            try {
                Identifier iconId16 = new Identifier("totem", "oipc.png");
                Identifier iconId32 = new Identifier("totem", "oipc.png");

                // 使用Minecraft的资源管理器来获取这些资源
                InputStream inputstream = MinecraftClient.getInstance().getResourceManager().getResource(iconId16).getInputStream();
                InputStream inputstream1 = MinecraftClient.getInstance().getResourceManager().getResource(iconId32).getInputStream();

                MinecraftClient.getInstance().getWindow().setIcon(inputstream, inputstream1); // 设置图标
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
*/

        }
    }

}
