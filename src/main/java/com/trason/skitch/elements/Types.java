package com.trason.skitch.elements;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.github.twitch4j.helix.domain.Prediction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public final class Types {

    static {

        Classes.registerClass(new ClassInfo<>(Prediction.PredictionBuilder.class, "prediction")
            /* .name("")
            .description()
            .since()
            .examples()
            .usage() */
            .parser(new Parser<Prediction.PredictionBuilder>() {
                @Override
                public String toString(Prediction.PredictionBuilder o, int flags) {
                    return toVariableNameString(o);
                }

                @Override
                public String toVariableNameString(Prediction.PredictionBuilder o) {
                    return o.toString();
                }

                @Override
                public boolean canParse(ParseContext context) {
                    return false;
                }

                @Override
                public @Nullable Prediction.PredictionBuilder parse(String s, ParseContext context) {
                    return null;
                }
            }));

    }

}
