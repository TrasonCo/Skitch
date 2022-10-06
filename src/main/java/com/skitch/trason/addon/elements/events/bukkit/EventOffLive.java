package com.skitch.trason.addon.elements.events.bukkit;

import com.github.twitch4j.events.ChannelGoOfflineEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class EventOffLive extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ChannelGoOfflineEvent event;
    private final String ChannelName;

    public EventOffLive(ChannelGoOfflineEvent event) {
        this.event = event;
        this.ChannelName = event.getChannel().getName();
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
