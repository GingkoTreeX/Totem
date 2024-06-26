package net.GingkoTreeX.Totem.Gui.Hud;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

public class ModHUD {

    private final TextRenderer textRenderer; // 使用TextRenderer代替Font
    private final  MatrixStack matrices;
    public ModHUD() {
        MinecraftClient client = MinecraftClient.getInstance();
        this.textRenderer = client.textRenderer; // 获取textRenderer实例
        this.matrices = new MatrixStack();
    }

    public void onRender() {
            MinecraftClient mc = MinecraftClient.getInstance();
            int x = mc.getWindow().getScaledWidth() - 100; // 更改计算宽度的函数
            int y = 50;
            RenderSystem.enableBlend(); // 启用混合模式
            if (matrices!=null&&textRenderer!=null) {
                DrawableHelper.drawCenteredText(this.matrices, this.textRenderer, "Totem", x, y, 0xFFFFFF);
            }
    }
}
