package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.trason.skitch.elements.events.bukkit.BridgeEventRewards;
import org.bukkit.event.Event;

@Name("Reward Cost")
@Description("Returns the cost of the reward.")
@Examples("on twitch reward redeemed:\n" +
        "\tbroadcast \"%event-rewardcost%\"")
public class ExprRewardCost extends SimpleExpression<Number> {
    static {
        Skript.registerExpression(ExprRewardCost.class, Number.class, ExpressionType.SIMPLE,
            "[event-]rewardcost");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected Number[] get(Event event) {
        if (event instanceof BridgeEventRewards) {
            Number rewardTitle = ((BridgeEventRewards) event).getEvent().getRedemption().getReward().getCost();
            return new Number[]{rewardTitle};
        }

        else
            return new Number[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "event-rewardcost";
    }

}


