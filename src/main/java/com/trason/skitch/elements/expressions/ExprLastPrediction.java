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
import com.github.twitch4j.helix.domain.Prediction;
import com.trason.skitch.elements.sections.SecPrediction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Last Prediction")
@Description("Returns the last prediction.")
@Examples("set {_prediction} to last prediction")
public class ExprLastPrediction extends SimpleExpression<Prediction.PredictionBuilder> {

    static {
        Skript.registerExpression(
            ExprLastPrediction.class,
            Prediction.PredictionBuilder.class,
            ExpressionType.SIMPLE,
            "[last] [used] prediction"
        );
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected @Nullable Prediction.PredictionBuilder[] get(Event e) {
        return new Prediction.PredictionBuilder[] {SecPrediction.builder};
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the last prediction";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Prediction.PredictionBuilder> getReturnType() {
        return Prediction.PredictionBuilder.class;
    }
}
