package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.chat.events.channel.CheerEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventCheer extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final CheerEvent event;

    public BridgeEventCheer(CheerEvent event) {
        this.event = event;
    }

    public CheerEvent getEvent(){
        return event;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() { return handlers; }

}
