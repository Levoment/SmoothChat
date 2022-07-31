package com.github.levoment.smoothchat.forge;

import com.github.levoment.smoothchat.CommonCommands;
import com.github.levoment.smoothchat.SmoothChatMod;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraftforge.client.ClientCommandSourceStack;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = SmoothChatMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onClientCommandRegister(RegisterClientCommandsEvent registerClientCommandsEvent) {
        registerClientCommandsEvent.getDispatcher().register(
                CommandManager.literal("smoothchat").then(CommandManager.literal("config").then(CommandManager.literal("reload").executes(context -> {
                    if (context.getSource() instanceof ClientCommandSourceStack clientCommandSourceStack) {
                        Entity entity = clientCommandSourceStack.getEntity();
                        CommonCommands.reloadCommandImplementation(entity);
                    }

                    return 1;
                })))
        );

        registerClientCommandsEvent.getDispatcher().register(
                CommandManager.literal("smoothchat").then(CommandManager.literal("speed").then(CommandManager.argument("<float-seconds>", FloatArgumentType.floatArg()).executes(context -> {
                    // Get the float value and player
                    Float seconds = context.getArgument("<float-seconds>", Float.class);
                    if (context.getSource() instanceof ClientCommandSourceStack clientCommandSourceStack) {
                        Entity entity = clientCommandSourceStack.getEntity();
                        // Change the animation speed
                        CommonCommands.changeAnimationSpeedImplementation(seconds, entity);
                    }
                    return 1;
                })))
        );

        registerClientCommandsEvent.getDispatcher().register(
                CommandManager.literal("smoothchat").then(CommandManager.literal("reset").executes(context -> {
                    // Get the float value and player
                    if (context.getSource() instanceof ClientCommandSourceStack clientCommandSourceStack) {
                        Entity entity = clientCommandSourceStack.getEntity();
                        // Change the animation speed
                        CommonCommands.resetCommandImplementation(entity);
                    }
                    return 1;
                }))
        );

        registerClientCommandsEvent.getDispatcher().register(
                CommandManager.literal("smoothchat").then(CommandManager.argument("true/false", BoolArgumentType.bool()).executes(context -> {
                    // Get the argument
                    Boolean enabled = context.getArgument("true/false", Boolean.class);
                    if (enabled != null) {
                        // Get the float value and player
                        if (context.getSource() instanceof ClientCommandSourceStack clientCommandSourceStack) {
                            Entity entity = clientCommandSourceStack.getEntity();
                            // Change the animation speed
                            CommonCommands.enabledCommandImplementation(enabled, entity);
                        }
                    }
                    return 1;
                }))
        );

        registerClientCommandsEvent.getDispatcher().register(
                CommandManager.literal("smoothchat").then(CommandManager.literal("upward-animation").then(CommandManager.argument("true/false", BoolArgumentType.bool()).executes(context -> {
                    // Get the argument
                    Boolean downUpAnimationEnabled = context.getArgument("true/false", Boolean.class);
                    if (downUpAnimationEnabled != null) {
                        // Get the float value and player
                        if (context.getSource() instanceof ClientCommandSourceStack clientCommandSourceStack) {
                            Entity entity = clientCommandSourceStack.getEntity();
                            // Change the animation speed
                            CommonCommands.toggleUpwardAnimation(downUpAnimationEnabled, entity);
                        }
                    }
                    return 1;
                }))
        ));

        registerClientCommandsEvent.getDispatcher().register(
                CommandManager.literal("smoothchat").then(CommandManager.literal("left-right-animation").then(CommandManager.argument("true/false", BoolArgumentType.bool()).executes(context -> {
                            // Get the argument
                            Boolean leftToRightAnimationEnabled = context.getArgument("true/false", Boolean.class);
                            if (leftToRightAnimationEnabled != null) {
                                // Get the float value and player
                                if (context.getSource() instanceof ClientCommandSourceStack clientCommandSourceStack) {
                                    Entity entity = clientCommandSourceStack.getEntity();
                                    // Change the animation speed
                                    CommonCommands.toggleLeftToRightAnimation(leftToRightAnimationEnabled, entity);
                                }
                            }
                            return 1;
                        }))
                ));

        // Register all the commands
        ConfigCommand.register(registerClientCommandsEvent.getDispatcher());
    }
}
