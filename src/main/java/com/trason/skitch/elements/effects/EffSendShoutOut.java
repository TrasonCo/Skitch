package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.helix.domain.User;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.ConsoleMessages.console;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
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

public class EffSendShoutOut extends AsyncEffect {

    static {
        Skript.registerEffect(EffSendShoutOut.class, "send shoutout to livechat %string%");
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
        String LiveChannel = client.getHelix().getUsers(null, null, Collections.singletonList(exprLiveChannel.getSingle(event))).execute().getUsers().get(0).getDisplayName();
        try {
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
        } catch (HystrixRuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ContextedRuntimeException) {
                ContextedRuntimeException cre = (ContextedRuntimeException) cause;
                Object error = cre.getFirstContextValue("errorMessage");
                if (error == null) {
                    console.ShoutoutError("Channel:" + " [" + LiveChannel + "] " + "You are not authorized to create a ShoutoutError in this channel.");
                }
                else {
                    console.ShoutoutError("Channel:" + " [" + LiveChannel + "] " + error.toString());
                }


            }
        }




    }

    @Override
    public String toString(@NotNull Event e, boolean debug) {
        return "null";
    }


}
