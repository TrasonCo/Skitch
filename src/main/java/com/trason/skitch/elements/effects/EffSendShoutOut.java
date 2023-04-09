package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.helix.domain.User;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;

@Name("Twitch Send Shoutout")
@Description("Send a shoutout to a channel!.")
@Examples("on twitch message:\n" +
        "\tsend shoutout to \"channel\"")

public class EffSendShoutOut extends Effect {

    static {
        Skript.registerEffect(EffSendShoutOut.class, "send shoutout to %string%");
    }

    private Expression<String> exprLiveChannel;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.exprLiveChannel = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        if (event instanceof BridgeEventChat) {
            OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
            String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
            List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprLiveChannel.getSingle(event))).execute().getUsers();
            String toLiveChannel = userName.get(0).getId();
            String fromLiveChannel = ((BridgeEventChat) event).getEvent().getChannel().getId();
            if (toLiveChannel == null)
                return;
            client.getHelix().sendShoutout(null, fromLiveChannel, toLiveChannel, botId).execute();
        }
        else if (event instanceof CommandEvent) {
            OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
            String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
            List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprLiveChannel.getSingle(event))).execute().getUsers();
            String toLiveChannel = userName.get(0).getId();
            String fromLiveChannel = ((CommandEvent) event).getEvent().getChannel().getId();
            if (toLiveChannel == null)
                return;
            client.getHelix().sendShoutout(null, fromLiveChannel, toLiveChannel, botId).execute();

        }
        else {
            OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
            String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
            List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprLiveChannel.getSingle(event))).execute().getUsers();
            String toLiveChannel = userName.get(0).getId();
            String fromLiveChannel = ((BridgeEventChat) event).getEvent().getChannel().getId();
            if (toLiveChannel == null)
                return;
            client.getHelix().sendShoutout(null, fromLiveChannel, toLiveChannel, botId).execute();
        }


    }

    @Override
    public String toString(@NotNull Event e, boolean debug) {
        return "null";
    }


}
