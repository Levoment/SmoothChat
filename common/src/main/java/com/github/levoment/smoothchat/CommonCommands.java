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
        SmoothChatMod.configurationObject = new ConfigurationObject();
        entity.sendMessage(Text.of("Smooth chat reset"));
        SmoothChatConfigurationManager.writeConfig();
    }

    public static void enabledCommandImplementation(Boolean enabled, Entity entity) {
        if (SmoothChatMod.configurationObject != null) {
            SmoothChatMod.configurationObject.SmoothChat = enabled;
        } else {
            SmoothChatMod.configurationObject = new ConfigurationObject();
            SmoothChatMod.configurationObject.SmoothChat = enabled;
        }
        if (enabled) {
            entity.sendMessage(Text.of("Smooth chat enabled"));
        } else {
            entity.sendMessage(Text.of("Smooth chat disabled"));
        }
        SmoothChatConfigurationManager.writeConfig();
    }


    public static void toggleLeftToRightAnimation(Boolean leftToRightAnimationEnabled, Entity entity) {
        if (SmoothChatMod.configurationObject != null) {
            SmoothChatMod.configurationObject.SmoothChat = leftToRightAnimationEnabled;
        } else {
            // Create the object with default settings
            SmoothChatMod.configurationObject = new ConfigurationObject();
        }
        if (leftToRightAnimationEnabled) {
            SmoothChatMod.configurationObject.LeftToRight = true;
            SmoothChatMod.configurationObject.Upward = false;
            entity.sendMessage(Text.of("Smooth chat left-right animation enabled"));
        } else {
            SmoothChatMod.configurationObject.LeftToRight = false;
            SmoothChatMod.configurationObject.Upward = true;
            entity.sendMessage(Text.of("Smooth chat left-right animation disabled"));
        }
        SmoothChatConfigurationManager.writeConfig();
    }

    public static void toggleUpwardAnimation(Boolean enabled, Entity playerEntity) {
        if (SmoothChatMod.configurationObject != null) {
            SmoothChatMod.configurationObject.SmoothChat = enabled;
        } else {
            // Create the object with default settings
            SmoothChatMod.configurationObject = new ConfigurationObject();
        }
        if (enabled) {
            SmoothChatMod.configurationObject.Upward = true;
            SmoothChatMod.configurationObject.LeftToRight = false;
            playerEntity.sendMessage(Text.of("Smooth chat upward animation enabled"));
        } else {
            SmoothChatMod.configurationObject.Upward = false;
            SmoothChatMod.configurationObject.LeftToRight = true;
            playerEntity.sendMessage(Text.of("Smooth chat upward animation disabled"));
        }
        SmoothChatConfigurationManager.writeConfig();
    }
}
