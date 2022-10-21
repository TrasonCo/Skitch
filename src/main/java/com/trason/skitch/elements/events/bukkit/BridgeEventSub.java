package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.chat.events.channel.SubscriptionEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventSub extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final SubscriptionEvent event;

    public BridgeEventSub(SubscriptionEvent event) {
        this.event = event;
    }

    public SubscriptionEvent getEvent() {
        return event;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() { return handlers; }

}
