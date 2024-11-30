package me.andreasmelone.foggedup.commands;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import me.andreasmelone.foggedup.capability.FogDataCapability;
import me.andreasmelone.foggedup.commands.suggestions.ListSuggestionProvider;
import me.andreasmelone.foggedup.commands.suggestions.OrDefaultSuggestionProvider;
import me.andreasmelone.foggedup.packet.PacketManager;
import me.andreasmelone.foggedup.packet.SyncFogDataPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.command.EnumArgument;

import java.util.Collection;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SetFogCommand {
    private static final DynamicCommandExceptionType INVALID_ARGUMENT_EXCEPTION = new DynamicCommandExceptionType(arg -> Component.literal("Invalid argument: '" + arg + "'"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("setfog").requires(o -> o.hasPermission(2)).then(literal("shape").then(argument("player", EntityArgument.players()).then(argument("shape", StringArgumentType.string()).suggests(new OrDefaultSuggestionProvider<>(EnumArgument.enumArgument(FogShape.class), "reset")).executes(stack -> {
            String stringShape = StringArgumentType.getString(stack, "shape").toLowerCase();
            Collection<ServerPlayer> players = EntityArgument.getPlayers(stack, "player");
            CommandSourceStack source = stack.getSource();

            if (stringShape.equalsIgnoreCase("reset")) {
                for (ServerPlayer player : players) {
                    FogDataCapability.clearFogShape(player);
                    PacketManager.sendTo(player, new SyncFogDataPacket(FogDataCapability.getCapability(player)));
                }
                String message = players.size() == 1 ? String.format("Reset fog shape for player %s", players.iterator().next().getName().getString()) : String.format("Reset fog shape for %d players", players.size());
                source.sendSuccess(() -> Component.literal(message), true);
                return 1;
            }

            FogShape shape = findEnumIgnoreCase(FogShape.class, stringShape);
            if (shape == null) throw INVALID_ARGUMENT_EXCEPTION.create(stringShape);

            for (ServerPlayer player : players) {
                FogDataCapability.setFogShape(player, shape);
                PacketManager.sendTo(player, new SyncFogDataPacket(FogDataCapability.getCapability(player)));
            }
            String message = players.size() == 1 ? String.format("Set fog shape for player %s to '%s'", players.iterator().next().getName().getString(), shape) : String.format("Set fog shape for %d players to '%s'", players.size(), shape);
            source.sendSuccess(() -> Component.literal(message), true);

            return 1;
        })))).then(literal("start").then(argument("player", EntityArgument.players()).then(argument("start", StringArgumentType.string()).suggests(new ListSuggestionProvider("reset", "0.5", "1", "5")).executes(stack -> {
            String start = StringArgumentType.getString(stack, "start").toLowerCase();
            Collection<ServerPlayer> players = EntityArgument.getPlayers(stack, "player");
            CommandSourceStack source = stack.getSource();

            if (start.equalsIgnoreCase("reset")) {
                for (ServerPlayer player : players) {
                    FogDataCapability.clearFogStart(player);
                    PacketManager.sendTo(player, new SyncFogDataPacket(FogDataCapability.getCapability(player)));
                }
                String message = players.size() == 1 ? String.format("Reset fog start for player %s", players.iterator().next().getName().getString()) : String.format("Reset fog start for %d players", players.size());
                source.sendSuccess(() -> Component.literal(message), true);

                return 1;
            }

            float startValue;
            try {
                startValue = Float.parseFloat(start);
            } catch (NumberFormatException e) {
                throw INVALID_ARGUMENT_EXCEPTION.create(start);
            }

            for (ServerPlayer player : players) {
                FogDataCapability.setFogStart(player, startValue);
                PacketManager.sendTo(player, new SyncFogDataPacket(FogDataCapability.getCapability(player)));
            }
            String message = players.size() == 1 ? String.format("Set fog start for player %s to '%s'", players.iterator().next().getName().getString(), startValue) : String.format("Set fog start for %d players to '%s'", players.size(), startValue);
            source.sendSuccess(() -> Component.literal(message), true);

            return 1;
        })))).then(literal("end").then(argument("player", EntityArgument.players()).then(argument("end", StringArgumentType.string()).suggests(new ListSuggestionProvider("reset", "0.5", "1", "5")).executes(stack -> {
            String end = StringArgumentType.getString(stack, "end").toLowerCase();
            Collection<ServerPlayer> players = EntityArgument.getPlayers(stack, "player");
            CommandSourceStack source = stack.getSource();

            if (end.equalsIgnoreCase("reset")) {
                for (ServerPlayer player : players) {
                    FogDataCapability.clearFogEnd(player);
                    PacketManager.sendTo(player, new SyncFogDataPacket(FogDataCapability.getCapability(player)));
                }
                String message = players.size() == 1 ? String.format("Reset fog end for player %s", players.iterator().next().getName().getString()) : String.format("Reset fog end for %d players", players.size());
                source.sendSuccess(() -> Component.literal(message), true);

                return 1;
            }

            float startValue;
            try {
                startValue = Float.parseFloat(end);
            } catch (NumberFormatException e) {
                throw INVALID_ARGUMENT_EXCEPTION.create(end);
            }

            for (ServerPlayer player : players) {
                FogDataCapability.setFogEnd(player, startValue);
                PacketManager.sendTo(player, new SyncFogDataPacket(FogDataCapability.getCapability(player)));
            }
            String message = players.size() == 1 ? String.format("Set fog end for player %s to '%s'", players.iterator().next().getName().getString(), startValue) : String.format("Set fog end for %d players to '%s'", players.size(), startValue);
            source.sendSuccess(() -> Component.literal(message), true);

            return 1;
        })))));
    }

    private static <T extends Enum<T>> T findEnumIgnoreCase(Class<T> enumClass, String value) {
        if (value == null || enumClass == null) {
            return null;
        }
        for (T constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }
        return null;
    }

}