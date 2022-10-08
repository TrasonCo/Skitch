package com.skitch.trason.addon.elements.events.bukkit;

import com.github.twitch4j.chat.events.channel.GiftSubscriptionsEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventGiftSub extends Event {

    public static HandlerList getHandlerList() { return handlers; }
    private static final HandlerList handlers = new HandlerList();
    private final GiftSubscriptionsEvent event;
    public BridgeEventGiftSub(GiftSubscriptionsEvent event) {
        this.event = event;
    }

    public GiftSubscriptionsEvent getEvent() {
        return event;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
