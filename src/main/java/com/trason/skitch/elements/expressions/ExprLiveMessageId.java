package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprLiveMessageId extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLiveMessageId.class, String.class, ExpressionType.SIMPLE,
            "[event-]livemessageid");
    }

    @Override
    protected String @NotNull [] get(@NotNull Event event) {
        if (event instanceof BridgeEventChat){
            String messageId = ((BridgeEventChat) event).getEvent().getMessageEvent().getMessageId().toString();
            return new String[]{messageId};
        }
        return new String[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "event-livemessageid";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }
}
