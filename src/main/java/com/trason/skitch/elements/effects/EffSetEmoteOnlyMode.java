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
import com.github.twitch4j.helix.domain.ChatSettings;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;
import static com.trason.skitch.elements.effects.EffLoginTwitchBot.clientOaToken;

@Name("Set Emote Only Mode")
@Description("Set the emote only mode of the chat to the specified value.\n" +
        "Emote only mode is a feature on Twitch that allows you to limit what user can send messages in a chat. When emote only mode is enabled, only emotes can be sent in the chat.\n"+
        "This effect requires the broadcaster/moderator permission to set emote only mode." +
        "\n" +
        "A Boolean value that determines whether chat messages must contain only emotes.")
@Examples("set emote only mode to true\n" +
        "set emote only mode to false")
@Since("1.4.1")
public class EffSetEmoteOnlyMode extends Effect {

    static {
        // This is the registration of the effect
        Skript.registerEffect(EffSetEmoteOnlyMode.class, "set [twitch] emote only mode to %boolean%");
    }

    private Expression<Boolean> exprEmoteOnlyMode;

    @Override
    protected void execute(@NotNull Event event) {
        // This is the code that will be executed when the effect is called
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        Boolean followerOnlyModeStatus = exprEmoteOnlyMode.getSingle(event);
        try {
            if (event instanceof BridgeEventChat) {
                String broadcasterID = ((BridgeEventChat) event).getEvent().getChannel().getId();
                ChatSettings chatSettings = ChatSettings.builder().isEmoteOnlyMode(followerOnlyModeStatus).build();
                client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
            }
            else if (event instanceof CommandEvent) {
                String broadcasterID = ((CommandEvent) event).getEvent().getChannel().getId();
                ChatSettings chatSettings = ChatSettings.builder().isEmoteOnlyMode(followerOnlyModeStatus).build();
                client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
            }
        }catch (HystrixRuntimeException e){
            console.error("Error:[SetEmoteOnlyMode] You don't have the broadcaster/moderator permission to set follower only mode.");
        }

    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "set emote only mode to " + exprEmoteOnlyMode.toString(event, b);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        this.exprEmoteOnlyMode = (Expression<Boolean>) expressions[0];
        return true;
    }
}
