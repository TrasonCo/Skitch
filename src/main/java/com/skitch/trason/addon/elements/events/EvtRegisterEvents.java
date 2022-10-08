package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.*;


public class EvtRegisterEvents {

   static {


       //! Go Live/Online Event
       Skript.registerEvent("Going Live", SimpleEvent.class, BridgeEventGoLive.class,"twitch [channel] goes live")

           .description("Called when a given Twitch channel goes Live",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")

           .examples("on twitch channel goes life:",
               "\tset {_channel} to event-livechannel",
               "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &cLive&r\" to console")

           .since("1.0.0");


       //! Go Offline Event
       Skript.registerEvent("Stop Live", SimpleEvent.class, BridgeEventOffLive.class,"twitch [channel] goes offline")

           .description("Called when a given Twitch channel goes Offline",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");


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


       //! On Follow Event
       Skript.registerEvent("Follow Event", SimpleEvent.class, BridgeEventFollow.class,"twitch follow [event]")

           .description("Triggered when a user follow in the given stream.",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch follow event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 followed the channel &a%{_livechannel}%&r\" to console")

           .since("1.0.0");


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


       //! On Cheer Event
       Skript.registerEvent("Cheer Event", SimpleEvent.class, BridgeEventCheer.class,"twitch cheer [event]")

           .description("Triggered when a user cheer in the given stream.",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch cheer event:",
               "\tset {_channel} to event-livechannel",
               "\tset {_user} to event-liveuser",
               "\tset {_bits} to event-cheerbits",
               "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 cheered &a%{_bits}%&6 bits to the channel &a%{_livechannel}%&r\" to console")

           .since("1.0.0");


       //! On GiftSub Event
       Skript.registerEvent("Gift Subscription Event", SimpleEvent.class, BridgeEventGiftSub.class,"twitch giftsub [event]")

           .description("Triggered when a user subscribe in the given stream.",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch giftsub event:",
               "\tset {_channel} to event-livechannel",
               "\tset {_user} to event-liveuser",
               "\tset {_amount} to event-subamount",
               "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 gifted &a%{_amount}%&6 subs to the channel &a%{_livechannel}%&r\" to console")

           .since("1.0.0");






    }

}
