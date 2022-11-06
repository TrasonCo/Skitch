package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.events.ChannelChangeGameEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventGameChange extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final ChannelChangeGameEvent event;
    private final String channelName;

    public BridgeEventGameChange(ChannelChangeGameEvent event) {
        this.event = event;
        this.channelName = event.getChannel().getName();
    }

    public ChannelChangeGameEvent getEvent() {
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

