package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.BridgeEventFollow;

public class EvtFollowEvent extends SimpleEvent {

    static {
        //! On Follow Event
        Skript.registerEvent("Follow Event", SimpleEvent.class, BridgeEventFollow.class,"twitch follow [event]")

            .description("Triggered when a user follow in the given stream.",
                "Please note that the Channels have to mention in the Login Code")

            .requiredPlugins("Skript 2.6.3+")


            .examples("on twitch follow event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 followed the channel &a%{_livechannel}%&r\" to console")

            .since("1.0.0");
    }
}
