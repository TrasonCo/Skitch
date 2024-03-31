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
import com.github.twitch4j.helix.domain.User;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Twitch Remove Channel Moderator")
@Description("Removes a moderator from the broadcaster’s chat room.\n" +
    "The bot needs broadcaster permission!!! (So only selfbots work here!)")
@Examples("remove twitch channel moderator \"username\"")
@Since("1.4.1")
public class EffRemoveChannelModerator extends Effect {
    static {
        // This is the registration of the effect
        Skript.registerEffect(EffRemoveChannelModerator.class, "remove [twitch] channel moderator %string%");
    }

    private Expression<String> exprUserName;
    @Override
    protected void execute(@NotNull Event event) {
        try {
            if (event instanceof BridgeEventChat) {
                String broadcasterID = ((BridgeEventChat) event).getEvent().getChannel().getId();
                List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprUserName.getSingle(event))).execute().getUsers();
                String userId = userName.get(0).getId();
                client.getHelix().removeChannelModerator(null, broadcasterID,userId).execute();
            }
            else if (event instanceof CommandEvent) {
                String broadcasterID = ((CommandEvent) event).getEvent().getChannel().getId();
                List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprUserName.getSingle(event))).execute().getUsers();
                String userId = userName.get(0).getId();
                client.getHelix().removeChannelModerator(null, broadcasterID,userId).execute();
            }
        } catch (HystrixRuntimeException e) {
            console.error("Error:[RemoveModeratorEffect] This error is triggered by one of the following reasons:\n" +
                "1.) User is not set.\n" +
                "2.) User is not a moderator.\n" +
                "3.) You don't have the broadcaster permission to remove a moderator.");
        }


    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "remove [twitch] channel moderator " + exprUserName.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        this.exprUserName = (Expression<String>) expressions[0];
        return true;
    }
}
