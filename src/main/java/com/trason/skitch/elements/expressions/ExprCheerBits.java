package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.trason.skitch.elements.events.bukkit.BridgeEventCheer;
import org.bukkit.event.Event;





public class ExprCheerBits extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprCheerBits.class, String.class, ExpressionType.SIMPLE,
            "[event-]cheerbits");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventCheer) {
            Integer bitsAmount = ((BridgeEventCheer)event).getEvent().getBits();
            return new String[]{String.valueOf(bitsAmount)};
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
    public String toString(Event e, boolean debug) {
        return "event-cheerbits";
    }

}
