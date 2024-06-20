package net.GingkoTreeX.Totem.Features.Model;

import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Features.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

public class FastUse extends ModuleManager {
    private boolean usedTimer = false;
    private int customTimerValue; // 示例，需初始化并在设置中获取实际值
    private int delayValue; // 同上
    private int customSpeedValue; // 同上

    public FastUse() {
        super("FastUse", Category.COMBAT, "Enhanced item use speed", GLFW.GLFW_KEY_Z);
        // 假设这些值在模块启用时或通过配置获取
        this.customTimerValue = 1; // 示例速度
        this.delayValue = 0; // 示例延迟
        this.customSpeedValue = 1; // 示例发送数据包次数
    }

    @Override
    public void onUpdate() {
        MinecraftClient mc=MinecraftClient.getInstance();
        if (this.isEnabled() && mc.player != null) {
            if (!mc.player.isUsingItem()) {
                // 假设这里需要重置任何计时或状态
            } else {
                ItemStack holdingItem = mc.player.getMainHandStack();
                if ((holdingItem.getItem()==Items.GOLDEN_APPLE) ||
                        (holdingItem.getItem()==Items.ENCHANTED_GOLDEN_APPLE)) { // 或者其他条件判断物品类型
                    handleFastUse(holdingItem);
                }
            }
        }
    }

    @Override
    public void onRender() {

    }

    private void handleFastUse(ItemStack holdingItem) {
        String effectiveMode = "";
        switch (effectiveMode.toLowerCase()) {
            case "instant":
                instantlyConsumeOrUse();
                break;
            case "customdelay": {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastCustomActionTime >= delayValue * 1000L) { // 假定lastCustomActionTime是上次执行此操作的时间
                    instantlyConsumeOrUse();
                    lastCustomActionTime = currentTime;
                }
                break;
            }

        }
    }

    private void instantlyConsumeOrUse() {
        MinecraftClient mc=MinecraftClient.getInstance();
        if (usedTimer) {
            usedTimer = false;
        }
        mc.player.stopUsingItem();
    }

    private long lastCustomActionTime = 0L;
}
