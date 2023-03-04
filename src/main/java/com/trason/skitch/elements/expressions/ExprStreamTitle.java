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
import com.trason.skitch.elements.events.bukkit.*;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import java.util.Collections;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Stream Title")
@Description("Returns the title of the stream.")
@Examples("on twitch message:\n" +
        "\tbroadcast \"%event-streamtitle%\"")

public class ExprStreamTitle extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprStreamTitle.class, String.class, ExpressionType.SIMPLE,
            "[event-]streamtitle");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventGoLive) {
            String title = ((BridgeEventGoLive) event).getEvent().getStream().getTitle();
            return new String[]{title};
        }
        else if (event instanceof BridgeEventChat) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1,
                null, null, null,
                Collections.singletonList(((BridgeEventChat) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String title = str.getTitle();
            return new String[]{title};
        }
        else if (event instanceof CommandEvent) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1,
                null, null, null,
                Collections.singletonList(((CommandEvent) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String title = str.getTitle();
            return new String[]{title};
        }
        else if (event instanceof BridgeEventFollow) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1,
                null, null, null,
                Collections.singletonList(((BridgeEventFollow) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String title = str.getTitle();
            return new String[]{title};
        }
        else if (event instanceof BridgeEventSub) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1,
                null, null, null,
                Collections.singletonList(((BridgeEventSub) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String title = str.getTitle();
            return new String[]{title};
        }
        else if (event instanceof BridgeEventGiftSub) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1,
                null, null, null,
                Collections.singletonList(((BridgeEventGiftSub) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String title = str.getTitle();
            return new String[]{title};
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
        return "event-streamtitle";
    }

}
