package com.trason.skitch.elements.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.config.validate.SectionValidator;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
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
        System.out.println("SkitchScope registered");
    }

    public static final SectionValidator validator = new SectionValidator()
        .addEntry("prefixes", true)
        .addEntry("aliases", true)
        .addSection("trigger", false);

    private String command;
    private String arguments;

    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, @NotNull SkriptParser.ParseResult parser) {
        command = parser.regexes.get(0).group(1);
        arguments = parser.regexes.get(0).group(2);
        SectionNode sectionNode = (SectionNode) SkriptLogger.getNode();

        String originalName = ParserInstance.get().getCurrentEventName();
        Class<? extends Event>[] originalEvents = ParserInstance.get().getCurrentEvents();
        Kleenean originalDelay = ParserInstance.get().getHasDelayBefore();
        ParserInstance.get().setCurrentEvent("skitch command", CommandEvent.class);

        SkitchCommand cmd = parse(sectionNode);
        command = cmd == null ? command : cmd.getCommand();

        ParserInstance.get().setCurrentEvent(originalName, originalEvents);
        ParserInstance.get().setHasDelayBefore(originalDelay);
        nukeSectionNode(sectionNode);

        return validate(cmd);
    }

    @Override
    public @Nullable SkitchCommand parse(@NotNull SectionNode node) {
        node.convertToEntries(0);
        final boolean valid = validator.validate(node);
        if (!valid)
            return null;

        return CommandParser.parse(node, this);
    }

    @Override
    public boolean validate(@Nullable SkitchCommand parsedEntity) {
        if (parsedEntity == null)
            return false;

        if (SkitchCommandManager.isRegistered(command)) {
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
