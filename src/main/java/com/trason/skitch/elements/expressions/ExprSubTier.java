package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.trason.skitch.elements.events.bukkit.*;
import org.bukkit.event.Event;

@Name("Sub Tier")
@Description("Returns the sub tier of the sub. NONE, TIER1, TIER2, TIER3, TWITCH_PRIME")
@Examples("on twitch sub:\n" +
        "\tbroadcast \"%event-subtier%\"")

public class ExprSubTier extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprSubTier.class, String.class, ExpressionType.SIMPLE,
            "[event-]subtier");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventGiftSub) {
            String subTier = ((BridgeEventGiftSub)event).getEvent().getSubscriptionPlan();
            return new String[]{subTier};
        }
        else if (event instanceof BridgeEventSub) {
            String subTier = ((BridgeEventSub)event).getEvent().getSubscriptionPlan();
            return new String[]{subTier};
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
        return "subtier";
    }

}
