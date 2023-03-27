package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.github.twitch4j.common.util.CryptoUtils;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Twitch Bot Send Live Message")
@Description({"This is to send a message to your Twitch Live Chat"})
@Examples({"on command /test:",
        "\tsend \"Hello World!\" to the livechat \"trason\""})
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
        if (event instanceof BridgeEventChat) {
            String message = exprMessage.getSingle(event);
            String liveChannel = exprLiveChannel.getSingle(event);
            if (message == null || liveChannel == null)
                return;
            client.getChat().sendMessage(liveChannel, message);
        }
        else if (event instanceof CommandEvent) {
            String message = exprMessage.getSingle(event);
            String liveChannel = exprLiveChannel.getSingle(event);
            if (message == null || liveChannel == null)
                return;
            client.getChat().sendMessage(liveChannel, message);
        }
        else {
            String message = exprMessage.getSingle(event);
            String liveChannel = exprLiveChannel.getSingle(event);
            if (message == null || liveChannel == null)
                return;
            client.getChat().sendMessage(liveChannel, message);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send " + exprMessage.toString(e, debug) + " to livechat " + exprLiveChannel.toString(e, debug);
    }

}
