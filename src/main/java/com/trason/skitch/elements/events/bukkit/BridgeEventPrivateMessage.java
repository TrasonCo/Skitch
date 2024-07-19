package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.common.events.user.PrivateMessageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventPrivateMessage extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final PrivateMessageEvent event;

    public BridgeEventPrivateMessage(PrivateMessageEvent event) {
        this.event = event;
    }

    public PrivateMessageEvent getEvent() {
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
