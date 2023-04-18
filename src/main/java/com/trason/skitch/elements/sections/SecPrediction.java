package com.trason.skitch.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.github.twitch4j.helix.domain.Prediction;
import org.bukkit.event.Event;

import java.util.List;

@Name("Prediction")
@Description("Create a new prediction")
@Examples("make new prediction:\n" +
    "\tset prediction target of last prediction to event-livechannelid\n" +
    "\tset prediction title of last prediction to \"test\"\n" +
    "\tadd \"test1\" to prediction outcome of last prediction\n" +
    "\tadd \"test2\" to prediction outcome of last prediction\n" +
    "\tset prediction time of last prediction to 10\n" +
    "send last prediction to livechat event-livechannel")

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
