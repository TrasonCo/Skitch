package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.pubsub.events.PredictionUpdatedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventPrediction extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final PredictionUpdatedEvent event;


    public BridgeEventPrediction(PredictionUpdatedEvent event) {
        this.event = event;
    }

    public PredictionUpdatedEvent getEvent() {
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
