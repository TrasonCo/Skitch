package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Prediction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Twitch Bot Send Live Message")
@Description({"This is to send a message to your Twitch Live Chat"})
@Examples({"on command /test:",
        "\tsend \"Hello World!\" to the livechat \"trason\""})
public class EffSendLiveMessage extends Effect {

    static {
        Skript.registerEffect(EffSendLiveMessage.class, "send %string/prediction% to [the] livechat %string%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        this.exprMessage = (Expression<Object>) exprs[0];
        this.exprLiveChannel = (Expression<String>) exprs[1];
        return true;
    }

    private Expression<Object> exprMessage;
    private Expression<String> exprLiveChannel;

    @Override
    protected void execute(@NotNull Event event) {
        Object message = exprMessage.getSingle(event);
        String liveChannel = exprLiveChannel.getSingle(event);

        if (message == null || liveChannel == null)
            return;

        if (message instanceof String)
            client.getChat().sendMessage(liveChannel, (String) message);
        else if (message instanceof Prediction.PredictionBuilder) {
            client.getHelix()
                .createPrediction(null,((Prediction.PredictionBuilder) message).build()).execute();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "send " + exprMessage.toString(e, debug) + " to livechat " + exprLiveChannel.toString(e, debug);
    }

}
