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
import com.github.twitch4j.helix.domain.BanUserInput;
import com.github.twitch4j.helix.domain.User;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;

@Name("Twitch Ban")
@Description({"This is to ban a user in your Twitch Live Chat"})
@Examples({"on command /test:",
        "\ttwitch ban \"event-liveuser\" from channel \"event-livechannel\" with reason \"Testing\""})

public class EffTwitchBan extends AsyncEffect {


    static {
        Skript.registerEffect(EffTwitchBan.class, "twitch ban %string% from channel %string% with reason %string% ");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.exprUser = (Expression<String>) exprs[0];
        this.exprLiveChannel = (Expression<String>) exprs[1];
        this.exprReason = (Expression<String>) exprs[2];
        return true;
    }

    private Expression<String> exprUser;
    private Expression<String> exprLiveChannel;
    private Expression<String> exprReason;

    @Override
    protected void execute(@NotNull Event event) {
        String user = exprUser.getSingle(event);
        String userId = client.getHelix().getUsers(null, null, Collections.singletonList(user)).execute().getUsers().get(0).getId();
        String reason = exprReason.getSingle(event);
        String LiveChannelId = client.getHelix().getUsers(null, null, Collections.singletonList(exprLiveChannel.getSingle(event))).execute().getUsers().get(0).getId();
        if (user == null || reason == null)
            console.error("&c[Skitch] &4Error: &cUser or Reason is null");
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();

        BanUserInput banUserInput = new BanUserInput().withUserId(userId).withReason(reason);
        client.getHelix().banUser(null, LiveChannelId, botId, banUserInput).execute();
    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "twitch ban " + exprUser.getSingle(event) + " from channel " + exprLiveChannel.getSingle(event) + " with reason " + exprReason.getSingle(event);
    }





}
