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
import org.bukkit.event.Event;

import java.util.Collections;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Live Uptime")
@Description("Returns the uptime of the event.")
@Examples("on twitch message:\n" +
        "\tbroadcast \"%event-liveuptime%\"")

public class ExprLiveUptime extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLiveUptime.class, String.class, ExpressionType.SIMPLE,
            "[event-]uptime");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        try {
            if (event instanceof BridgeEventChat) {
                StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((BridgeEventChat) event).getEvent().getChannel().getName())).execute();
                Stream str = list.getStreams().get(0);
                long time = str.getUptime().getSeconds();
                return new String[]{String.format("%d:%02d:%02d", time / 3600, (time % 3600) / 60, (time % 60))};
            }
        } catch (IndexOutOfBoundsException e) {
            return new String[]{"STREAM_OFFLINE"};
        }
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
        return "event-liveuptime";
    }

}
