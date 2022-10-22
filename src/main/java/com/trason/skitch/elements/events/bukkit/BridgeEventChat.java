package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventChat extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ChannelMessageEvent event;
    private final String channelName;

    public BridgeEventChat(ChannelMessageEvent event) {
        this.event = event;
        this.channelName = event.getChannel().getName();
    }

    public ChannelMessageEvent getEvent() {
        return event;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}


