package net.GingkoTreeX.totem.utils;


import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.entity.PartEntity;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static void renderEsp(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta) {
        Box aabb = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
        WorldRenderer.drawBox(matrices, vertices, aabb, 1.0F, 1.0F, 1.0F, 1.0F);
        if (entity.isMultipartEntity()) {
            double d0 = -MathHelper.lerp(tickDelta, entity.lastRenderX, entity.getX());
            double d1 = -MathHelper.lerp(tickDelta, entity.lastRenderY, entity.getY());
            double d2 = -MathHelper.lerp(tickDelta, entity.lastRenderZ, entity.getZ());
            PartEntity[] var11 = entity.getParts();
            int var12 = var11.length;

            for (int var13 = 0; var13 < var12; ++var13) {
                PartEntity<?> enderdragonpart = var11[var13];
                matrices.push();
                double d3 = d0 + MathHelper.lerp(tickDelta, enderdragonpart.lastRenderX, enderdragonpart.getX());
                double d4 = d1 + MathHelper.lerp(tickDelta, enderdragonpart.lastRenderY, enderdragonpart.getY());
                double d5 = d2 + MathHelper.lerp(tickDelta, enderdragonpart.lastRenderZ, enderdragonpart.getZ());
                matrices.translate(d3, d4, d5);
                WorldRenderer.drawBox(matrices, vertices, enderdragonpart.getBoundingBox().offset(-enderdragonpart.getX(), -enderdragonpart.getY(), -enderdragonpart.getZ()), 0.25F, 1.0F, 0.0F, 1.0F);
                matrices.pop();
            }
        }
    }

    public static void setColor(int color) {
        float a = (float) (color >> 24 & 0xFF) / 255.0f;
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }


    public static void prepareBoxRender(float lineWidth, double red, double green, double blue, double alpha) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(lineWidth);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(false);
        GL11.glColor4d(red, green, blue, alpha);
    }


    public static void stopBoxRender() {
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }


    public static void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 0xFF) / 255.0f;
        float red = (float) (hex >> 16 & 0xFF) / 255.0f;
        float green = (float) (hex >> 8 & 0xFF) / 255.0f;
        float blue = (float) (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glHint(3154, 4354);
    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}

