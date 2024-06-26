package net.GingkoTreeX.Totem.Gui;

import net.GingkoTreeX.Totem.Controller.ModuleManager;
import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Features.ModuleHackFramework;
import net.GingkoTreeX.Totem.Utils.AddChatMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.List;

@Mod.EventBusSubscriber(modid = "totem",bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClickGui extends ModuleManager {
    private static boolean isOpen;
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public ClickGui() {
        super("ClickGUI", Category.COMBAT, "ClickGUI", GLFW.GLFW_KEY_N);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        AddChatMessage.addPinkChatMessage("[DEBUG]ClickGUI Enable");
        isOpen = true;
        mc.mouse.unlockCursor();
    }
    @Override
    public void onDisable() {
        super.onDisable();
        AddChatMessage.addPinkChatMessage("[DEBUG]ClickGUI Disable");
        isOpen = false;
        mc.mouse.lockCursor();
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && isOpen) {
            AddChatMessage.addPinkChatMessage("[DEBUG]ClickGUI Step-0");
            MatrixStack matrices = event.getMatrixStack();
            renderModuleList(matrices);
        }
    }
    private void renderModuleList(MatrixStack matrices) {
        int xPosition = 10; // 列表起始X位置
        int yPosition = 30; // 列表起始Y位置
        int elementHeight = 20; // 单个元素的高度
        int listWidth = 150; // 列表宽度

        List<ModuleManager> modules = ModuleHackFramework.getInstance().getAllModules(); // 获取模块列表
        AddChatMessage.addPinkChatMessage("[DEBUG]ClickGUI Step-1");
        for (int i = 0; i < modules.size() && yPosition <= mc.getWindow().getScaledHeight() - 30; i++) {
            ModuleManager module = modules.get(i);

            // 绘制模块名称
            mc.textRenderer.drawWithShadow(matrices, module.getName(), xPosition, yPosition, -1);

            // 检查鼠标交互
            if (checkMouseHover(xPosition, yPosition, listWidth, elementHeight)) {
                handleModuleClick(module); // 处理点击事件
            }

            yPosition += elementHeight; // 下移至下一个位置
        }
        AddChatMessage.addPinkChatMessage("[DEBUG]ClickGUI Step-2");
    }

    private boolean isMouseOverElement(int x, int y, int width, int height) {
        double mouseX = new Mouse(MinecraftClient.getInstance()).getX() * mc.getWindow().getWidth() / mc.getWindow().getScaledWidth();
        double mouseY = mc.getWindow().getScaledHeight() -  new Mouse(MinecraftClient.getInstance()).getY() * mc.getWindow().getScaledHeight() / mc.getWindow().getHeight();
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    private void handleModuleClick(ModuleManager module) {
        if (module != null) {
            module.setEnabled(!module.isEnabled()); // 切换状态
        }
    }
    private boolean checkMouseHover(int x, int y, int width, int height) {
        double mouseX = mc.mouse.getX();
        double mouseY = mc.mouse.getY();
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }



    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
    }


}
