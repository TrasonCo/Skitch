package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.github.twitch4j.common.util.CryptoUtils;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import org.bukkit.event.Event;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

// Not sure if you wanted to add documentation here or not ! ^-^
public class EffLiveReply extends Effect {

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
        String message = exprMessage.getSingle(event);
        String channel = ((BridgeEventChat) event).getEvent().getChannel().getName();
        // We check if the values are null, you should always do this for expression :)
        if (message == null)
            return;
        client.getChat().sendMessage(((BridgeEventChat) event).getEvent().getChannel().getName(), message, CryptoUtils.generateNonce(32), ((BridgeEventChat) event).getEvent().getMessageEvent().getMessageId().orElse(null)); //Variablen werden nicht gesendet
    }

    @Override
    public String toString(Event e, boolean debug) {
        // NEVER return null here! It's used for debugging and more, so it's important to return a valid string representing the effect.
        return "send " + exprMessage.toString(e, debug) + " to livechat " + exprMessage.toString(e, debug);
    }

}
