package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.ChatUserColor;
import com.github.twitch4j.helix.domain.UserChatColorList;
import com.trason.skitch.elements.events.bukkit.BridgeEventChat;
import com.trason.skitch.elements.events.custom.CommandEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Chat Color")
@Description("Returns the chat color of a user"
    + "This expression returns the chat color of a user in HEX format only if the user has set a chat color by himself."
    + "If the user has not set a chat color, this expression will return nothing.")
@Examples("set {_chatcolor} to chat color")
public class ExprChatColor extends SimpleExpression<String> {



    static {
        Skript.registerExpression(ExprChatColor.class, String.class, ExpressionType.SIMPLE,
            "[twitch] chat color");
    }


    @Override
    protected  String @NotNull [] get(@NotNull Event event) {
        if (event instanceof BridgeEventChat) {
            String userid = ((BridgeEventChat) event).getEvent().getUser().getId();
            UserChatColorList list = client.getHelix().getUserChatColor(null, Collections.singletonList(userid)).execute();
            String chatColor = list.getData().get(0).getColor();
            return new String[]{chatColor};
        }
        else if (event instanceof CommandEvent) {
            String userid = ((CommandEvent) event).getEvent().getUser().getId();
            UserChatColorList list = client.getHelix().getUserChatColor(null, Collections.singletonList(userid)).execute();
            String chatColor = list.getData().get(0).getColor();
            return new String[]{chatColor};
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
    public @NotNull String toString(Event e, boolean debug) {
        return "chat color";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(BridgeEventChat.class, CommandEvent.class)) {
            Skript.error("You cannot use chat color outside an LiveMessage or Command event!");
            return false;
        }
        return true;
    }
}
