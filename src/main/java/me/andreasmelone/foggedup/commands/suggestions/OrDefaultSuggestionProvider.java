package me.andreasmelone.foggedup.commands.suggestions;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;

import java.util.concurrent.CompletableFuture;

public class OrDefaultSuggestionProvider<T extends ArgumentType<?>> implements SuggestionProvider<CommandSourceStack> {
    private final T parent;
    private final String defaultValue;

    public OrDefaultSuggestionProvider(T parent, String defaultValue) {
        this.parent = parent;
        this.defaultValue = defaultValue;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,
                                                         SuggestionsBuilder builder) {
        builder.suggest(defaultValue);
        return parent.listSuggestions(context, builder);
    }
}
