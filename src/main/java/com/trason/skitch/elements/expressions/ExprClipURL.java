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
import com.trason.skitch.elements.events.bukkit.BridgeEventClip;
import org.bukkit.event.Event;

@Name("Clip URL")
@Description("Returns the URL of the clip.")
@Examples("set {_url} to clipurl")
public class ExprClipURL extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprClipTitle.class, String.class, ExpressionType.SIMPLE,
            "[event-]clipurl");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (event instanceof BridgeEventClip) {
            String url = ((BridgeEventClip)event).getEvent().getClip().getUrl();
            return new String[]{url};
        }
        else
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
        return "clipurl";
    }
}
