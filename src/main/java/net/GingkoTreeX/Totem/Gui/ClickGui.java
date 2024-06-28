package net.GingkoTreeX.Totem.Gui;

import net.GingkoTreeX.Totem.Controller.ModuleManager;
import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Features.ModuleHackFramework;
import net.GingkoTreeX.Totem.Utils.AddChatMessage;
import net.GingkoTreeX.Totem.Utils.WindowUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;

import java.awt.*;
import java.util.List;

@Mod.EventBusSubscriber(modid = "totem",bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClickGui extends ModuleManager {
    private static boolean isOpen;
    private final MinecraftClient mc = MinecraftClient.getInstance();

    private long lastActionTime;
    private static final int MINIMUM_DELAY_MS = 300;//点击间隔

    public ClickGui() {
        super("ClickGUI", Category.COMBAT, "ClickGUI", GLFW.GLFW_KEY_G);
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
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && isOpen) {
            //AddChatMessage.addPinkChatMessage("[DEBUG]ClickGUI Step-0");
            MatrixStack matrices = event.getMatrixStack();
            renderModuleList(matrices);
        }
    }
   private void renderModuleList(MatrixStack matrices){
        int xPosition = 20;//初始x坐标
        int yPosition = 30;//初始y坐标
        int elementHeight = 20;//每个功能的高度
        int listWidth = 100;//~宽度

        List<ModuleManager> modules = ModuleHackFramework.getInstance().getAllModules();

        for (ModuleManager module : modules) {


            // 绘制前进行点击检测，基于当前Y位置
            if (checkMouseHover(xPosition, yPosition + 2, listWidth, yPosition + elementHeight) && mc.mouse.wasLeftButtonClicked()) {
                handleModuleClick(module);
            }

            // 绘制模块名称和边框
            int color = module.isEnabled() ? 0x00FFFF : -1;
            mc.textRenderer.drawWithShadow(matrices, module.getName(), xPosition, yPosition + 2, color);
            color = module.isEnabled() ? 0x8000FF0:0x8000000 ;
            DrawableHelper.fill(matrices, xPosition, yPosition + 2, xPosition + listWidth, yPosition + elementHeight, color);

            // 更新Y位置到下一个元素
            yPosition =  yPosition + elementHeight;;

            // 确保不会超出屏幕下方
            if(yPosition > mc.getWindow().getScaledHeight() - 30) break;
        }
    }

    private void handleModuleClick(ModuleManager module) {
        if ((System.currentTimeMillis() - lastActionTime) > MINIMUM_DELAY_MS) {
            if (module != null) {
                module.setEnabled(!module.isEnabled()); // 切换状态
            }
            lastActionTime=System.currentTimeMillis();
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
