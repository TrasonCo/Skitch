package com.skitch.trason.addon;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.*;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import com.skitch.trason.addon.elements.events.bukkit.*;
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
//        broadcast(String.format("[Twitch] %s just followed %s!", event.getUser().getName(), event.getChannel().getName()));
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventFollow(event));
        });
    }

    @EventSubscriber
    public void onCheer(CheerEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventCheer(event));
        });
        //          broadcast(String.format("[Twitch] %s just cheered %d bits for %s!", event.getUser().getName(), event.getBits(), event.getChannel().getName()));
    }

    @EventSubscriber
    public void onSub(SubscriptionEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventSub(event));
        });
        //          broadcast(String.format("[Twitch] %s just subscribed to %s for %d months", event.getUser().getName(), event.getChannel().getName(), event.getMonths()));
    }

    @EventSubscriber
    public void onSubMysteryGift(GiftSubscriptionsEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new BridgeEventGiftSub(event));
        });
        //broadcast(String.format("[Twitch] Thank you %s for gifting %d subs to %s", event.getUser().getName(), event.getCount(), event.getChannel().getName()));



}
}
