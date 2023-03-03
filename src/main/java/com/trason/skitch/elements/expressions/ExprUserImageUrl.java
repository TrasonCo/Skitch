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
import com.github.twitch4j.helix.domain.GameList;
import com.github.twitch4j.helix.domain.UserList;
import com.netflix.hystrix.HystrixCommand;
import com.trason.skitch.elements.events.bukkit.*;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import java.util.Arrays;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;


@Name("User Image")
@Description({"Returns the user image of the user.",
    "This expression returns the user image Url of the user.",
    "The Image is a 300x300 pixel image."})
@Examples("set {_userimage} to userimage")
public class ExprUserImageUrl extends SimpleExpression<String>{

    static {
        Skript.registerExpression(ExprUserImageUrl.class, String.class, ExpressionType.SIMPLE,
            "[event-]userimage");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            Integer userid = Integer.valueOf(((BridgeEventChat) event).getEvent().getUser().getId());
            HystrixCommand<GameList> rl = client.getHelix().getGames(null, Arrays.asList("dfhdfhgd"), null, null);
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(userid)), null).execute();
            String userImageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{userImageUrl};
        }
        else if (event instanceof BridgeEventCheer) {
            Integer userid = Integer.valueOf(((BridgeEventCheer) event).getEvent().getUser().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(userid)), null).execute();
            String userImageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{userImageUrl};
        }
        else if (event instanceof BridgeEventFollow) {
            Integer userid = Integer.valueOf(((BridgeEventFollow) event).getEvent().getUser().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(userid)), null).execute();
            String userImageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{userImageUrl};
        }
        else if (event instanceof BridgeEventGiftSub) {
            Integer userid = Integer.valueOf(((BridgeEventGiftSub) event).getEvent().getUser().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(userid)), null).execute();
            String userImageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{userImageUrl};
        }
        else if (event instanceof BridgeEventSub) {
            Integer userid = Integer.valueOf(((BridgeEventSub) event).getEvent().getUser().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(userid)), null).execute();
            String userImageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{userImageUrl};
        }
        else if (event instanceof CommandEvent) {
            Integer userid = Integer.valueOf(((CommandEvent) event).getEvent().getUser().getId());
            UserList list = client.getHelix().getUsers(null, Arrays.asList(String.valueOf(userid)), null).execute();
            String userImageUrl = list.getUsers().get(0).getProfileImageUrl();
            return new String[]{userImageUrl};
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
        return "event-ExprUserImage";
    }

}
