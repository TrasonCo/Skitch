package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.helix.domain.BlockedTerm;
import com.github.twitch4j.helix.domain.BlockedTermList;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;

@Name("Blocked Terms")
@Description("Gets the broadcaster’s list of non-private, blocked words or phrases. These are the terms that " +
    "the broadcaster or moderator added manually, or that were denied by AutoMod.\n" +
    "The bot needs to be at least Moderator!!!")
@Examples("set {_blockedterms::*} to blocked twitch-terms")
@Since("1.4.1")
public class ExprGetBlockedTerms extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprGetBlockedTerms.class, String.class, ExpressionType.SIMPLE, "[event-]blocked [twitch-]terms");
    }
    @Override
    protected String @NotNull [] get(Event event) {
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        if (event instanceof BridgeEventChat) {
            String channelID = ((BridgeEventChat)event).getEvent().getChannel().getId();
            BlockedTermList blockedTerms = client.getHelix().getBlockedTerms(null, channelID, botId, null, null).execute();
            return blockedTerms.getBlockedTerms().stream().map(BlockedTerm::getText).toArray(String[]::new);
        }
        else if (event instanceof CommandEvent) {
            String channelID = ((CommandEvent)event).getEvent().getChannel().getId();
            BlockedTermList blockedTerms = client.getHelix().getBlockedTerms(null, channelID, botId, null, null).execute();
            return blockedTerms.getBlockedTerms().stream().map(BlockedTerm::getText).toArray(String[]::new);
        }
        else
            return new String[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "blocked twitch-terms";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(BridgeEventChat.class, CommandEvent.class)) {
            Skript.error("You cannot use event-blocked-terms outside an LiveMessage or Command event!");
            return false;
        }
        return true;
    }
}
