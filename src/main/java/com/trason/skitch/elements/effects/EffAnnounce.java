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
import com.github.twitch4j.helix.domain.AnnouncementColor;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;

@Name("Twitch Send Announce")
@Description("Send a announce to a channel!.\n" +
    "Colors: BLUE, GREEN, PURPLE, PRIMARY\n" +
    "If color is set to primary, the channel’s accent color is used to highlight the announcement")
@Examples("on twitch message:\n" +
        "\tmake twitch announce \"message\" with color \"blue\"")


public class EffAnnounce extends AsyncEffect {

    static {
        Skript.registerEffect(EffAnnounce.class, "make twitch announce %string% with color %string%");
    }

    private Expression<String> exprMessage;
    private Expression<String> exprColor;

    @Override
    protected void execute(Event event) {
        String message = exprMessage.getSingle(event);
        String color = exprColor.getSingle(event).toUpperCase();
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        if (message == null || color == null)
            return;
        if (event instanceof BridgeEventChat) {
            String bID = ((BridgeEventChat) event).getEvent().getChannel().getId();
            client.getHelix().sendChatAnnouncement(null, bID, botId, message, AnnouncementColor.valueOf(color)).execute();
        } else if (event instanceof CommandEvent) {
            String bID = ((CommandEvent) event).getEvent().getChannel().getId();
            client.getHelix().sendChatAnnouncement(null, bID, botId, message, AnnouncementColor.valueOf(color)).execute();

        }


    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "null";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.exprMessage = (Expression<String>) exprs[0];
        this.exprColor = (Expression<String>) exprs[1];
        return true;
    }
}

