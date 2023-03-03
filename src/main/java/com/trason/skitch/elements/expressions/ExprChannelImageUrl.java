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
import com.github.twitch4j.helix.domain.UserList;
import com.trason.skitch.elements.events.bukkit.*;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import java.util.Arrays;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
@Name("Channel Image")
@Description({"Returns the channel image of the channel.",
    "This expression returns the channel image Url of the channel.",
    "The Image is a 300x300 pixel image."})
@Examples("set {_channelimage} to channelimage")
public class ExprChannelImageUrl extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprChannelImageUrl.class, String.class, ExpressionType.SIMPLE,
            "[event-]channelimage");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            Integer channelId = Integer.valueOf(((BridgeEventChat) event).getEvent().getChannel().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(channelId)), null).execute();
            String channelimageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{channelimageUrl};
        }
        else if (event instanceof BridgeEventGoLive) {
            Integer channelId = Integer.valueOf(((BridgeEventGoLive) event).getEvent().getChannel().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(channelId)), null).execute();
            String channelimageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{channelimageUrl};
        }
        else if (event instanceof BridgeEventOffLive) {
            Integer channelId = Integer.valueOf(((BridgeEventOffLive) event).getEvent().getChannel().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(channelId)), null).execute();
            String channelimageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{channelimageUrl};
        }
        else if (event instanceof BridgeEventCheer) {
            Integer channelId = Integer.valueOf(((BridgeEventCheer) event).getEvent().getChannel().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(channelId)), null).execute();
            String channelimageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{channelimageUrl};
        }
        else if (event instanceof BridgeEventFollow) {
            Integer channelId = Integer.valueOf(((BridgeEventFollow) event).getEvent().getChannel().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(channelId)), null).execute();
            String channelimageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{channelimageUrl};
        }
        else if (event instanceof BridgeEventGiftSub) {
            Integer channelId = Integer.valueOf(((BridgeEventGiftSub) event).getEvent().getChannel().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(channelId)), null).execute();
            String channelimageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{channelimageUrl};
        }
        else if (event instanceof BridgeEventSub) {
            Integer channelId = Integer.valueOf(((BridgeEventSub) event).getEvent().getChannel().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(channelId)), null).execute();
            String channelimageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{channelimageUrl};
        }
        else if (event instanceof CommandEvent) {
            Integer channelId = Integer.valueOf(((CommandEvent) event).getEvent().getChannel().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(channelId)), null).execute();
            String channelimageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{channelimageUrl};
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
    public String toString( Event e, boolean debug) {
        return "event-channelimage";
    }

}
