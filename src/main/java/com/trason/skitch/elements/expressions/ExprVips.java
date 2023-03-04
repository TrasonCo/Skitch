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
import com.github.twitch4j.helix.domain.ChannelVipList;
import com.github.twitch4j.helix.domain.ChattersList;
import com.github.twitch4j.tmi.domain.Chatters;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;

import java.util.Arrays;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Vips")
@Description("Returns the vips from the Broadcaster.")
@Examples("on twitch message:\n" +
        "\tbroadcast \"%event-vips%\"")
public class ExprVips extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprVips.class, String.class, ExpressionType.SIMPLE, "[event-]vips");
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            String channelID = ((BridgeEventChat)event).getEvent().getChannel().getId();
            String userID = ((CommandEvent) event).getEvent().getUser().getId();
            ChattersList chatter = client.getHelix().getChatters(null,channelID,userID,null,null).execute();
            String allChatters = chatter.getChatters().toString();
            ChannelVipList vips = client.getHelix().getChannelVips(null,channelID, Arrays.asList(allChatters),null,null).execute();
            return new String[]{String.valueOf(vips)};
        }
        else if (event instanceof CommandEvent) {
            String channelID = ((BridgeEventChat)event).getEvent().getChannel().getId();
            String userID = ((CommandEvent) event).getEvent().getUser().getId();
            ChattersList chatter = client.getHelix().getChatters(null,channelID,userID,null,null).execute();
            String allChatters = chatter.getChatters().toString();
            ChannelVipList vips = client.getHelix().getChannelVips(null,channelID, Arrays.asList(allChatters),null,null).execute();
            return new String[]{String.valueOf(vips)};
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
    public String toString(Event event, boolean debug) {
        return "event-vips";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
