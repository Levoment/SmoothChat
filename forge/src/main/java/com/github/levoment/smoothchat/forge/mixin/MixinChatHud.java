package com.github.levoment.smoothchat.forge.mixin;

import com.github.levoment.smoothchat.SmoothChatMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.time.Duration;
import java.time.Instant;

import static com.github.levoment.smoothchat.SmoothChatMod.smoothChatFinalXPosition;


@Mixin(ChatHud.class)
public abstract class MixinChatHud {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;I)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"
                    , ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void drawText(MatrixStack matrices, int tickDelta, CallbackInfo ci, int i, int j, boolean bl, float f, int k, double d, double e, double g, double h, double l, int m, int n, ChatHudLine<OrderedText> chatHudLine, int p, double q, int r, int s, int t, double last) {
        if (SmoothChatMod.configurationObject.SmoothChat) {
            // If the end of the smooth transition has been achieved
            if (SmoothChatMod.finishSmoothTransition) {
                // Do nothing
            } else {
                // Get the current time
                Instant currentTime = Instant.now();
                // Get the elapsedTime since the text arrived to the chat
                float elapsedTime = (float) Duration.between(SmoothChatMod.start, currentTime).toMillis() / 1000;
                // Get the percentage
                float percentageComplete = elapsedTime / SmoothChatMod.configurationObject.TransitionTimeFloat;

                if (SmoothChatMod.configurationObject.LeftToRight) {
                    // If the message is the last message
                    if (n == 0) {
                        smoothChatFinalXPosition = -this.client.textRenderer.getWidth(chatHudLine.getText());
                        float easeOutCubicPosition = SmoothChatMod.easeOut(percentageComplete);
                        // Get the position for the text in the x coordinate
                        float lerpPosition = SmoothChatMod.lerpChat(0, this.client.textRenderer.getWidth(chatHudLine.getText()), easeOutCubicPosition);
                        SmoothChatMod.smoothChatLerpedPosition = lerpPosition;
                        SmoothChatMod.smoothChatFinalYPosition = (float) last  + (float) l;
                        SmoothChatMod.smoothChatOrderedText = chatHudLine.getText();
                    } else {
                        smoothChatFinalXPosition = 0;
                        SmoothChatMod.smoothChatLerpedPosition = 0;
                        SmoothChatMod.smoothChatFinalYPosition = (float) last  + (float) l;
                        SmoothChatMod.smoothChatOrderedText = chatHudLine.getText();
                    }
                } else {
                    // Get the position for the text in the y coordinate
                    float lerpPosition = SmoothChatMod.lerpChat(0.0, this.client.textRenderer.fontHeight - 1, percentageComplete);
                    SmoothChatMod.smoothChatLerpedPosition = lerpPosition;
                    SmoothChatMod.smoothChatFinalYPosition = (float) last;
                    SmoothChatMod.smoothChatOrderedText = chatHudLine.getText();
                }

                // Write the text
                // this.client.textRenderer.drawWithShadow(matrices, chatHudLine.getText(), 0.0f, (float) t - lerpPosition, 0xFFFFFF + (q << 24));
                if (percentageComplete >= 1.0f) {
                    // Finish the transition
                    SmoothChatMod.finishSmoothTransition = true;
                    SmoothChatMod.smoothChatFinalYPosition = 0f;
                    SmoothChatMod.smoothChatIncreaseVisibleHeight = 0f;
                    SmoothChatMod.smoothChatLerpedPosition = 0f;
                }
            }
        }
    }
}
