package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.BridgeEventChat;

public class EvtLiveChat extends SimpleEvent {

    static {
        //! On Twitch Chat Message
        Skript.registerEvent("Live Chat Message", SimpleEvent.class, BridgeEventChat.class,"twitch [channel] message")

            .description("Triggered when a message is written in the given stream.",
                "Please note that the Channels have to mention in the Login Code")

            .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


            .examples("on twitch message:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_message} to event-livemessage",
                "\tsend \"&6[%{_channel}%] &b| &6[%{_user}%] :&a %{_message}%%nl%\" to console")

            .since("1.0.0");
    }
}
