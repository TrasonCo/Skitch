package com.trason.skitch;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.*;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import com.trason.skitch.elements.events.bukkit.*;
import org.bukkit.*;

public class TwitchEventHandler {

    private final Skitch plugin;

    public TwitchEventHandler(Skitch plugin) {
        this.plugin = plugin;
    }

    @EventSubscriber
    public void onStreamUp(ChannelGoLiveEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventGoLive(event));
        });
    }

    @EventSubscriber
    public void onStreamDown(ChannelGoOfflineEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventOffLive(event));
        });
    }

    @EventSubscriber
    public void onChat(ChannelMessageEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventChat(event));
        });
    }

    @EventSubscriber
    public void onFollow(FollowEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventFollow(event));
        });
    }

    @EventSubscriber
    public void onCheer(CheerEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventCheer(event));
        });
    }

    @EventSubscriber
    public void onSub(SubscriptionEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventSub(event));
        });
     }

    @EventSubscriber
    public void onSubMysteryGift(GiftSubscriptionsEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventGiftSub(event));
        });
    }

}
