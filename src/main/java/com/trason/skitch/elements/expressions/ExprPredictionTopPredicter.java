package com.trason.skitch.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.twitch4j.pubsub.domain.PredictionOutcome;
import com.trason.skitch.elements.events.bukkit.BridgeEventPrediction;
import org.bukkit.event.Event;


public class ExprPredictionTopPredicter extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprPredictionTopPredicter.class, String.class, ExpressionType.SIMPLE,
            "[event-]toppredictor");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventPrediction) {
            String winningOutcome = ((BridgeEventPrediction) event)
                .getEvent()
                .getEvent()
                .getWinningOutcomeId();

            String[] topPredictor = ((BridgeEventPrediction) event)
                .getEvent()
                .getEvent()
                .getOutcomes().stream().filter(outcome -> outcome.getId().equals(winningOutcome))
                .map(PredictionOutcome::getTopPredictors).toArray(String[]::new);
            return topPredictor;
        }
        else {
            Skript.warning("This expression can only be used in a prediction event.");
            return null;
        }

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
