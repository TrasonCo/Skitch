package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.bukkit.BridgeEventGoLive;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import java.util.Collections;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Live Message")
@Description("Returns the message of the event.")
@Examples("on twitch chat:\n" +
        "\tbroadcast \"%event-livemessage%\"")

public class ExprLiveMessage extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLiveMessage.class, String.class, ExpressionType.SIMPLE,
            "[event-]livemessage");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(BridgeEventChat.class, CommandEvent.class)) {
            Skript.error("You cannot use event-livemessage outside an LiveMessage event!");
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            String message = ((BridgeEventChat)event).getEvent().getMessage();
            return new String[]{message};
        }
        else if (event instanceof CommandEvent) {
            String message = ((CommandEvent)event).getEvent().getMessage();
            return new String[]{message};
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
        return "event-livemessage";
    }

}
