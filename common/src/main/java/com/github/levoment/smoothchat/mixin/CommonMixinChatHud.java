package com.github.levoment.smoothchat.mixin;

import com.github.levoment.smoothchat.SmoothChatMod;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;

@Mixin(ChatHud.class)
public abstract class CommonMixinChatHud {

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At("HEAD"))
    public void onAddMessage(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ci) {
        System.out.println("New message");
        if (!SmoothChatMod.configurationObject.SmoothChat) {
            SmoothChatMod.finishSmoothTransition = true;
        } else {
            SmoothChatMod.finishSmoothTransition = false;
            SmoothChatMod.smoothChatIncreaseVisibleHeight = 0f;
            SmoothChatMod.smoothChatFinalYPosition = 0f;
            SmoothChatMod.smoothChatLerpedPosition = 0f;
            SmoothChatMod.smoothChatFinalXPosition = 0f;
            // Set the timer for the message
            SmoothChatMod.start = Instant.now();
            SmoothChatMod.elapsedTime = 0f;
        }
    }

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"))
    public int redirectDrawWithShadow(TextRenderer instance, MatrixStack matrices, OrderedText text, float x, float y, int color) {
        if (!SmoothChatMod.finishSmoothTransition) {
            if (SmoothChatMod.configurationObject.LeftToRight) {
                return instance.drawWithShadow(matrices, SmoothChatMod.smoothChatOrderedText, SmoothChatMod.smoothChatFinalXPosition + SmoothChatMod.smoothChatLerpedPosition, (SmoothChatMod.smoothChatFinalYPosition), color);
            }
            if (SmoothChatMod.configurationObject.Upward) {
                return instance.drawWithShadow(matrices, SmoothChatMod.smoothChatOrderedText, 0.0f, (SmoothChatMod.smoothChatFinalYPosition - SmoothChatMod.smoothChatLerpedPosition + 0.1f), color);
            }
        }
        return instance.drawWithShadow(matrices, text, x, y, color);
    }
}
