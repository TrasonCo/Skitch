package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.helix.domain.BlockedTerm;
import com.github.twitch4j.helix.domain.BlockedTermList;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;


import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;


@Name("Twitch Remove Blocked Term")
@Description("Removes a word or phrase from the broadcaster’s list of blocked terms. These are the terms that broadcasters don’t want used in their chat room.\n" +
    "\n" +
    "The term must contain a minimum of 2 characters and may contain up to a maximum of 500 characters.\n" +
    "\n" +
    "Terms can use a wildcard character (*). The wildcard character must appear at the beginning or end of a word, or set of characters. For example, *foo or foo*.\n" +
    "The bot needs to be at least Moderator!!!")
@Examples("remove blocked twitch term \"example\"")
@Since("1.4.1")
public class EffRemoveBlockedTerm extends Effect {
    static {
        // This is the registration of the effect
        Skript.registerEffect(EffRemoveBlockedTerm.class, "remove blocked [twitch] term %string%");
    }

    private Expression<String> exprTerm;
    @Override
    protected void execute(@NotNull Event event) {
        String term = exprTerm.getSingle(event);
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        try {
            if (event instanceof BridgeEventChat) {
                String channelId = ((BridgeEventChat) event).getEvent().getChannel().getId();
                BlockedTermList blockedTerms = client.getHelix().getBlockedTerms(null,channelId,botId,null,100).execute();
                for (BlockedTerm blockedTerm : blockedTerms.getBlockedTerms()) {
                    if (term.equals(blockedTerm.getText())) {
                        client.getHelix().removeBlockedTerm(null,channelId,botId, blockedTerm.getId()).execute();
                    }
                }

            }
            else if (event instanceof CommandEvent) {
                String channelId = ((CommandEvent) event).getEvent().getChannel().getId();
                BlockedTermList blockedTerms = client.getHelix().getBlockedTerms(null,channelId,botId,null,100).execute();
                for (BlockedTerm blockedTerm : blockedTerms.getBlockedTerms()) {
                    if (term.equals(blockedTerm.getText())) {
                        client.getHelix().removeBlockedTerm(null,channelId,botId, blockedTerm.getId()).execute();
                    }
                }
            }
        } catch (HystrixRuntimeException e) {
            console.error("Error: Term to remove is not set.");
        }

    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "remove blocked [twitch] term " + exprTerm.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        this.exprTerm = (Expression<String>) expressions[0];
        return true;
    }
}
