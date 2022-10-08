package com.skitch.trason.addon.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.skitch.trason.addon.elements.events.bukkit.*;
import org.bukkit.event.Event;

public class ExprGiftSubAmount extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprGiftSubAmount.class, String.class, ExpressionType.SIMPLE, "[event-]subamount");
    }

    @Override
    protected String[] get(Event e) {
        if (e  instanceof BridgeEventGiftSub) {
            Integer subAmount = ((BridgeEventGiftSub)e).getEvent().getCount();
            return new String[]{String.valueOf(subAmount)};
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
        return "subamount";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
