package com.github.levoment.smoothchat;

import com.google.common.base.Suppliers;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.Registries;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;

import java.io.File;
import java.time.Instant;
import java.util.function.Supplier;

public class SmoothChatMod {
    public static boolean finishSmoothTransition = false;
    public static float smoothChatIncreaseVisibleHeight = 0f;
    public static float smoothChatFinalXPosition = 0;
    public static OrderedText smoothChatOrderedText;
    public static float smoothChatFinalYPosition;
    public static float smoothChatLerpedPosition;

    public static Instant start;

    public static float elapsedTime;

    public static final String configPath = Platform.getConfigFolder().resolve("smoothchat-config.json").toAbsolutePath().toString();
    public static final File configFile = new File(configPath);

    public static ConfigurationObject configurationObject;


    public static final String MOD_ID = "smoothchat";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));

    public static void init() {
        // Read or create the config
        SmoothChatConfigurationManager.readOrCreateConfig();
    }

    public static float lerpChat(double startY, double endY, double delta) {
        return MathHelper.lerp((float) delta, (float) startY, (float) endY);
    }

    public static float easeOut(float progress) {
        return (float) (1f - Math.pow(1f - progress, 3));
    }

}
