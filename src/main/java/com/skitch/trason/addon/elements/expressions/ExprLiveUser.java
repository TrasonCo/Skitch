package com.skitch.trason.addon.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.skitch.trason.addon.elements.events.bukkit.*;
import org.bukkit.event.Event;

public class ExprLiveUser extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLiveUser.class, String.class, ExpressionType.SIMPLE, "[event-]liveuser");
    }


    @Override
    protected  String[] get(Event e) {
        if (e instanceof BridgeEventChat) {
            String user = ((BridgeEventChat) e).getEvent().getUser().getName();
            return new String[]{user};
        }
        if (e  instanceof BridgeEventCheer) {
            String user = ((BridgeEventCheer)e).getEvent().getUser().getName();
            return new String[]{user};
        }
        if (e  instanceof BridgeEventFollow) {
            String user = ((BridgeEventFollow)e).getEvent().getUser().getName();
            return new String[]{user};
        }
        if (e  instanceof BridgeEventGiftSub) {
            String user = ((BridgeEventGiftSub)e).getEvent().getUser().getName();
            return new String[]{user};
        }
        if (e  instanceof BridgeEventSub) {
            String user = ((BridgeEventSub)e).getEvent().getUser().getName();
            return new String[]{user};
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
    public String toString( Event e, boolean debug) {
        return "event-liveuser";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
