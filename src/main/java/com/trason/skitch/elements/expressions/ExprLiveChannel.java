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
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

@Name("Live Channel")
@Description("Returns the channel name of the event.")
@Examples("on twitch cheer:\n" +
        "\tbroadcast \"%event-livechannel%\"")

public class ExprLiveChannel extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLiveChannel.class, String.class, ExpressionType.SIMPLE,
            "[event-]livechannel");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            String channel = ((BridgeEventChat) event).getEvent().getChannel().getName();
            return new String[]{channel};
        }
        else if (event instanceof BridgeEventCheer) {
            String channel = ((BridgeEventCheer) event).getEvent().getChannel().getName();
            return new String[]{channel};
        }
        else if (event instanceof BridgeEventFollow) {
            String channel = ((BridgeEventFollow) event).getEvent().getChannel().getName();
            return new String[]{channel};
        }
        else if (event instanceof BridgeEventGiftSub) {
            String channel = ((BridgeEventGiftSub) event).getEvent().getChannel().getName();
            return new String[]{channel};
        }
        else if (event instanceof BridgeEventGoLive) {
            String channel = ((BridgeEventGoLive) event).getEvent().getChannel().getName();
            return new String[]{channel};
        }
        else if (event instanceof BridgeEventOffLive) {
            String channel = ((BridgeEventOffLive) event).getEvent().getChannel().getName();
            return new String[]{channel};
        }
        else if (event instanceof BridgeEventSub) {
            String channel = ((BridgeEventSub) event).getEvent().getChannel().getName();
            return new String[]{channel};
        }

        else if (event instanceof CommandEvent) {
            String channel = ((CommandEvent) event).getEvent().getChannel().getName();
            return new String[]{channel};
        }
        else if (event instanceof BridgeEventClip) {
            String user = ((BridgeEventClip) event).getEvent().getClip().getBroadcasterName();
            return new String[]{user};
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
        return "event-livechannel";
    }

}
