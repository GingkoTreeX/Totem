package net.GingkoTreeX.Totem.Mixin;

import net.GingkoTreeX.Totem.Features.ModuleHackFramework;
import net.GingkoTreeX.Totem.Gui.ClickGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.GlfwUtil;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.SmoothUtil;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import java.util.List;

@Mixin(Mouse.class)
public abstract class MixinMouse{

    @Shadow private boolean leftButtonClicked;
    @Shadow private boolean middleButtonClicked;
    @Shadow private boolean rightButtonClicked;
    @Shadow private double x;
    @Shadow private double y;
    @Shadow private int controlLeftTicks;
    @Shadow private int activeButton;
    @Shadow private boolean hasResolutionChanged;
    @Shadow private int field_1796;
    @Shadow private double glfwTime;
    @Shadow private SmoothUtil cursorXSmoother;
    @Shadow private SmoothUtil cursorYSmoother;
    @Shadow private double cursorDeltaX;
    @Shadow private double cursorDeltaY;
    @Shadow private double eventDeltaWheel;
    @Shadow private double lastMouseUpdateTime;
    @Shadow private boolean cursorLocked;
    @Final
    @Shadow private MinecraftClient client;

    @Shadow public void setResolutionChanged(){}

    @Shadow public void unlockCursor() {}

    @Shadow  public void lockCursor() {}

    /**
     * @author GingkoTreeX
     * @reason 解决一些模组与客户端冲突的问题
     */

    @Overwrite
    public void onMouseButton(long window, int button, int action, int mods) {
        if (window == this.client.getWindow().getHandle()) {
            boolean flag = action == 1;
            if (MinecraftClient.IS_SYSTEM_MAC && button == 0) {
                if (flag) {
                    if ((mods & 2) == 2) {
                        button = 1;
                        ++this.controlLeftTicks;
                    }
                } else if (this.controlLeftTicks > 0) {
                    button = 1;
                    --this.controlLeftTicks;
                }
            }

            if (flag) {
                if (this.client.options.touchscreen && this.field_1796++ > 0) {
                    return;
                }

                this.activeButton = button;
                this.glfwTime = GlfwUtil.getTime();
            } else if (this.activeButton != -1) {
                if (this.client.options.touchscreen && --this.field_1796 > 0) {
                    return;
                }

                this.activeButton = -1;
            }

            if (ForgeHooksClient.onRawMouseClicked(button, action, mods)) {
                return;
            }

            boolean[] aboolean = new boolean[]{false};
            if (this.client.getOverlay() == null) {
                if (this.client.currentScreen == null) {
                    if (!this.cursorLocked && flag ) {
                        if (!ModuleHackFramework.getInstance().isModuleEnabled(ClickGui.class)) {
                            this.lockCursor();
                        }
                    }
                } else {
                    double d0 = this.x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
                    double d1 = this.y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
                    Screen screen = this.client.currentScreen;
                    if (flag) {
                        screen.applyMousePressScrollNarratorDelay();
                        int finalButton = button;
                        Screen.wrapScreenError(() -> {
                            aboolean[0] = ForgeHooksClient.onScreenMouseClickedPre(screen, d0, d1, finalButton);
                            if (!aboolean[0]) {
                                aboolean[0] = this.client.currentScreen.mouseClicked(d0, d1, finalButton);
                                aboolean[0] = ForgeHooksClient.onScreenMouseClickedPost(screen, d0, d1, finalButton, aboolean[0]);
                            }

                        }, "mouseClicked event handler", screen.getClass().getCanonicalName());
                    } else {
                        int finalButton1 = button;
                        Screen.wrapScreenError(() -> {
                            aboolean[0] = ForgeHooksClient.onScreenMouseReleasedPre(screen, d0, d1, finalButton1);
                            if (!aboolean[0]) {
                                aboolean[0] = this.client.currentScreen.mouseReleased(d0, d1, finalButton1);
                                aboolean[0] = ForgeHooksClient.onScreenMouseReleasedPost(screen, d0, d1, finalButton1, aboolean[0]);
                            }

                        }, "mouseReleased event handler", screen.getClass().getCanonicalName());
                    }
                }
            }

            if (!aboolean[0] && (this.client.currentScreen == null || this.client.currentScreen.passEvents) && this.client.getOverlay() == null) {
                if (button == 0) {
                    this.leftButtonClicked = flag;
                } else if (button == 2) {
                    this.middleButtonClicked = flag;
                } else if (button == 1) {
                    this.rightButtonClicked = flag;
                }

                KeyBinding.setKeyPressed(InputUtil.Type.MOUSE.createFromCode(button), flag);
                if (flag) {
                    if (this.client.player.isSpectator() && button == 2) {
                        this.client.inGameHud.getSpectatorHud().useSelectedCommand();
                    } else {
                        KeyBinding.onKeyPressed(InputUtil.Type.MOUSE.createFromCode(button));
                    }
                }
            }

            ForgeHooksClient.fireMouseInput(button, action, mods);
        }
    }

}
