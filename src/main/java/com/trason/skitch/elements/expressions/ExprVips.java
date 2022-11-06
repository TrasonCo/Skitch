package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.tmi.domain.Chatters;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import org.bukkit.event.Event;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

public class ExprVips extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprVips.class, String.class, ExpressionType.SIMPLE, "[event-]vips");
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            String channelName = ((BridgeEventChat)event).getEvent().getChannel().getName();
            Chatters chatter = client.getMessagingInterface().getChatters(channelName).execute();
            return new String[]{String.valueOf(chatter.getVips())};
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
    public String toString(Event event, boolean debug) {
        return "event-vips";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
