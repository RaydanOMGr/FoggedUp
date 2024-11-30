package me.andreasmelone.foggedup.commands;

import com.mojang.brigadier.CommandDispatcher;
import me.andreasmelone.foggedup.capability.FogDataCapability;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class GetFogCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("getfog").requires(o -> o.hasPermission(2))
                .then(literal("shape").then(argument("player", EntityArgument.player()).executes(command -> {
                    Player player = EntityArgument.getPlayer(command, "player");
                    AtomicReference<String> currentFogShape = new AtomicReference<>("");
                    FogDataCapability.getFogShape(player).ifPresentOrElse(
                            value -> currentFogShape.set(value.name()),
                            () -> currentFogShape.set("default")
                    );

                    command.getSource().sendSuccess(() -> player.getName().copy().append("'s current fog shape is '").append(currentFogShape.get()).append("'"), true);
                    return 1;
                })))
                .then(literal("start").then(argument("player", EntityArgument.player()).executes(command -> {
                    Player player = EntityArgument.getPlayer(command, "player");
                    AtomicReference<String> currentFogStart = new AtomicReference<>("");
                    FogDataCapability.getFogStart(player).ifPresentOrElse(
                            value -> currentFogStart.set(Float.toString(value)),
                            () -> currentFogStart.set("default")
                    );

                    command.getSource().sendSuccess(() -> player.getName().copy().append("'s current fog start is '").append(currentFogStart.get()).append("'"), true);
                    return 1;
                })))
                .then(literal("end").then(argument("player", EntityArgument.player()).executes(command -> {
                    Player player = EntityArgument.getPlayer(command, "player");
                    AtomicReference<String> currentFogEnd = new AtomicReference<>("");
                    FogDataCapability.getFogEnd(player).ifPresentOrElse(
                            value -> currentFogEnd.set(Float.toString(value)),
                            () -> currentFogEnd.set("default")
                    );

                    command.getSource().sendSuccess(() -> player.getName().copy().append("'s current fog end is '").append(currentFogEnd.get()).append("'"), true);
                    return 1;
                })))
        );
    }
}
