package com.trason.skitch.elements.expressions.predictions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.github.twitch4j.eventsub.domain.PredictionOutcome;
import com.github.twitch4j.helix.domain.Prediction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprPredictionOutcome extends SimplePropertyExpression<Prediction.PredictionBuilder, String> {

    static {
        register(
            ExprPredictionOutcome.class,
            String.class,
            "prediction outcome", // change here to change the expression itself (aka 'set prediction _title_ to ...')
            "prediction"
        );
    }

    @Override
    protected String getPropertyName() { // This is for Debuggingug
        return "prediction outcome";
    }

    @Override
    public @Nullable String convert(Prediction.PredictionBuilder predictionBuilder) {
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD)
            return new Class[] {String.class};
        return new Class[0];
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0 || delta.length > 25 || !(delta[0] instanceof String)) {
            Skript.warning("The input for the Outcome is wrong!");
            return;
        }
        final PredictionOutcome outcome = PredictionOutcome.builder().title((String) delta[0]).build();
        final Prediction.PredictionBuilder builder = getExpr().getSingle(e);

        if (builder == null) {
            Skript.warning("The prediction builder is not set.");
            return;
        }

        // change the method that is called, to actually change the real value inside the builder
        builder.outcome(outcome);
    }
}
