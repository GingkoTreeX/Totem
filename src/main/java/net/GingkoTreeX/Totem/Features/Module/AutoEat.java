package net.GingkoTreeX.Totem.Features.Module;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.GingkoTreeX.Totem.Features.Category;
import net.GingkoTreeX.Totem.Controller.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

import static net.minecraft.screen.slot.SlotActionType.*;


public class AutoEat extends ModuleManager {
    private long lastActionTime;
    private static final int MINIMUM_DELAY_MS = 5000;


    public AutoEat() {
        super("AutoEat", Category.COMBAT, "When your HP is below 8, you will automatically eat golden apples", GLFW.GLFW_KEY_Z);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onUpdate() {
        if (!isEnabled())return;
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

                        MinecraftClient mc = MinecraftClient.getInstance();
                        PlayerInventory inventory = mc.player.getInventory();

                        for (int i = 0; i < inventory.main.size(); ++i) { // 遍历主物品栏的每个槽位
                            ItemStack stackInSlot = inventory.main.get(i);

                            if (!stackInSlot.isEmpty() && stackInSlot.getItem() == player.getMainHandStack().getItem()) {

                                lastActionTime=System.currentTimeMillis();
                            }
                            break;

                            }
                        }
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
