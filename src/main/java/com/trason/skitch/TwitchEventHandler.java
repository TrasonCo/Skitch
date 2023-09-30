package com.trason.skitch;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.ChatConnectionStateEvent;
import com.github.twitch4j.chat.events.channel.*;
import com.github.twitch4j.events.ChannelChangeGameEvent;
import com.github.twitch4j.events.ChannelClipCreatedEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import com.github.twitch4j.pubsub.events.PredictionUpdatedEvent;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import com.trason.skitch.elements.events.bukkit.*;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.*;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class TwitchEventHandler {

    private final Skitch plugin;

    public TwitchEventHandler(Skitch plugin) {
        this.plugin = plugin;
    }

    private void callEvent(@NotNull Event event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(event);
        });
    }

    @EventSubscriber
    public void onClipCreate(ChannelClipCreatedEvent event) {
        callEvent(new BridgeEventClip(event));
    }



    @EventSubscriber
    public void onGameTitleChange(ChannelChangeGameEvent event){
        callEvent(new BridgeEventGameChange(event));
    }

    @EventSubscriber
    public void onRaid(RaidEvent event) {
        callEvent(new BridgeEventRaid(event));
    }

    @EventSubscriber
    public void onChannelPointsRedemption(RewardRedeemedEvent event) {
        callEvent(new BridgeEventRewards(event));
    }

    @EventSubscriber
    public void onPredictionEvent(PredictionUpdatedEvent event) {
        if ("RESOLVED".equalsIgnoreCase(event.getEvent().getStatus())) {
            callEvent(new BridgeEventPrediction(event));
        }
    }



    @EventSubscriber
    public void onStreamUp(ChannelGoLiveEvent event) {
        callEvent(new BridgeEventGoLive(event));
    }

    @EventSubscriber
    public void onStreamDown(ChannelGoOfflineEvent event) {
        callEvent(new BridgeEventOffLive(event));
    }

    @EventSubscriber
    public void onChat(ChannelMessageEvent event) {
        callEvent(new BridgeEventChat(event));

        SkitchCommandManager.callEvent(new BridgeEventChat(event).getEvent());
    }


    @EventSubscriber
    public void onFollow(FollowEvent event) {
        callEvent(new BridgeEventFollow(event));
    }

    @EventSubscriber
    public void onCheer(CheerEvent event) {
        callEvent(new BridgeEventCheer(event));
    }

    @EventSubscriber
    public void onSub(SubscriptionEvent event) {
        callEvent(new BridgeEventSub(event));
     }

    @EventSubscriber
    public void onSubMysteryGift(GiftSubscriptionsEvent event) {
        callEvent(new BridgeEventGiftSub(event));
    }



}
