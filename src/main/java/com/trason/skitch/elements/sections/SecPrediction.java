package com.trason.skitch.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Prediction;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.bukkit.event.Event;

import java.util.List;

public class SecPrediction extends Section {

    static {
        Skript.registerSection(SecPrediction.class, "make new prediction");
    }

    public static Prediction.PredictionBuilder builder;

    @Override
    public boolean init(Expression<?>[] exprs,
                        int matchedPattern,
                        Kleenean isDelayed,
                        SkriptParser.ParseResult parseResult,
                        SectionNode sectionNode,
                        List<TriggerItem> triggerItems) {
        loadCode(sectionNode);
        return true;
    }

    @Override
    protected  TriggerItem walk(Event e) {
        builder = Prediction.builder();
        return walk(e, true);
    }

    @Override
    public String toString( Event e, boolean debug) {
        return "make new prediction";
    }
}
