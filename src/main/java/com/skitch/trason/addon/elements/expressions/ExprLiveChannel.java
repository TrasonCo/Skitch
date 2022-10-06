package com.skitch.trason.addon.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.skitch.trason.addon.elements.events.bukkit.EventChat;
import com.skitch.trason.addon.elements.events.bukkit.EventGoLive;
import org.bukkit.event.Event;

public class ExprLiveChannel extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprLiveChannel.class, String.class, ExpressionType.SIMPLE, "[event-]livechannel");
    }

    @Override
    protected
    String[] get(Event e) {
        if (e instanceof EventChat) {
            String channel = ((EventChat) e).getEvent().getChannel().getName();
            return new String[]{channel};
        }

        if (e  instanceof EventGoLive) {
            String channel = ((EventGoLive)e).getEvent().getChannel().getName();
            return new String[]{channel};
        }

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
    public String toString(Event e, boolean debug) {
        return "event-livechannel";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        return true;
    }
}
