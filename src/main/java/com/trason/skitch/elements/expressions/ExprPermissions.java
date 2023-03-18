package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.common.enums.CommandPermission;
import com.trason.skitch.elements.events.bukkit.*;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import java.util.Set;

@Name("Permissions")
@Description("Returns the permissions of the event-liveuser.")
@Examples("on twitch message:\n" +
        "\tbroadcast \"%event-event-twitchperms%\"")

public class ExprPermissions extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprPermissions.class, String.class, ExpressionType.SIMPLE,
            "[event-]twitchperms");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(BridgeEventChat.class, CommandEvent.class)) {
            Skript.error("You cannot use event-permissions outside an LiveMessage or Command event!");
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            Set<CommandPermission> perms = ((BridgeEventChat) event).getEvent().getPermissions();
            return perms.stream().map(Enum::name).toArray(String[]::new);
        }

        else if (event instanceof CommandEvent) {
            Set<CommandPermission> perms = ((CommandEvent) event).getEvent().getPermissions();
            return perms.stream().map(Enum::name).toArray(String[]::new);
        }
        else
            return new String[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString( Event e, boolean debug) {
        return "twitch permissions";
    }

}
