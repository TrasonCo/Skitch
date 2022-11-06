package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

// Not sure if you wanted to add documentation here or not ! ^-^
public class EffSendLiveMessage extends Effect {

    static {
        Skript.registerEffect(EffSendLiveMessage.class, "send %string% to [the] livechat %string%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.exprMessage = (Expression<String>) exprs[0];
        this.exprLiveChannel = (Expression<String>) exprs[1];
        return true;
    }

    private Expression<String> exprMessage;
    private Expression<String> exprLiveChannel;

    @Override
    protected void execute(Event event) {
        String message = exprMessage.getSingle(event);
        String liveChannel = exprLiveChannel.getSingle(event);
        // We check if the values are null, you should always do this for expression :)
        if (message == null || liveChannel == null)
            return;
        client.getChat().sendMessage(liveChannel, message);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send " + exprMessage.toString(e, debug) + " to livechat " + exprLiveChannel.toString(e, debug);
    }

}
