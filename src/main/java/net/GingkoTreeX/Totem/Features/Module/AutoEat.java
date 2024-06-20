package net.GingkoTreeX.Totem.Features.Module;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Features.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

import static net.minecraft.screen.slot.SlotActionType.PICKUP;

public class AutoEat extends ModuleManager {
    private long lastActionTime;
    private static final int MINIMUM_DELAY_MS = 500; // 假设最小延迟为500毫秒


    public AutoEat() {
        super("AutoEat", Category.COMBAT, "When your HP is below 8, you will automatically eat golden apples", GLFW.GLFW_KEY_Z);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onUpdate() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && player.getHealth() < 10) {
// 检查是否应该允许行动
        if ((System.currentTimeMillis() - lastActionTime) < MINIMUM_DELAY_MS) {
            return;
        }
            int goldenAppleSlot = 0;
            // 寻找金苹果的位置
            for (int slot = 0; slot <= 9; slot++) {
                ItemStack stack = player.getInventory().getStack(slot);
                if (stack.getItem() == Items.GOLDEN_APPLE || stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
                    goldenAppleSlot = slot;
                }
                if (goldenAppleSlot >= 1) {

                    if (MinecraftClient.getInstance().interactionManager != null) {

                        ClickSlotC2SPacket packet = new ClickSlotC2SPacket(
                                0, // syncId
                                /* revision */ 0,
                                slot, // slot 目标槽位
                                0, // button 常规点击
                                PICKUP,
                                player.getMainHandStack().copy(), // stack 复制一份手持物品栈以防原物品被意外修改
                                Int2ObjectMaps.emptyMap() // modifiedStacks 空表示无额外修改记录
                        );
                        Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(packet);
                        PlayerInteractItemC2SPacket usePacket = new PlayerInteractItemC2SPacket(Hand.MAIN_HAND);
                        Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(usePacket);
                    }
                    break;


                }
            }
            }
        }


    @Override
    public void onRender() {

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
