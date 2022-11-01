package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.trason.skitch.elements.events.bukkit.*;
import org.bukkit.event.Event;

public class ExprLiveUserId extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLiveUserId.class, String.class, ExpressionType.SIMPLE,
            "[event-]liveuserid");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            String user = ((BridgeEventChat) event).getEvent().getUser().getId();
            return new String[]{user};
        }
        else if (event instanceof BridgeEventCheer) {
            String user = ((BridgeEventCheer) event).getEvent().getUser().getId();
            return new String[]{user};
        }
        else if (event instanceof BridgeEventFollow) {
            String user = ((BridgeEventFollow) event).getEvent().getUser().getId();
            return new String[]{user};
        }
        else if (event instanceof BridgeEventGiftSub) {
            String user = ((BridgeEventGiftSub) event).getEvent().getUser().getId();
            return new String[]{user};
        }
        else if (event instanceof BridgeEventSub) {
            String user = ((BridgeEventSub) event).getEvent().getUser().getId();
            return new String[]{user};
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
        return "event-liveuserid";
    }

}
