package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.ChattersList;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

public class ExprChatters extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprChatters.class, String.class, ExpressionType.SIMPLE, "[event-]chatter");
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            String channelID = ((BridgeEventChat)event).getEvent().getChannel().getId();
            String userID = ((BridgeEventChat) event).getEvent().getUser().getId();
            ChattersList chatter = client.getHelix().getChatters(null,channelID,userID,null,null).execute();
            return new String[]{String.valueOf(chatter.getChatters())};
        }
        else if (event instanceof CommandEvent) {
            String channelID = ((CommandEvent)event).getEvent().getChannel().getId();
            String userID = ((CommandEvent) event).getEvent().getUser().getId();
            ChattersList chatter = client.getHelix().getChatters(null,channelID,userID,null,null).execute();
            return new String[]{String.valueOf(chatter.getChatters())};
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
        return "event-chatter";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(BridgeEventChat.class, CommandEvent.class)) {
            Skript.error("You cannot use event-chatter outside an LiveMessage or Command event!");
            return false;
        }
        return true;
    }
}
