package com.trason.skitch.elements.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.config.validate.SectionValidator;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import com.trason.skitch.SkitchCommandManager;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.BaseScope;
import com.trason.skitch.util.commands.CommandParser;
import com.trason.skitch.util.commands.SkitchCommand;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkitchScope extends BaseScope<SkitchCommand> {

    static {
        Skript.registerEvent("Skitch Command", SkitchScope.class, CommandEvent.class, "skitch command <([^\\s]+)( .+)?$>")
            .description("TODO: Write a description about this.");
    }

    public static final SectionValidator validator = new SectionValidator()
        .addEntry("prefixes", true)
        .addEntry("aliases", true)
        .addSection("trigger", false);

    @Override
    public void init(Literal<?> @NotNull [] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult, SectionNode node) {
        super.init(args, matchedPattern, parseResult, node);
    }

    @Override
    public @Nullable SkitchCommand parse(@NotNull SectionNode node) {
        node.convertToEntries(0);
        if (!validator.validate(node))
            return null;

        return CommandParser.parse(node, this);
    }

    @Override
    public boolean validate(@Nullable SkitchCommand parsedEntity) {
        if (parsedEntity == null)
            return false;

        if (SkitchCommandManager.isRegistered(parsedEntity.getCommand())) {
            Skript.error("A Skitch command with the name '" + parsedEntity.getCommand() + "' is already registered.");
            return false;
        }

        SkitchCommandManager.registerCommand(parsedEntity);
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "skitch command";
    }

}
