package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.BridgeEventOffLive;

public class EvtStopLive extends SimpleEvent {

    static {
        //! Go Offline Event
        Skript.registerEvent("Stop Live", SimpleEvent.class, BridgeEventOffLive.class,"twitch [channel] goes offline")

            .description("Called when a given Twitch channel goes Offline",
                "Please note that the Channels have to mention in the Login Code")

            .requiredPlugins("Skript 2.6.3+")


            .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

            .since("1.0.0");
    }
}
