package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.events.ChannelGoOfflineEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventOffLive extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ChannelGoOfflineEvent event;

    public BridgeEventOffLive(ChannelGoOfflineEvent event) {
        this.event = event;
    }

    public ChannelGoOfflineEvent getEvent() {
        return event;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() { return handlers; }

}
