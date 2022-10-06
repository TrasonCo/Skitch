package com.skitch.trason.addon.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.skitch.trason.addon.elements.events.bukkit.EventChat;
import com.skitch.trason.addon.elements.events.bukkit.EventGoLive;
import com.skitch.trason.addon.elements.events.bukkit.EventOffLive;


public class EvtRegisterEvents {

   static {
       Skript.registerEvent("Going Live", SimpleEvent.class, EventGoLive.class,"twitch [channel] goes live")

           .description("Called when a given Twitch channel goes Live",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")

           .examples("on twitch channel goes life:",
               "\tset {_channel} to event-livechannel",
               "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &cLive&r\" to console")

           .since("1.0.0");



       Skript.registerEvent("Stop Live", SimpleEvent.class, EventOffLive.class,"twitch [channel] goes offline")

           .description("Called when a given Twitch channel goes Offline",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");


       Skript.registerEvent("Live Chat Message", SimpleEvent.class, EventChat.class,"twitch [channel] message")

           .description("Called when a given Twitch channel goes Offline",
               "Please note that the Channels have to mention in the Login Code")

           .requiredPlugins("Skript 2.6.3+ (Stucture-API)")


           .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\t\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")

           .since("1.0.0");






    }

}
