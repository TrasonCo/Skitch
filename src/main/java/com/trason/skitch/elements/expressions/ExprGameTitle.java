package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import com.trason.skitch.elements.events.bukkit.*;
import org.bukkit.event.Event;

import java.util.Collections;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;


public class ExprGameTitle extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprGameTitle.class, String.class, ExpressionType.SIMPLE,
            "[event-]gametitle");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventGoLive) {
            String gameTitle = ((BridgeEventGoLive)event).getEvent().getStream().getGameName();
            return new String[]{gameTitle};
        }
        else if (event instanceof BridgeEventChat) {
            StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(((BridgeEventChat) event).getEvent().getChannel().getName())).execute();
            Stream str = list.getStreams().get(0);
            String gameTitle = str.getGameName();
            return new String[]{gameTitle};
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
        return "event-gametitle";
    }

}
