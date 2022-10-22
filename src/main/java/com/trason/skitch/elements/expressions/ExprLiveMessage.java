package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import org.bukkit.event.Event;

public class ExprLiveMessage extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLiveMessage.class, String.class, ExpressionType.SIMPLE,
            "[event-]livemessage");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(BridgeEventChat.class)) {
            Skript.error("You cannot use event-livemessage outside an LiveMessage event!");
            return false;
        }
        return true;
    }

    @Override
    protected
    String[] get(Event event) {
        return event instanceof BridgeEventChat ?
            new String[]{((BridgeEventChat) event).getEvent().getMessage()} :
            new String[0];
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
        return "event-livemessage";
    }

}
