package com.trason.skitch.elements.events.bukkit;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class BridgeEventChat extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ChannelMessageEvent event;

    public BridgeEventChat(ChannelMessageEvent event) {
        this.event = event;
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

    public void runAsyncTask(Consumer<BridgeEventChat> callback) {
        // Use BukkitRunnable to run the callback asynchronously
        new BukkitRunnable() {
            @Override
            public void run() {
                callback.accept(BridgeEventChat.this);
            }
        }.runTaskAsynchronously(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Skitch")));
    }
}
