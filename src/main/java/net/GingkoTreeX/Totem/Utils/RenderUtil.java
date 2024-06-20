package net.GingkoTreeX.Totem.Utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class RenderUtil {
    private Color color;
    private int x, y;
    private long timestamp;
    private long duration;

    public RenderUtil(Color color, int x, int y, long duration) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.timestamp = System.currentTimeMillis();
        this.duration = duration;
    }

    public void render(MatrixStack matrices) {
        MinecraftClient mc = MinecraftClient.getInstance();

        // 计算时间差异并确定图形的显示时间
        long currentTimestamp = System.currentTimeMillis();
        long diff = currentTimestamp - timestamp;
        double progress = (double) diff / (double) duration;
        if (progress > 1.0) {
            progress = 1.0;
        }

        // 开始绘制
        int alpha = (int) (color.getAlpha() * (1.0 - progress));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        // 绘制矩形
        int width = 50;
        int height = 50;
        int border = 1;
        mc.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
        DrawableHelper.fill(matrices, x - border, y - border, x + width + border, y + height + border, 0xFF000000 | (alpha << 24));
        DrawableHelper.fill(matrices, x, y, x + width, y + height, MathHelper.packRgb(red, green, blue) | (alpha << 24));
    }
}
