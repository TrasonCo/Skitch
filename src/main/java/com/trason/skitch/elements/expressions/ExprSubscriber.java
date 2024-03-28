package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.common.enums.CommandPermission;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class ExprSubscriber extends SimpleExpression<Boolean> {

    static {
        Skript.registerExpression(ExprSubscriber.class, Boolean.class, ExpressionType.SIMPLE,
            "sub[scribe]-status");
    }


    @Override
    protected  Boolean[] get(Event event) {
           if (event instanceof BridgeEventChat) {
               boolean isSubscriber = ((BridgeEventChat) event).getEvent().getPermissions().contains(CommandPermission.SUBSCRIBER);
               return new Boolean[]{isSubscriber};
           }
              else
                return new Boolean[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "subscriber";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }
}
