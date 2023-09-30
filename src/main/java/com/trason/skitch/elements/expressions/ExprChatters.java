package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.helix.domain.Chatter;
import com.github.twitch4j.helix.domain.ChattersList;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;


import static com.trason.skitch.elements.effects.EffLoginTwitchBot.*;

public class ExprChatters extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprChatters.class, String.class, ExpressionType.SIMPLE, "[event-]chatter");
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventChat) {
            String channelID = ((BridgeEventChat)event).getEvent().getChannel().getId();
            return getChatters(channelID);
        }
        else if (event instanceof CommandEvent) {
            String channelID = ((CommandEvent)event).getEvent().getChannel().getId();
            return getChatters(channelID);
        }

        else
            return new String[0];
    }

    @NotNull
    private String[] getChatters(String channelID) {
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String BotId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        ChattersList chatter = client.getHelix().getChatters(null,channelID,BotId,null,null).execute();
        String[] chatters = chatter.getChatters().stream().map(Chatter::getUserName).toArray(String[]::new);
        return chatters;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "event-chatter";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(BridgeEventChat.class, CommandEvent.class)) {
            Skript.error("You cannot use event-chatter outside an LiveMessage or Command event!");
            return false;
        }
        return true;
    }
}
