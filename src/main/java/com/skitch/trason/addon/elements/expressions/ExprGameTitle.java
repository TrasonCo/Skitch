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

import static com.skitch.trason.addon.elements.effects.EffLoginTwitchBot.client;


public class ExprGameTitle extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprGameTitle.class, String.class, ExpressionType.SIMPLE, "[event-]gametitle");
    }
    @Override
    protected String[] get(Event e) {
        if (e  instanceof BridgeEventGoLive) {
            String gameTitle = ((BridgeEventGoLive)e).getEvent().getStream().getGameName();
            return new String[]{gameTitle};
        }
        if (e instanceof BridgeEventChat) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((BridgeEventChat) e).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String gameTitle = str.getGameName();
            return new String[]{gameTitle};
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
        return "event-gametitle";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
