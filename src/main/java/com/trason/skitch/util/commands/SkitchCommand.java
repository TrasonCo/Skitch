package com.trason.skitch.util.commands;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import com.trason.skitch.elements.events.custom.CommandEvent;

import java.util.List;

public class SkitchCommand {

    private final String command;
    private final List<Expression<String>> prefixes;
    private final List<String> aliases;
    private final List<Argument<?>> arguments;
    private final List<TriggerItem> triggers;
    private final String pattern;

    public SkitchCommand(List<Expression<String>> prefixes, List<String> aliases, String command, List<Argument<?>> arguments,
                         List<TriggerItem> triggers, String pattern) {
        this.prefixes = prefixes;
        this.aliases = aliases;
        this.command = command;
        this.arguments = arguments;
        this.triggers = triggers;
        this.pattern = pattern;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPattern() {
        return pattern;
    }

    public List<Argument<?>> getArguments() {
        return arguments;
    }

    public List<Expression<String>> getPrefixes() {
        return prefixes;
    }

    public List<TriggerItem> getTriggers() {
        return triggers;
    }

    public boolean execute(CommandEvent event) {

        ParseLogHandler log = SkriptLogger.startParseLogHandler();

        try {

            boolean ok = CommandParser.parseArguments(event.getArguments(), this, event);
            if (!ok)
                return false;

            if (getTriggers().isEmpty())
                return false;
            TriggerItem.walk(getTriggers().get(0), event);

        } finally {
            log.stop();
        }

        return true;

    }
}
