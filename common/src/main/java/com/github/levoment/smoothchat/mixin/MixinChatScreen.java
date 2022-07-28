package com.github.levoment.smoothchat.mixin;

import com.github.levoment.smoothchat.SmoothChatMod;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen extends Screen {

    protected MixinChatScreen(Text title) {
        super(title);
    }

    private int smoothChatWriteBoxLastTick;
    private boolean finishSmoothTransition = false;

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"
    , at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;fill(Lnet/minecraft/client/util/math/MatrixStack;IIIII)V"))
    public void smoothWriteBoxOpen(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        if (finishSmoothTransition) {
            ChatScreen.fill(matrices, 2, this.height - 14, this.width - 2, this.height - 2, this.client.options.getTextBackgroundColor(Integer.MIN_VALUE));
        } else {
            ChatScreen.fill(matrices, 2, this.height - smoothChatWriteBoxLastTick, this.width - 2, this.height - 2, this.client.options.getTextBackgroundColor(Integer.MIN_VALUE));
            smoothChatWriteBoxLastTick++;
            if (this.smoothChatWriteBoxLastTick == 14) {
                finishSmoothTransition = true;
                this.smoothChatWriteBoxLastTick = 0;
            }
        }
    }

    @Inject(method = "renderChatPreview(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At("HEAD"))
    public  void renderChatPreviewCallback(MatrixStack matrices, CallbackInfo ci) {
        SmoothChatMod.finishSmoothTransition = false;
    }
}