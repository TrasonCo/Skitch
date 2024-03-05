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
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;


import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;

@Name("Twitch Delete Message")
@Description("Delete a message from a channel!(Message Id is needed!)")
@Examples("on twitch message:\n" +
        "\ttwitch delete \"messageID\" from channel \"channelID\"")
public class EffDeleteMessages extends AsyncEffect {

    static {
        Skript.registerEffect(EffDeleteMessages.class, "twitch delete %string% from channel %string%");
    }

    private Expression<String> exprLiveChannel;
    private Expression<String> exprMessageId;


    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.exprMessageId = (Expression<String>) exprs[0];
        this.exprLiveChannel = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        String liveChannel = exprLiveChannel.getSingle(event);
        String messageId = exprMessageId.getSingle(event);

        if (liveChannel == null || messageId == null)
            console.error("&c[Skitch] &4Error: &cLive Channel or Message ID is null");
        if (event instanceof BridgeEventChat) {
            OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
            String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
            client.getHelix().deleteChatMessages(null, liveChannel, botId, messageId).execute();
        }

    }

    @Override
    public @NotNull String toString(@NotNull Event e, boolean debug) {
        return "twitch delete message";
    }
}
