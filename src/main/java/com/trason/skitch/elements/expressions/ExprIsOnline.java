package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import java.util.Collections;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;


@Name("Twitch Channel Status")
@Description("Returns the status of the Channel! If the channel is online, it will return 'ONLINE' else 'OFFLINE'. needs the channel name as input.")
@Examples("on twitch message:\n" +
        "\tbroadcast \"%twitch channel status of event-channel%\"")
public class ExprIsOnline extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprIsOnline.class, String.class, ExpressionType.SIMPLE,
                "twitch channel status of %string%");
    }

    private Expression<String> exprLiveChannel;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.exprLiveChannel = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected String[] get(Event event) {
        try {
            if (event instanceof BridgeEventChat) {
                String channelName = exprLiveChannel.getSingle(event);
                StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(channelName)).execute();
                Stream str = list.getStreams().get(0);
                return new String[]{"ONLINE"};
            }
            else if (event instanceof CommandEvent) {
                String channelName = exprLiveChannel.getSingle(event);
                StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(channelName)).execute();
                Stream str = list.getStreams().get(0);
                return new String[]{"ONLINE"};
            }
            else {
                String channelName = exprLiveChannel.getSingle(event);
                StreamList list = client.getHelix().getStreams(null, null, null, 1, null, null, null, Collections.singletonList(channelName)).execute();
                Stream str = list.getStreams().get(0);
                return new String[]{"ONLINE"};
            }
        } catch (IndexOutOfBoundsException e) {
            return new String[]{"OFFLINE"};
        }
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
        return "online status of a channel";
    }


}
