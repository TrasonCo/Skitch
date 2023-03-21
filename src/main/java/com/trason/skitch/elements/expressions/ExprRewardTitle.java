package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.trason.skitch.elements.events.bukkit.*;
import org.bukkit.event.Event;

public class ExprRewardTitle extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprRewardTitle.class, String.class, ExpressionType.SIMPLE,
            "[event-]rewardtitle");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventRewards) {
            String rewardTitle = ((BridgeEventRewards) event).getEvent().getRedemption().getReward().getTitle();
            return new String[]{rewardTitle};
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
        return "event-rewardtitle";
    }

}
