package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.BridgeEventGoLive;


public class EvtGoLive extends SimpleEvent {

    static {
        //! Go Live/Online Event
        Skript.registerEvent("Going Live", SimpleEvent.class, BridgeEventGoLive.class,"twitch [channel] goes live")

            .description("Called when a given Twitch channel goes Live",
                "Please note that the Channels have to mention in the Login Code")

            .requiredPlugins("Skript 2.6.3+")

            .examples("on twitch channel goes life:",
                "\tset {_channel} to event-livechannel",
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &cLive&r\" to console")

            .since("1.0.0");
    }
}
