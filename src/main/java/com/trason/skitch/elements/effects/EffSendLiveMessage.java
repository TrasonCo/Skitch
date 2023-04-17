package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Prediction;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.trason.skitch.util.ConsoleMessages.console;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import static com.trason.skitch.elements.effects.EffLoginTwitchBot.client;

@Name("Twitch Bot Send Live Message")
@Description({"This is to send a message to your Twitch Live Chat"})
@Examples({"on command /test:",
        "\tsend \"Hello World!\" to the livechat \"trason\""})

public class EffSendLiveMessage extends AsyncEffect {

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

        try {
            if (message instanceof Prediction.PredictionBuilder) {
                client.getHelix()
                    .createPrediction(null, ((Prediction.PredictionBuilder) message).build()).execute();
            }
        } catch (HystrixRuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ContextedRuntimeException) {
                ContextedRuntimeException cre = (ContextedRuntimeException) cause;
                Object error = cre.getFirstContextValue("errorMessage");
                if (error == null) {
                    console.PredictionError("Channel:" + " [" + liveChannel + "] " + "You are not authorized to create a prediction in this channel.");
                }
                else {
                    console.PredictionError("Channel:" + " [" + liveChannel + "] " + error.toString());
                }


            }


        }



    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        return "send " + exprMessage.toString(e, debug) + " to livechat " + exprLiveChannel.toString(e, debug);
    }

}
