package com.trason.skitch.util;


import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import java.util.stream.Stream;

public abstract class AsyncEffect extends Effect implements Runnable{

    private Event event;
    private Object variables;

    protected abstract void executeAsync(Event e);
    protected abstract boolean initAsync(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult);

    protected static <E extends Effect> void registerAsyncEffect(final Class<E> effect, final String... patterns) {
        Skript.registerEffect(
            effect, patterns
        );
    }

    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return initAsync(expr, matchedPattern, isDelayed, parseResult);
    }

    @Override
    protected void execute(Event e) {
        this.event = e;
        variables = Variables.removeLocals(this.event);
        Thread effect = new Thread(this);
        effect.setName(this.toString());
        effect.start();


    }

    @Override
    public void run() {
        if (variables != null) {
            Variables.setLocalVariables(this.event, variables);
        }
        executeAsync(this.event);
    }

}
