package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.bukkit.BridgeEventSub;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Subscriber Months")
@Description("Gets the number of months the subscriber has been subscribed to the channel.")
@Examples("set {_months} to subscriber months")
@Since("1.4.1")

public class ExprGetSubscriberMonths extends SimpleExpression<Integer> {

    static {
        Skript.registerExpression(ExprGetSubscriberMonths.class, Integer.class, ExpressionType.SIMPLE, "[event-]subscriber months");
    }
    @Override
    protected Integer @NotNull [] get(@NotNull Event event) {
        if (event instanceof BridgeEventChat) {
            int months = ((BridgeEventChat)event).getEvent().getSubscriberMonths();
            return new Integer[]{months};

        }
        else if (event instanceof CommandEvent) {
            int months = ((CommandEvent)event).getEvent().getSubscriberMonths();
            return new Integer[]{months};
        }
        else if (event instanceof BridgeEventSub) {
            int months = ((BridgeEventSub)event).getEvent().getMonths();
            return new Integer[]{months};
        }
        else
            return new Integer[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "subscriber months";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }
}
