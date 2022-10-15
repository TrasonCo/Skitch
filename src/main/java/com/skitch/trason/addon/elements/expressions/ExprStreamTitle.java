package com.skitch.trason.addon.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import com.skitch.trason.addon.elements.events.bukkit.*;
import org.bukkit.event.Event;

import java.util.Collections;

import static com.skitch.trason.addon.elements.stucture.StructTwitch.client;

public class ExprStreamTitle extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprStreamTitle.class, String.class, ExpressionType.SIMPLE, "[event-]streamtitle");
    }

    @Override
    protected  String[] get(Event e) {

        if (e  instanceof BridgeEventGoLive) {
            String title = ((BridgeEventGoLive)e).getEvent().getStream().getTitle();
            return new String[]{title};
        }
        if (e instanceof BridgeEventChat) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((BridgeEventChat) e).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String title = str.getTitle();
            return new String[]{title};
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
        return "event-streamtitle";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
