package com.trason.skitch.elements.events.bukkit;


import com.github.twitch4j.events.ChannelClipCreatedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;



public class BridgeEventClip extends Event {

    private static final HandlerList handlers = new HandlerList();


private final ChannelClipCreatedEvent event;


    public BridgeEventClip(ChannelClipCreatedEvent event) {
        this.event = event;
    }

    public ChannelClipCreatedEvent getEvent() {
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
