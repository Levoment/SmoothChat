package com.github.levoment.smoothchat.fabric.mixin;

import com.github.levoment.smoothchat.SmoothChatMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.time.Duration;
import java.time.Instant;
import java.util.Deque;
import java.util.List;

import static com.github.levoment.smoothchat.SmoothChatMod.*;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow private int scrolledLines;

    @Shadow public abstract int getVisibleLineCount();

    @Shadow @Final private Deque<Text> messageQueue;

    @Shadow @Final private List<ChatHudLine<Text>> messages;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;I)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"
                    , ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void drawText(MatrixStack matrices, int tickDelta, CallbackInfo ci, int i, int j, boolean bl, float f, int k, double d, double e, double g, double h, double l, int m, int n, ChatHudLine<OrderedText> chatHudLine, double p, int q, int r, int s, double t) {
        if (configurationObject.SmoothChat) {
            // If the end of the smooth transition has been achieved
            if (SmoothChatMod.finishSmoothTransition) {
                // Do nothing
            } else {
                // Get the current time
                Instant currentTime = Instant.now();
                // Get the elapsedTime since the text arrived to the chat
                float elapsedTime = (float) Duration.between(start, currentTime).toMillis() / 1000;
                // Get the percentage
                float percentageComplete = elapsedTime / configurationObject.TransitionTimeFloat;

                if (SmoothChatMod.configurationObject.LeftToRight) {
                    // If the message is the last message
                    if (n == 0) {
                        smoothChatFinalXPosition = -this.client.textRenderer.getWidth(chatHudLine.getText());
                        // Get the position for the text in the x coordinate
                        float lerpPosition = SmoothChatMod.lerpChat(0, this.client.textRenderer.getWidth(chatHudLine.getText()), percentageComplete);
                        SmoothChatMod.smoothChatLerpedPosition = lerpPosition;
                        SmoothChatMod.smoothChatFinalYPosition = (float) t  + (float) l;
                        SmoothChatMod.smoothChatOrderedText = chatHudLine.getText();
                    } else {
                        smoothChatFinalXPosition = 0;
                        SmoothChatMod.smoothChatLerpedPosition = 0;
                        SmoothChatMod.smoothChatFinalYPosition = (float) t  + (float) l;
                        SmoothChatMod.smoothChatOrderedText = chatHudLine.getText();
                    }
                } else {
                    // Get the position for the text in the y coordinate
                    float lerpPosition = SmoothChatMod.lerpChat(0.0, this.client.textRenderer.fontHeight - 1, percentageComplete);
                    // Set the position for global usage
                    SmoothChatMod.smoothChatLerpedPosition = lerpPosition;
                    SmoothChatMod.smoothChatFinalYPosition = (float) t;
                    SmoothChatMod.smoothChatOrderedText = chatHudLine.getText();
                }

                if (percentageComplete >= 1.0f) {
                    finishSmoothTransition = true;
                    smoothChatFinalYPosition = 0f;
                    smoothChatIncreaseVisibleHeight = 0f;
                    smoothChatLerpedPosition = 0f;
                }
            }
        }
    }
}
