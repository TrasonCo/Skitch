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
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.bukkit.BridgeEventFollow;
import com.trason.skitch.elements.events.bukkit.BridgeEventGoLive;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;
import java.util.Collections;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Viewer Count")
@Description("Returns the viewer count of the Channel when he is Live!.")
@Examples("on twitch message:\n" +
        "\tbroadcast \"%event-viewercount%\"")

public class ExprViewerCount extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprViewerCount.class, String.class, ExpressionType.SIMPLE,
            "[event-]viewer[count] [of [the] [channel] %string%]");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.exprLiveChannel = (Expression<String>) exprs[0];
        return true;
    }


    private Expression<String> exprLiveChannel;

    @Override
    protected String[] get(Event event) {
        String channel = exprLiveChannel.getSingle(event);

        if (channel == null) {
            try {
                if (event instanceof BridgeEventGoLive) {
                    String viewerCount = ((BridgeEventGoLive) event).getEvent().getStream().getViewerCount().toString();
                    return new String[]{viewerCount};
                } else if (event instanceof BridgeEventChat) {
                    StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((BridgeEventChat) event).getEvent().getChannel().getName())).execute();
                    Stream str = list.getStreams().get(0);
                    String viewerCount = str.getViewerCount().toString();
                    return new String[]{viewerCount};
                } else if (event instanceof BridgeEventFollow) {
                    StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((BridgeEventFollow) event).getEvent().getChannel().getName())).execute();
                    Stream str = list.getStreams().get(0);
                    String viewerCount = str.getViewerCount().toString();
                    return new String[]{viewerCount};
                } else if (event instanceof CommandEvent) {
                    StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((CommandEvent) event).getEvent().getChannel().getName())).execute();
                    Stream str = list.getStreams().get(0);
                    String viewerCount = str.getViewerCount().toString();
                    return new String[]{viewerCount};
                }


            } catch (IndexOutOfBoundsException e) {
                return new String[]{"STREAM_OFFLINE"};
            }
        }
        else if (channel != null) {
            try {
                StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(channel)).execute();
                Stream str = list.getStreams().get(0);
                String viewerCount = str.getViewerCount().toString();
                return new String[]{viewerCount};
            } catch (IndexOutOfBoundsException e) {
                return new String[]{"STREAM_OFFLINE"};
            }
        }
        return null;
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
