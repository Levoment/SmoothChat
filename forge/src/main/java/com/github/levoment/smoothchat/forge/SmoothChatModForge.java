package com.github.levoment.smoothchat.forge;

import com.github.levoment.smoothchat.SmoothChatMod;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SmoothChatModForge.MOD_ID)
public class SmoothChatModForge {
    public static final String MOD_ID = "smoothchat";

    public SmoothChatModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(SmoothChatModForge.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        SmoothChatModForge.init();
        SmoothChatMod.init();
    }
    private static void init() {

    }
}
