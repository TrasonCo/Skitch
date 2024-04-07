package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
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

@Name("Set Subscribers Only Mode")
@Description("Set the subscribers only mode of the chat to the specified value.\n" +
        "Subscribers only mode is a feature on Twitch that allows you to limit how often each user can send messages in a chat. When subscribers only mode is enabled, only subscribers of the channel can send messages.\n"+
        "This effect requires the broadcaster/moderator permission to set subscribers only mode.")
@Examples("set subscribers only mode to true\n" +
        "set subscribers only mode to false")
@Since("1.4.1")
public class EffSetSubscribersOnlyMode extends Effect {

    static {
        // This is the registration of the effect
        Skript.registerEffect(EffSetSubscribersOnlyMode.class, "set [twitch] subscribers only mode to %boolean%");
    }

    private Expression<Boolean> exprSubscribersOnlyMode;

    @Override
    protected void execute(@NotNull Event event) {
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        Boolean followerOnlyModeStatus = exprSubscribersOnlyMode.getSingle(event);
        try {
            if (event instanceof BridgeEventChat) {
                String broadcasterID = ((BridgeEventChat) event).getEvent().getChannel().getId();
                ChatSettings chatSettings = ChatSettings.builder().isSubscribersOnlyMode(followerOnlyModeStatus).build();
                client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
            }
            else if (event instanceof CommandEvent) {
                String broadcasterID = ((CommandEvent) event).getEvent().getChannel().getId();
                ChatSettings chatSettings = ChatSettings.builder().isSubscribersOnlyMode(followerOnlyModeStatus).build();
                client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
            }
        }catch (HystrixRuntimeException e){
            console.error("Error:[SetSubscribersOnlyMode] You don't have the broadcaster/moderator permission to set follower only mode.");
        }

    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "set subscribers only mode to " + exprSubscribersOnlyMode.toString(event, b);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        this.exprSubscribersOnlyMode = (Expression<Boolean>) expressions[0];
        return true;
    }
}
