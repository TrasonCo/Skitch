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
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");


       //! On Twitch Chat Message
       //TODO: Change Description and example!
       Skript.registerEvent("Live Chat Message", SimpleEvent.class, BridgeEventChat.class,"twitch [channel] message")

           .description("Called when a given Twitch channel goes Offline",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");


       //! On Follow Event
       //TODO: Change Description and example!
       Skript.registerEvent("Follow Event", SimpleEvent.class, BridgeEventFollow.class,"twitch follow [event]")

           .description("Called when a given Twitch channel goes Offline",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");


       //! On Subscription Event
       //TODO: Change Description and example!
       Skript.registerEvent("Subscription Event", SimpleEvent.class, BridgeEventSub.class,"twitch sub [event]")

           .description("Called when a given Twitch channel goes Offline",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");

       //! On Cheer Event
       //TODO: Change Description and example!
       Skript.registerEvent("Cheer Event", SimpleEvent.class, BridgeEventCheer.class,"twitch cheer [event]")

           .description("Called when a given Twitch channel goes Offline",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");

       //! On GiftSub Event
       //TODO: Change Description and example!
       Skript.registerEvent("Subscription Event", SimpleEvent.class, BridgeEventGiftSub.class,"twitch giftsub [event]")

           .description("Called when a given Twitch channel goes Offline",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");






    }

}
