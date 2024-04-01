package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.ChannelVip;
import com.github.twitch4j.helix.domain.ChannelVipList;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;


import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Channel Vips")
@Description("Gets a list of the channel’s VIPs.")
@Examples("set {_vips::*} to channel vips")
@Since("1.4.1")
public class ExprChannelVips extends SimpleExpression<String> {

    static {
        // This is the registration of the expression
        Skript.registerExpression(ExprChannelVips.class, String.class, ExpressionType.SIMPLE, "[event-]channel vips");
    }

    @Override
    protected String @NotNull [] get(@NotNull Event event) {
        try {
            if (event instanceof BridgeEventChat) {
                String channelId = ((BridgeEventChat) event).getEvent().getChannel().getId();
                ChannelVipList vipList = client.getHelix().getChannelVips(null, channelId, null, 100, null).execute();
                String[] vips = vipList.getData().stream().map(ChannelVip::getUserName).toArray(String[]::new);
                return vips;
            }
            else if (event instanceof CommandEvent) {
                String channelId = ((CommandEvent) event).getEvent().getChannel().getId();
                ChannelVipList vipList = client.getHelix().getChannelVips(null, channelId, null, 100, null).execute();
                String[] vips = vipList.getData().stream().map(ChannelVip::getUserName).toArray(String[]::new);
                return vips;
            }
        } catch (HystrixRuntimeException e) {
            console.error("Error:[getChannelVips] You don't have the broadcaster permission to get the Vip list.");
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
    public @NotNull String toString(Event event, boolean b) {
        return "channel vips";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }
}
