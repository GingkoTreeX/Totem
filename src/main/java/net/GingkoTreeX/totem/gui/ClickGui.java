package net.GingkoTreeX.totem.gui;

import java.util.List; import java.util.Objects;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.systems.RenderSystem;

import net.GingkoTreeX.totem.controller.ModuleManager; import net.GingkoTreeX.totem.features.Category; import net.GingkoTreeX.totem.features.ModuleHackFramework; import net.GingkoTreeX.totem.utils.MessageUtils; import net.minecraft.client.MinecraftClient; import net.minecraft.client.gui.DrawableHelper; import net.minecraft.client.util.math.MatrixStack; import net.minecraftforge.client.event.RenderGameOverlayEvent; import net.minecraftforge.eventbus.api.SubscribeEvent; import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "totem", bus = Mod.EventBusSubscriber.Bus.MOD) public class ClickGui extends ModuleManager { private static boolean isOpen; private final MinecraftClient mc = MinecraftClient.getInstance();

    private long lastActionTime;
    private static final int MINIMUM_DELAY_MS = 300;// 点击间隔

    public ClickGui() {
        super("ClickGUI", Category.RENDER, "ClickGUI", GLFW.GLFW_KEY_G);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        isOpen = true;
        mc.mouse.unlockCursor();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        isOpen = false;
        mc.mouse.lockCursor();
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && isOpen) {
            MatrixStack matrixStack = event.getMatrixStack();
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            int xPosition = 20;// 初始x坐标
            int yPosition = 30;// 初始y坐标
            int elementHeight = 40;// 每个功能的高度
            int listWidth = 120;// ~宽度
            List<ModuleManager> modules = ModuleHackFramework.getInstance().getAllModules();
            for (ModuleManager module : modules) {
                // 绘制前进行点击检测，基于当前Y位置
                if (checkMouseHover(xPosition, yPosition + 2, listWidth, elementHeight) && mc.mouse.wasLeftButtonClicked()) {
                    handleModuleClick(module);
                }
                // 绘制模块名称和边框
                int color = module.isEnabled() ? 0x00FFFF : -1;
                String bind = (GLFW.glfwGetKeyName(module.getKeyBind(), GLFW.glfwGetKeyScancode(module.getKeyBind())));
                if ((GLFW.glfwGetKeyName(module.getKeyBind(), GLFW.glfwGetKeyScancode(module.getKeyBind()))) != null) {
                    bind = (Objects.requireNonNull(GLFW.glfwGetKeyName(module.getKeyBind(), GLFW.glfwGetKeyScancode(module.getKeyBind())))).toUpperCase();
                }
                mc.textRenderer.drawWithShadow(matrixStack, module.getName() + " bind: " + bind, xPosition + 10, yPosition + 15, color);
                color = module.isEnabled() ? 0x8000C200 : 0x80000000;
                DrawableHelper.fill(matrixStack, xPosition, yPosition, xPosition + listWidth, yPosition + elementHeight, color);
                // 更新Y位置到下一个元素
                yPosition = yPosition + elementHeight;
                // 确保不会超出屏幕下方
                if (yPosition > mc.getWindow().getScaledHeight() - 30) {
                    yPosition = 30;
                    xPosition = xPosition + listWidth;
                }// 初始y坐标;
            }
            // 绘制全局强度等级GUI
            int level = this.getModuleLevel();
            DrawableHelper.fill(matrixStack, xPosition + listWidth + 10, yPosition, xPosition + listWidth + 10 + 270, yPosition + 50, 0x80000000);
            mc.textRenderer.drawWithShadow(matrixStack, "全局强度等级 优化自动配置:", xPosition + listWidth+40, yPosition + 25, 0xFFFFFF);
            mc.textRenderer.drawWithShadow(matrixStack, "(等级越高越暴力 例如杀戮光环距离提升等) 当前等级:" + level, xPosition + listWidth+40, yPosition + 35, 0xFFFFFF);
            double mouseX = mc.mouse.getX();
            double mouseY = mc.mouse.getY();
            Button plusButton = new Button(xPosition + listWidth + 10, yPosition + 10, 20, 10, "+");
            Button minusButton = new Button(xPosition + listWidth + 10, yPosition + 25, 20, 10, "-");
            // 绘制按钮
            plusButton.draw(mc, matrixStack, (int) mouseX, (int) mouseY);
            minusButton.draw(mc, matrixStack, (int) mouseX, (int) mouseY);
            // 检测鼠标是否在GUI区域内，并执行相应的操作
                if (mc.mouse.wasLeftButtonClicked()) {
                    if (checkMouseHover(xPosition + listWidth + 10, yPosition + 10, 20, 10)) {
                        onClickLevelButton(true);
                    }else if (checkMouseHover(xPosition + listWidth + 10, yPosition + 25, 20, 10)){
                        onClickLevelButton(false);
                    }
                }
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
        }
    }

    private void handleModuleClick(ModuleManager module) {
        if ((System.currentTimeMillis() - lastActionTime) > MINIMUM_DELAY_MS) {
            if (module != null) {
                module.setEnabled(!module.isEnabled()); // 切换状态
            }
            lastActionTime = System.currentTimeMillis();
        }
    }

    private void onClickLevelButton(boolean plusOrMinus) {
        if ((System.currentTimeMillis() - lastActionTime) > MINIMUM_DELAY_MS) {
            if (plusOrMinus) {
                MessageUtils.addPinkChatMessage("[DEBUG]成功增加等级");
                ModuleHackFramework.getInstance().setAllModulesLevel(this.getModuleLevel() + 1);
            }else {
                MessageUtils.addPinkChatMessage("[DEBUG]成功降低等级");
                ModuleHackFramework.getInstance().setAllModulesLevel(this.getModuleLevel() - 1);
            }
            lastActionTime = System.currentTimeMillis();
        }
    }

    private boolean checkMouseHover(int x, int y, int width, int height) {
        double mouseX = mc.mouse.getX() * mc.getWindow().getScaledWidth() / mc.getWindow().getWidth();
        double mouseY = mc.mouse.getY() * mc.getWindow().getScaledHeight() / mc.getWindow().getHeight();
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
    }

    private record Button(int xPosition, int yPosition, int width, int height, String text) {
        public void draw(MinecraftClient mc, MatrixStack matrices, int mouseX, int mouseY) {
            int color = isMouseHover(mouseX, mouseY) ? 0x80FFFFFF : 0x80C0C0C0;
            DrawableHelper.fill(matrices, xPosition, yPosition, xPosition + width, yPosition + height, color);
            mc.textRenderer.drawWithShadow(matrices, text, xPosition + 6, yPosition + 3, 0xFFFFFF);
        }

        public boolean isMouseHover(int mouseX, int mouseY) {
            return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        }
    }
}