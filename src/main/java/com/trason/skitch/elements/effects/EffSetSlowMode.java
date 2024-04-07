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

@Name("Set SlowMode")
@Description("Set the slowmode of the chat to the specified value.\n" +
        "Slowmode is a feature on Twitch that allows you to limit how often each user can send messages in a chat. When slow mode is enabled, users can only send one message every x seconds.\n"+
        "This effect requires the broadcaster/moderator permission to set slowmode.\n" +
        "If you don't specify a delay, the default delay is 5 seconds. The value has to be in a range of 3 to 120 seconds.")
@Examples("set slowmode to true with delay 10 seconds\n" +
        "set slowmode to true\n" +
        "set slowmode to false")
@Since("1.4.1")
public class EffSetSlowMode extends Effect{

    static {
        // This is the registration of the effect
        Skript.registerEffect(EffSetSlowMode.class, "set [twitch] slowmode to %boolean% [with delay %integer% seconds]");
    }

    private Expression<Boolean> exprSlowMode;
    private Expression<Integer> exprSlowDelay;
    @Override
    protected void execute(@NotNull Event event) {
        // This is the code that will be executed when the effect is called
        OAuth2Credential chatAccount = new OAuth2Credential("twitch", clientOaToken);
        String botId = new TwitchIdentityProvider(null, null, null).getAdditionalCredentialInformation(chatAccount).get().getUserId();
        Boolean slowModeStatus = exprSlowMode.getSingle(event);
        Integer exprDelay = exprSlowDelay.getSingle(event);
        if (exprDelay == 1) {
            try {
                if (event instanceof BridgeEventChat) {
                    String broadcasterID = ((BridgeEventChat) event).getEvent().getChannel().getId();
                    ChatSettings chatSettings = ChatSettings.builder().isSlowMode(slowModeStatus).slowModeWaitTime(5).build();
                    client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
                }
                else if (event instanceof CommandEvent) {
                    String broadcasterID = ((CommandEvent) event).getEvent().getChannel().getId();
                    ChatSettings chatSettings = ChatSettings.builder().isSlowMode(slowModeStatus).slowModeWaitTime(5).build();
                    client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
                }

            }catch (HystrixRuntimeException e) {
                console.error("Error:[SetSlowMode] You don't have the broadcaster/moderator permission to set slowmode.");
            }
        }
        else {
            try {
                if (event instanceof BridgeEventChat) {
                    String broadcasterID = ((BridgeEventChat) event).getEvent().getChannel().getId();
                    ChatSettings chatSettings = ChatSettings.builder().isSlowMode(slowModeStatus).slowModeWaitTime(exprDelay).build();
                    client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
                }
                else if (event instanceof CommandEvent) {
                    String broadcasterID = ((CommandEvent) event).getEvent().getChannel().getId();
                    ChatSettings chatSettings = ChatSettings.builder().isSlowMode(slowModeStatus).slowModeWaitTime(exprDelay).build();
                    client.getHelix().updateChatSettings(null, broadcasterID, botId, chatSettings).execute();
                }
            }catch (HystrixRuntimeException e) {
                console.error("Error:[SetSlowMode] You don't have the broadcaster/moderator permission to set slowmode.");
            }

        }

    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "set slowmode to " + exprSlowMode.getSingle(event);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        exprSlowMode = (Expression<Boolean>) expressions[0];
        exprSlowDelay = (Expression<Integer>) expressions[1];
        return true;
    }
}
