package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.github.twitch4j.TwitchClient;
import com.skitch.trason.addon.elements.stucture.StructTwitch;
import org.bukkit.event.Event;

import static com.skitch.trason.addon.elements.stucture.StructTwitch.client;


public class EffSendLiveMessage extends Effect{




    static {
        Skript.registerEffect(EffSendLiveMessage.class, "send %string% to [the] livechat %string%");
    }


    private String channel;
    private String message;


    private Expression<String> exprMessage;
    private Expression<String> exprLiveChannel;



    public static TwitchClient getClient() {
        return client;
    }

    @Override
    protected void execute(Event event) {
        client = getClient();
        client.getChat().sendMessage(exprMessage.getSingle(event), exprLiveChannel.getSingle(event)); //Variablen werden nicht gesendet



    }

    @Override
    public String toString( Event e, boolean debug) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.exprMessage = (Expression<String>) exprs[0];
        this.exprLiveChannel = (Expression<String>) exprs[1];
        return true;
    }
}
