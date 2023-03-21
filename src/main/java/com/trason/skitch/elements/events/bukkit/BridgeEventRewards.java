package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.pubsub.events.ChannelPointsRedemptionEvent;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BridgeEventRewards extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final RewardRedeemedEvent event;


    public BridgeEventRewards(RewardRedeemedEvent event) {
        this.event = event;
    }

    public RewardRedeemedEvent getEvent() {
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
