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
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Twitch Add Channel VIP")
@Description("Adds a VIP to the broadcaster’s chat room.\n" +
        "The bot needs broadcaster permission!!! (So only selfbots work here!)")
@Examples("add twitch channel vip \"username\"")
@Since("1.4.1")
public class EffAddChannelVip extends Effect {
    static {
        // This is the registration of the effect
        Skript.registerEffect(EffAddChannelVip.class, "add [twitch] channel vip %string%");
    }

    private Expression<String> exprUserName;
    @Override
    protected void execute(@NotNull Event event) {
        try {
            if (event instanceof BridgeEventChat) {
                String broadcasterID = ((BridgeEventChat) event).getEvent().getChannel().getId();
                List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprUserName.getSingle(event))).execute().getUsers();
                String userId = userName.get(0).getId();
                client.getHelix().addChannelVip(null, broadcasterID,userId).execute();
            }
            else if (event instanceof CommandEvent) {
                String broadcasterID = ((CommandEvent) event).getEvent().getChannel().getId();
                List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(exprUserName.getSingle(event))).execute().getUsers();
                String userId = userName.get(0).getId();
                client.getHelix().addChannelVip(null, broadcasterID,userId).execute();
            }
        } catch (HystrixRuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ContextedRuntimeException) {
                ContextedRuntimeException cre = (ContextedRuntimeException) cause;
                Object error = cre.getFirstContextValue("errorMessage");
                if (error == null) {
                    console.error("Error:[AddChannelVip] You don't have the broadcaster permission to add a vip.");
                }
                else {
                    console.error("Error:[AddChannelVip] " + error);
                }
            }

        }


    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "add [twitch] channel vip " + exprUserName.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        this.exprUserName = (Expression<String>) expressions[0];
        return true;
    }
}
