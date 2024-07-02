package net.GingkoTreeX.totem.gui.hud;

import net.GingkoTreeX.totem.controller.ModuleManager;
import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.ModuleHackFramework;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "totem",bus = Mod.EventBusSubscriber.Bus.MOD)
public class Hud extends ModuleManager {
    private static boolean isOpen;
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public Hud() {
        super("Hud", Category.RENDER, "Hud", null);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        isOpen = true;
    }
    @Override
    public void onDisable() {
        super.onDisable();
        isOpen = false;
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
        int xPosition = MinecraftClient.getInstance().getWindow().getScaledWidth()-200; // 列表起始X位置
        int yPosition = 20; // 列表起始Y位置
        int elementHeight = 15; // 单个元素的高度

        List<ModuleManager> modules = ModuleHackFramework.getInstance().getAllModules();

        for (int i = 0; i < modules.size() && yPosition <= mc.getWindow().getScaledHeight() - 15; i++) {
            ModuleManager module = modules.get(i);
            if (module.isEnabled()) {
                // 绘制模块名称
                int color = -1;
                mc.textRenderer.drawWithShadow(matrices, "[+]" + module.getName()+" level"+module.getModuleLevel() , xPosition, yPosition + 2, color);
                // 添加矩形边框
                yPosition += elementHeight;
            }
        }
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
    }


}
