package com.skitch.trason.addon;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.*;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import com.skitch.trason.addon.elements.events.bukkit.EventChat;
import com.skitch.trason.addon.elements.events.bukkit.EventGoLive;
import com.skitch.trason.addon.elements.events.bukkit.EventOffLive;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class TwitchEventHandler {

    private final Skitch plugin;

    public TwitchEventHandler(Skitch plugin) {
        this.plugin = plugin;
    }

    @EventSubscriber
    public void onStreamUp(ChannelGoLiveEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new EventGoLive(event));
        });
    }

    @EventSubscriber
    public void onStreamDown(ChannelGoOfflineEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new EventOffLive(event));
        });
    }

    @EventSubscriber
    public void onChat(ChannelMessageEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> { // Call event on the main thread
            Bukkit.getPluginManager().callEvent(new EventChat(event));
        });
    }

    @EventSubscriber
    public void onFollow(FollowEvent event) {
        broadcast(String.format("[Twitch] %s just followed %s!", event.getUser().getName(), event.getChannel().getName()));
    }

    @EventSubscriber
    public void onCheer(CheerEvent event) {
        if (event.getBits() >= 500)
            broadcast(String.format("[Twitch] %s just cheered %d bits for %s!", event.getUser().getName(), event.getBits(), event.getChannel().getName()));
    }

    @EventSubscriber
    public void onSub(SubscriptionEvent event) {
        if (!event.getGifted())
            broadcast(String.format("[Twitch] %s just subscribed to %s for %d months", event.getUser().getName(), event.getChannel().getName(), event.getMonths()));
    }

    @EventSubscriber
    public void onSubMysteryGift(GiftSubscriptionsEvent event) {
        broadcast(String.format("[Twitch] Thank you %s for gifting %d subs to %s", event.getUser().getName(), event.getCount(), event.getChannel().getName()));

        // Create a firework at spawn for a large gift sub event
        // Note: these EventSubscriber's operate on a separate thread so we need to use Bukkit's scheduler to run this on the main thread
        if (event.getCount() >= 25) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                World world = plugin.getServer().getWorlds().get(0);
                Location spawnLocation = world.getSpawnLocation();
                if (world.isChunkLoaded(spawnLocation.getChunk())) {
                    world.spawn(spawnLocation, Firework.class, fw -> {
                        FireworkMeta meta = fw.getFireworkMeta();
                        meta.setPower(Math.min(event.getCount(), 64));
                        meta.addEffect(
                            FireworkEffect.builder()
                                .with(FireworkEffect.Type.STAR)
                                .flicker(event.getCount() >= 50)
                                .trail(event.getCount() >= 100)
                                .withColor(Color.FUCHSIA)
                                .withFade(Color.PURPLE)
                                .build()
                        );
                        fw.setFireworkMeta(meta);
                    });
                }
            });
        }
    }

    private void broadcast(String message) {
        this.plugin.getServer().broadcastMessage(message);
    }

}
