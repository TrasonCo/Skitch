package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Moderator;
import com.github.twitch4j.helix.domain.ModeratorList;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.ConsoleMessages.console;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

public class ExprGetModerators extends SimpleExpression<String> {

    static {
        // This is the registration of the expression
        Skript.registerExpression(ExprGetModerators.class, String.class, ExpressionType.SIMPLE, "[event-][twitch-]moderators");
    }

    @Override
    protected String @NotNull [] get(Event event) {
        try {
            if (event instanceof BridgeEventChat) {
                String channelId = ((BridgeEventChat) event).getEvent().getChannel().getId();
                ModeratorList moderatorList = client.getHelix().getModerators(null, channelId, null, null, 100).execute();
                String[] moderators = moderatorList.getModerators().stream().map(Moderator::getUserName).toArray(String[]::new);
                return moderators;
            }
            else if (event instanceof CommandEvent) {
                String channelId = ((CommandEvent) event).getEvent().getChannel().getId();
                ModeratorList moderatorList = client.getHelix().getModerators(null, channelId, null, null, 100).execute();
                String[] moderators = moderatorList.getModerators().stream().map(Moderator::getUserName).toArray(String[]::new);
                return moderators;
            }
        } catch (HystrixRuntimeException e) {
            console.error("Error:[getChannelModerators] You don't have the broadcaster permission to get the moderator list.");

        }
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
        return "get moderators";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }
}
