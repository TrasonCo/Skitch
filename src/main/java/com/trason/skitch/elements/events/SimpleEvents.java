package com.trason.skitch.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import com.trason.skitch.elements.events.bukkit.*;


public class SimpleEvents {

    static {
        //! On Subscription Event
        Skript.registerEvent("Subscription Event", SimpleEvent.class, BridgeEventSub.class,
                "twitch sub [event]")
            .description("Triggered when a user subscribe in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch sub event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_tier} to event-subtier",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 subscribed to the channel &a%{_livechannel}%&6 with a &a%{_tier}%&6 sub!\" to console")
            .since("1.0.0");

        //! Go Offline Event
        Skript.registerEvent("Stop Live", SimpleEvent.class, BridgeEventOffLive.class,
                "twitch [channel] (goes|going) offline")
            .description("Called when a given Twitch channel goes Offline",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch channel goes offline:",
                "\tset {_channel} to event-livechannel",
                "\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &bOFFLINE&r\" to console")
            .since("1.0.0");

        //! On Twitch Chat Message
        Skript.registerEvent("Live Chat Message", SimpleEvent.class, BridgeEventChat.class,
                "twitch [channel] message")
            .description("Triggered when a message is written in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch message:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_message} to event-livemessage",
                "\tsend \"&6[%{_channel}%] &b| &6[%{_user}%] :&a %{_message}%%nl%\" to console")
            .since("1.0.0");

        //! Go Live/Online Event
        Skript.registerEvent("Going Live", SimpleEvent.class, BridgeEventGoLive.class,
                "twitch [channel] (goes|going) live")
            .description("Called when a given Twitch channel goes Live",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch channel goes life:",
                "\tset {_channel} to event-livechannel",
                "\tsend \"&a[&5TWITCH&a]&6 The channel &a%{_livechannel}%&6 is now &cLive&r\" to console")
            .since("1.0.0");

        //! On GiftSub Event
        Skript.registerEvent("Gift Subscription Event", SimpleEvent.class, BridgeEventGiftSub.class,
            "twitch giftsub [event]")
            .description("Triggered when a user subscribe in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch giftsub event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_amount} to event-subamount",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 gifted &a%{_amount}%&6 subs to the channel &a%{_livechannel}%&r\" to console")
            .since("1.0.0");

        //! On Follow Event
        Skript.registerEvent("Follow Event", SimpleEvent.class, BridgeEventFollow.class,
                "twitch follow [event]")
            .description("Triggered when a user follow in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch follow event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 followed the channel &a%{_livechannel}%&r\" to console")
            .since("1.0.0");

        //! On Cheer Event
        Skript.registerEvent("Cheer Event", SimpleEvent.class, BridgeEventCheer.class,
                "twitch cheer [event]")
            .description("Triggered when a user cheer in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch cheer event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_bits} to event-cheerbits",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_suer}%&6 cheered &a%{_bits}%&6 bits to the channel &a%{_livechannel}%&r\" to console")
            .since("1.0.0");

        //! On Game Change Event
        Skript.registerEvent("Game Change Event", SimpleEvent.class, BridgeEventGameChange.class,
                "twitch game change [event]",
                "\tsend event-gametitle to console")
            .description("Triggered when a game changed in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch game change event:")
            .since("1.0.0");

        //! On Raid Event
        Skript.registerEvent("Raid Event", SimpleEvent.class, BridgeEventRaid.class,
                "twitch raid [event]")
            .description("Triggered when a user raid in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch raid event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_viewers} to event-raidviewers",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_user}%&6 raided the channel &a%{_livechannel}%&6 with &a%{_viewers}%&6 viewers&r\" to console")
            .since("1.0.0");

        //! On CustomReward Event
        Skript.registerEvent("Custom Reward Event", SimpleEvent.class, BridgeEventRewards.class,
                "twitch custom reward [event]")
            .description("Triggered when a user redeem a custom reward in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch custom reward event:",
                "\tset {_channel} to event-livechannel",
                "\tset {_user} to event-liveuser",
                "\tset {_reward} to event-rewardtitle",
                "\tsend \"&a[&5TWITCH&a]&6 &a%{_user}%&6 redeemed the reward &a%{_reward}%&6 in the channel &a%{_livechannel}%&r\" to console")
            .since("1.0.0");

        //! On Prediction Event
        Skript.registerEvent("Prediction Event", SimpleEvent.class, BridgeEventPrediction.class,
                "twitch prediction [event]")
            .description("Triggered when a user redeem a custom reward in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch prediction event:",
                "\tset {_toppredictor} to event-toppredictor",
                "\tsend \"%{_toppredictor}%\" to console")
            .since("1.0.0");

        //! On Clip Create Event
        Skript.registerEvent("Clip Create Event", SimpleEvent.class, BridgeEventPrediction.class,
                "twitch clip create [event]")
            .description("Triggered when a clip is created in the given stream.",
                "Please note that the Channels have to mention in the Login Code")
            .requiredPlugins("Skript 2.6.3+")
            .examples("on twitch clip create event:",
                "\tset {_clipTitle} to event-cliptitle",
                "\tset {_clipCreator} to event-liveuser",
                "\tset {_clipChannel} to event-livechannel",
                "\tset {_clipThumbnailUrl} to event-clipurl")
            .since("1.0.0");
    }

}
