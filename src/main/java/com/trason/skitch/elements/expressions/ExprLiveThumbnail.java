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
import com.trason.skitch.elements.events.bukkit.BridgeEventClip;
import com.trason.skitch.elements.events.bukkit.BridgeEventGoLive;
import org.bukkit.event.Event;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Live Thumbnail")
@Description("Returns the thumbnail of the event as URL.")
@Examples("on twitch message:\n" +
        "\tbroadcast \"%event-livethumbnail%\"")

public class ExprLiveThumbnail extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLiveThumbnail.class, String.class, ExpressionType.SIMPLE,
            "[event-]livethumbnail");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventGoLive) {
            String thumbnail = ((BridgeEventGoLive)event).getEvent().getStream().getThumbnailUrl(1280,720);
            return new String[]{thumbnail};
        }
        else if (event instanceof BridgeEventChat) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((BridgeEventChat) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String thumbnail = str.getThumbnailUrl(1280,720);
            return new String[]{thumbnail};
        }
        else if (event instanceof BridgeEventClip) {
            String thumbnail = ((BridgeEventClip) event).getEvent().getClip().getThumbnailUrl();
            return new String[]{thumbnail};
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
        return "event-livethumbnail";
    }
}
