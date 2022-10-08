package com.skitch.trason.addon.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.skitch.trason.addon.elements.events.bukkit.*;
import org.bukkit.event.Event;

public class ExprGameTitle extends SimpleExpression<String> {
    @Override
    protected String[] get(Event e) {
        if (e  instanceof BridgeEventGoLive) {
            String channel = ((BridgeEventGoLive)e).getEvent().getStream().getTitle();
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
        return "event-gametitle";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
