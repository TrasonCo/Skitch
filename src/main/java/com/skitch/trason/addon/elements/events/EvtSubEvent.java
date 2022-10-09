package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.BridgeEventSub;

public class EvtSubEvent extends SimpleEvent {

    static {
        //! On Subscription Event
        Skript.registerEvent("Subscription Event", SimpleEvent.class, BridgeEventSub.class,"twitch sub [event]")

            .description("Triggered when a user subscribe in the given stream.",
                "Please note that the Channels have to mention in the Login Code")

            .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


            .examples("on twitch sub event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_tier} to event-subtier",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 subscribed to the channel &a%{_livechannel}%&6 with a &a%{_tier}%&6 sub!\" to console")

            .since("1.0.0");
    }
}
