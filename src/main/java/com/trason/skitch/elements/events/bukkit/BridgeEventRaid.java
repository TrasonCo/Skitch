package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.chat.events.channel.RaidEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;



public class BridgeEventRaid extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final RaidEvent event;

    public BridgeEventRaid(RaidEvent event) {
        this.event = event;
    }

    public RaidEvent getEvent() {
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
