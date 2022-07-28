package com.github.levoment.smoothchat.fabric;

import com.github.levoment.smoothchat.SmoothChatMod;
import net.fabricmc.api.ModInitializer;

public class SmoothChatModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SmoothChatMod.init();
    }
}
