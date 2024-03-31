package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.helix.domain.User;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import jdk.jfr.Description;
import jdk.jfr.Name;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;

@Name("Twitch UnBan")
@Description("This is to unban a user in your Twitch Live Chat\n" +
    "The bot needs to be at least Moderator!!!")
@Examples("twitch unban \"UserName\"")
@Since("1.4.1")
public class EffTwitchUnBan extends Effect {

    static {
        // This is the registration of the effect
        Skript.registerEffect(EffTwitchUnBan.class, "twitch unban %string%");
    }

    private Expression<String> exprUserName;

    @Override
    protected void execute(@NotNull Event event) {
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        try {
            if (event instanceof BridgeEventChat) {
                String broadcasterID = ((BridgeEventChat) event).getEvent().getChannel().getId();
                List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprUserName.getSingle(event))).execute().getUsers();
                String userId = userName.get(0).getId();
                client.getHelix().unbanUser(null, broadcasterID, botId, userId).execute();
            }
            else if (event instanceof CommandEvent) {
                String broadcasterID = ((CommandEvent) event).getEvent().getChannel().getId();
                List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprUserName.getSingle(event))).execute().getUsers();
                String userId = userName.get(0).getId();
                client.getHelix().unbanUser(null, broadcasterID, botId, userId).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "unban [twitch-]user " + exprUserName.toString(event, b);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        this.exprUserName = (Expression<String>) expressions[0];
        return true;
    }
}
