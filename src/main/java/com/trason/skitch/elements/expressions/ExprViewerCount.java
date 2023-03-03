package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
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

public class ExprViewerCount extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprViewerCount.class, String.class, ExpressionType.SIMPLE,
            "[event-]viewer[count]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventGoLive) {
            String viewerCount = ((BridgeEventGoLive) event).getEvent().getStream().getViewerCount().toString();
            return new String[]{viewerCount};
        }
        else if (event instanceof BridgeEventChat) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((BridgeEventChat) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String viewerCount = str.getViewerCount().toString();
            return new String[]{viewerCount};
        }
        else if (event instanceof CommandEvent) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((CommandEvent) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String viewerCount = str.getViewerCount().toString();
            return new String[]{viewerCount};
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
        return "event-viewer";
    }

}
