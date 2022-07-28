package com.github.levoment.smoothchat;

import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

public class CommonCommands {

    public static void reloadCommandImplementation(Entity playerEntity) {
        SmoothChatConfigurationManager.readConfig();
        playerEntity.sendMessage(Text.of("Smooth chat config reloaded from file"));
    }

    public static void changeAnimationSpeedImplementation(float speed, Entity playerEntity) {
        if (playerEntity != null) {
            if (SmoothChatMod.configurationObject != null) {
                SmoothChatMod.configurationObject.TransitionTimeFloat = speed;
                playerEntity.sendMessage(Text.of("Smooth chat speed: " + speed));
                // Write the new values
                SmoothChatConfigurationManager.writeConfig();
            }
        }
    }

    public static void resetCommandImplementation(Entity entity) {
        if (SmoothChatMod.configurationObject != null) {
            SmoothChatMod.configurationObject.SmoothChat = true;
            SmoothChatMod.configurationObject.TransitionTimeFloat = 0.5f;
            entity.sendMessage(Text.of("Smooth chat reset"));
        } else {
            SmoothChatMod.configurationObject = new ConfigurationObject();
            SmoothChatMod.configurationObject.SmoothChat = true;
            SmoothChatMod.configurationObject.TransitionTimeFloat = 0.5f;
            entity.sendMessage(Text.of("Smooth chat reset"));
        }
        SmoothChatConfigurationManager.writeConfig();
    }

    public static void enabledCommandImplementation(Boolean enabled, Entity entity) {
        if (SmoothChatMod.configurationObject != null) {
            SmoothChatMod.configurationObject.SmoothChat = enabled;
        } else {
            SmoothChatMod.configurationObject = new ConfigurationObject();
            SmoothChatMod.configurationObject.SmoothChat = enabled;
            SmoothChatMod.configurationObject.TransitionTimeFloat = 0.5f;
        }
        if (enabled) {
            entity.sendMessage(Text.of("Smooth chat enabled"));
        } else {
            entity.sendMessage(Text.of("Smooth chat disabled"));
        }
        SmoothChatConfigurationManager.writeConfig();
    }
}
