package com.skitch.trason.addon.elements.stucture;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.skitch.trason.addon.Skitch;
import com.skitch.trason.addon.TwitchEventHandler;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.lang.entry.EntryContainer;
import org.skriptlang.skript.lang.entry.EntryValidator;
import org.skriptlang.skript.lang.structure.Structure;

import java.util.Arrays;

public class StructTwitch extends Structure {



    static {
        Skript.registerStructure(StructTwitch.class, EntryValidator.builder()
                .addEntry("client id", null, false)
                .addEntry("client secret", null, false)
                .addEntry("oauth token", null, false)
                .addEntry("channels", null, false)
                .build(),
            "define [new] twitch [client]");
    }




    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult, EntryContainer entryContainer) {

        return true;
    }

    public static TwitchClient client;



    @Override
    public boolean load() {

        client.getEventManager().close();
        client.close();




        EntryContainer container = getEntryContainer();

        String clientId = container.get("client id", String.class, false);
        String clientSecret = container.get("client secret", String.class, false);
        String clientToken = container.get("oauth token", String.class, false);

        String[] clientChannels = container.get("channels", String.class, false).split(", ");

        OAuth2Credential credential = StringUtils.isNotBlank(clientToken) ? new OAuth2Credential("twitch", clientToken) : null;

        this.client = TwitchClientBuilder.builder()
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withEnableChat(true)
                .withChatAccount(credential)
                .withEnableHelix(true)
                .withDefaultAuthToken(credential)
                .build();

        for (String channel : clientChannels)
            client.getChat().joinChannel(channel);

        client.getClientHelper().enableFollowEventListener(Arrays.asList(clientChannels));
        client.getClientHelper().enableStreamEventListener(Arrays.asList(clientChannels));

        client.getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new TwitchEventHandler(Skitch.getPlugin(Skitch.class)));

        return true;
    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        return "define twitch";
    }
}
