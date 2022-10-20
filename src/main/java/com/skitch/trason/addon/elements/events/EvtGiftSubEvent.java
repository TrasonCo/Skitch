package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.BridgeEventGiftSub;


@Name("Gift Subscription Event")

@Description({"Triggered when a user subscribe in the given stream.",
            "Please note that the Channels have to mention in the Login Code"})

@Examples({"on twitch giftsub event:",
    "\tset {_channel} to event-livechannel",
    "\tset {_user} to event-liveuser",
    "\tset {_amount} to event-subamount",
    "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 gifted &a%{_amount}%&6 subs to the channel &a%{_livechannel}%&r\" to console"})

@Since("1.0.0")

@RequiredPlugins("Skript 2.6.3+")

public class EvtGiftSubEvent extends SimpleEvent {

    static {
        //! On GiftSub Event
        Skript.registerEvent("Gift Subscription Event", SimpleEvent.class, BridgeEventGiftSub.class,"twitch giftsub [event]");
    }
}
