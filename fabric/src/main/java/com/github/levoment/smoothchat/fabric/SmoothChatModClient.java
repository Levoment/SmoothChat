package com.github.levoment.smoothchat.fabric;

import com.github.levoment.smoothchat.CommonCommands;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.entity.Entity;

public class SmoothChatModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("smoothchat").then(ClientCommandManager.literal("config").then(ClientCommandManager.literal("reload").executes(context -> {
                Entity playerEntity = context.getSource().getEntity();
                if (playerEntity != null) {
                    CommonCommands.reloadCommandImplementation(playerEntity);
                }
                return 1;
            }))));
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("smoothchat").then(ClientCommandManager.literal("speed").then(ClientCommandManager.argument("<float-seconds>", FloatArgumentType.floatArg()).executes(context -> {
                Entity playerEntity = context.getSource().getEntity();
                // Get the float value and player
                Float seconds = context.getArgument("<float-seconds>", Float.class);
                if (playerEntity != null && seconds != null) {
                    CommonCommands.changeAnimationSpeedImplementation(seconds, playerEntity);
                }
                return 1;
            }))));
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("smoothchat").then(ClientCommandManager.literal("reset").executes(context -> {
                Entity playerEntity = context.getSource().getEntity();
                if (playerEntity != null) {
                    CommonCommands.resetCommandImplementation(playerEntity);
                }
                return 1;
            })));
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("smoothchat").then(ClientCommandManager.argument("true/false", BoolArgumentType.bool()).executes(context -> {
                // Get the argument
                Boolean enabled = context.getArgument("true/false", Boolean.class);
                Entity playerEntity = context.getSource().getEntity();
                if (playerEntity != null && enabled != null) {
                    CommonCommands.enabledCommandImplementation(enabled, playerEntity);
                }
                return 1;
            })));
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("smoothchat").then(ClientCommandManager.literal("upward-animation").then(ClientCommandManager.argument("true/false", BoolArgumentType.bool()).executes(context -> {
                // Get the argument
                Boolean enabled = context.getArgument("true/false", Boolean.class);
                Entity playerEntity = context.getSource().getEntity();
                if (playerEntity != null && enabled != null) {
                    CommonCommands.toggleUpwardAnimation(enabled, playerEntity);
                }
                return 1;
            }))));
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("smoothchat").then(ClientCommandManager.literal("left-right-animation").then(ClientCommandManager.argument("true/false", BoolArgumentType.bool()).executes(context -> {
                // Get the argument
                Boolean enabled = context.getArgument("true/false", Boolean.class);
                Entity playerEntity = context.getSource().getEntity();
                if (playerEntity != null && enabled != null) {
                    CommonCommands.toggleLeftToRightAnimation(enabled, playerEntity);
                }
                return 1;
            }))));
        });
    }
}
