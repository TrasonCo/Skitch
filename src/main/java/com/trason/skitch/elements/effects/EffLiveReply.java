package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import com.github.twitch4j.common.util.CryptoUtils;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Twitch Reply")
@Description("Reply to a twitch chat message")
@Examples("twitch reply with \"Hello!\"")
public class EffLiveReply extends AsyncEffect {

    static {
        Skript.registerEffect(EffLiveReply.class, "twitch reply with %string%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.exprMessage = (Expression<String>) exprs[0];
        return true;
    }

    private Expression<String> exprMessage;

    @Override
    protected void execute(Event event) {
        if (event instanceof BridgeEventChat) {
            String message = exprMessage.getSingle(event);
            String channel = ((BridgeEventChat) event).getEvent().getChannel().getName();
            if (message == null)
                return;
            client.getChat().sendMessage(((BridgeEventChat) event).getEvent().getChannel().getName(), message, CryptoUtils.generateNonce(32), ((BridgeEventChat) event).getEvent().getMessageEvent().getMessageId().orElse(null));
        }
        else if (event instanceof CommandEvent) {
            String message = exprMessage.getSingle(event);
            String channel = ((CommandEvent) event).getEvent().getChannel().getName();
            // We check if the values are null, you should always do this for expression :)
            if (message == null)
                return;
            client.getChat().sendMessage(((CommandEvent) event).getEvent().getChannel().getName(), message, CryptoUtils.generateNonce(32), ((CommandEvent) event).getEvent().getMessageEvent().getMessageId().orElse(null));

        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        // NEVER return null here! It's used for debugging and more, so it's important to return a valid string representing the effect.
        return "send " + exprMessage.toString(e, debug) + " to livechat " + exprMessage.toString(e, debug);
    }

}
