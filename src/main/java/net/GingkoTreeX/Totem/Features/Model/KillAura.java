package net.GingkoTreeX.Totem.Features.Model;

import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Features.ModuleManager;
import net.GingkoTreeX.Totem.Utils.AuraUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class KillAura extends ModuleManager {

    private static double lastX, lastY, lastZ; // 记录玩家受击时的位置

    private static boolean shouldRestorePosition = false; // 是否应该恢复位置
    private static Vec3d restorePosition = null; // 存储需要恢复的位置
    private static long teleportTime = -1L; // 记录预定传送的时间戳
    public KillAura() {
        super("KillAura", Category.COMBAT, "Automatically attacks nearby enemies.",GLFW.GLFW_KEY_R);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (MinecraftClient.getInstance().player != null) {
                new AuraUtil().attackNearestEntity(MinecraftClient.getInstance().player, 16);

            }

        }
    }
    @Override
    public void onRender() {
    }



    @Override
    public void onDisable() {

    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

    public static void drawCircle(double x, double y, double z, float width, double radius, float red, float green, float blue, float alpha) {
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor4f(red, green, blue, alpha);
        for (int i = 0; i <= 360; i++) {
            double posX = x - Math.sin(Math.toRadians(i)) * radius;
            double posZ = z + Math.cos(Math.toRadians(i)) * radius;
            GL11.glVertex3d(posX, y, posZ);
        }
        GL11.glEnd();
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glDisable(GL11.GL_SCISSOR_TEST); // Assuming 2884 refers to GL_SCISSOR_TEST constant
    }


    public static Color getColor() {

        return new Color(0xFF, 0, 0);
    }
}
