package net.GingkoTreeX.Totem.Utils;


import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.win32.WinUser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.gl.WindowFramebuffer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.option.MouseOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.windows.WINDOWPLACEMENT;

import javax.swing.FocusManager;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.lang.reflect.Field;
import java.util.Locale;

import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

public class WindowUtils{

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static void setTotemWindow() {
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
