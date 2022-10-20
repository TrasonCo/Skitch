package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.BridgeEventCheer;

public class EvtCheerEvent extends SimpleEvent {

    static {
        //! On Cheer Event
        Skript.registerEvent("Cheer Event", SimpleEvent.class, BridgeEventCheer.class,"twitch cheer [event]")

            .description("Triggered when a user cheer in the given stream.",
                "Please note that the Channels have to mention in the Login Code")

            .requiredPlugins("Skript 2.6.3+")


            .examples("on twitch cheer event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_bits} to event-cheerbits",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 cheered &a%{_bits}%&6 bits to the channel &a%{_livechannel}%&r\" to console")

            .since("1.0.0");
    }
}
