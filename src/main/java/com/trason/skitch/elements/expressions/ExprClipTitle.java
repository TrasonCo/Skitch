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
import org.jetbrains.annotations.NotNull;

@Name("Clip Title")
@Description("Returns the title of the clip.")
@Examples("set {_title} to cliptitle")
public class ExprClipTitle extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprClipTitle.class, String.class, ExpressionType.SIMPLE,
            "[event-]cliptitle");
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    protected String @NotNull [] get(@NotNull Event event) {
        if (event instanceof BridgeEventClip) {
            String title = ((BridgeEventClip)event).getEvent().getClip().getTitle();
            return new String[]{title};
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
    public @NotNull String toString(Event event, boolean debug) {
        return "cliptitle";
    }
}
