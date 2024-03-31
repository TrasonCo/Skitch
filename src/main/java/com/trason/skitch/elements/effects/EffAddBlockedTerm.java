package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.events.bukkit.SkriptParseEvent;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;


@Name("Twitch Add Blocked Term")
@Description("Adds a word or phrase to the broadcaster’s list of blocked terms. These are the terms that broadcasters don’t want used in their chat room.\n" +
    "\n" +
    "The term must contain a minimum of 2 characters and may contain up to a maximum of 500 characters.\n" +
    "\n" +
    "Terms can use a wildcard character (*). The wildcard character must appear at the beginning or end of a word, or set of characters. For example, *foo or foo*.\n" +
    "The bot needs to be at least Moderator!!!")
@Examples("add blocked twitch term \"example\"")
@Since("1.4.1")
public class EffAddBlockedTerm extends Effect {
    static {
        // This is the registration of the effect
        Skript.registerEffect(EffAddBlockedTerm.class, "add blocked [twitch] term %string%");
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
                client.getHelix().addBlockedTerm(null,channelId,botId,term).execute();
            }
            else if (event instanceof CommandEvent) {
                String channelId = ((CommandEvent) event).getEvent().getChannel().getId();
                client.getHelix().addBlockedTerm(null,channelId,botId,term).execute();
            }
        } catch (HystrixRuntimeException e) {
            console.error("Error: Term is not set or too short, please provide a term to block with minimum 2 character.");
        }
    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "add blocked [twitch] term " + exprTerm.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        this.exprTerm = (Expression<String>) expressions[0];
        return true;
    }
}
