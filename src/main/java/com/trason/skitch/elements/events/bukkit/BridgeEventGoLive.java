package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.events.ChannelGoLiveEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventGoLive extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ChannelGoLiveEvent event;

    public BridgeEventGoLive(ChannelGoLiveEvent event) {
        this.event = event;
    }

    public ChannelGoLiveEvent getEvent() {
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
