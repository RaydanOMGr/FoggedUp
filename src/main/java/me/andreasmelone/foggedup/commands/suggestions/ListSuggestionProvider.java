package me.andreasmelone.foggedup.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ListSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
    private final List<String> suggestions;

    public ListSuggestionProvider(String... suggestions) {
        this.suggestions = new ArrayList<>(Arrays.asList(suggestions));
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        suggestions.forEach(builder::suggest);
        return builder.buildFuture();
    }
}
