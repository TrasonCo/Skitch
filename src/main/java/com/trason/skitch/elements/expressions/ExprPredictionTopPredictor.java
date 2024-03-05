package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.pubsub.domain.Prediction;
import com.github.twitch4j.pubsub.domain.PredictionOutcome;
import com.trason.skitch.elements.events.bukkit.BridgeEventPrediction;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

@Name("Top Predictor")
@Description("Returns the top predictor of the event.")
@Examples("on twitch prediction resolve:\n" +
        "\tbroadcast \"%event-toppredictor%\"")
public class ExprPredictionTopPredictor extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprPredictionTopPredictor.class, String.class, ExpressionType.SIMPLE,
            "[event-]toppredictor");
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventPrediction) {
            BridgeEventPrediction e = (BridgeEventPrediction) event;
            String winningId = e.getWinningOutcomeId();
            if ("RESOLVED".equalsIgnoreCase(e.getStatus()) && StringUtils.isNotEmpty(winningId)) {
                String[] topWinners = e.getEvent()
                    .getEvent()
                    .getOutcomes()
                    .stream()
                    .filter(o -> winningId.equals(o.getId()))
                    .findAny()
                    .map(PredictionOutcome::getTopPredictors)
                    .filter(list -> !list.isEmpty())
                    .map(x -> x.stream()
                        .map(Prediction::getUserDisplayName)
                        .collect(Collectors.toList()))
                    .map(list -> list.toArray(String[]::new))
                    .orElse(new String[0]);
                return topWinners;
            }
        }
        else {
            Skript.warning("This expression can only be used in a prediction event.");
            return null;
        }

        return new String[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "toppredictor";
    }
}
