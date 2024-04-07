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

@Name("Set Follower Only Mode")
@Description("Set the follower only mode of the chat to the specified value.\n" +
        "Follower only mode is a feature on Twitch that allows you to limit how often each user can send messages in a chat. When follower only mode is enabled, only followers of the channel can send messages.\n"+
        "This effect requires the broadcaster/moderator permission to set follower only mode.")
@Examples("set follower only mode to true")
@Since("1.4.1")
public class EffSetFollowerOnlyMode extends Effect {

    static {
        // This is the registration of the effect
        Skript.registerEffect(EffSetFollowerOnlyMode.class, "set [twitch] follower only mode to %boolean%");
    }

    private Expression<Boolean> exprFollowerOnlyMode;
    @Override
    protected void execute(@NotNull Event event) {
        // This is the code that will be executed when the effect is called
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        Boolean followerOnlyModeStatus = exprFollowerOnlyMode.getSingle(event);
        try {
            if (event instanceof BridgeEventChat) {
                String broadcasterID = ((BridgeEventChat) event).getEvent().getChannel().getId();
                ChatSettings chatSettings = ChatSettings.builder().isFollowersOnlyMode(followerOnlyModeStatus).build();
                client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
            }
            else if (event instanceof CommandEvent) {
                String broadcasterID = ((CommandEvent) event).getEvent().getChannel().getId();
                ChatSettings chatSettings = ChatSettings.builder().isFollowersOnlyMode(followerOnlyModeStatus).build();
                client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
            }
        }catch (HystrixRuntimeException e){
            console.error("Error:[SetFollowerOnlyMode] You don't have the broadcaster/moderator permission to set follower only mode.");
        }
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "set follower only mode to " + exprFollowerOnlyMode.getSingle(event);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        this.exprFollowerOnlyMode = (Expression<Boolean>) expressions[0];
        return true;
    }
}
