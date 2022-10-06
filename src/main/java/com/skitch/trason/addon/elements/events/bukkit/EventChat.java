package com.skitch.trason.addon.elements.events.bukkit;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class EventChat extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ChannelMessageEvent event;
    private final String ChannelName;

    public EventChat(ChannelMessageEvent event) {
        this.event = event;
        this.ChannelName = event.getChannel().getName();

    }

    public ChannelMessageEvent getEvent() {
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


