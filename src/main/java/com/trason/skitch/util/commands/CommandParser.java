package com.trason.skitch.util.commands;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.util.Utils;
import ch.njol.util.NonNullPair;
import ch.njol.util.StringUtils;
import com.trason.skitch.elements.scopes.SkitchScope;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommandParser {

    private static final Pattern commandPattern = Pattern.compile("(?i)^(on )?discord command (\\S+)(\\s+(.+))?$");
    private static final Pattern argumentPattern = Pattern.compile("<\\s*(?:(.+?)\\s*:\\s*)?(.+?)\\s*(?:=\\s*(" + SkriptParser.wildcard + "))?\\s*>");
    private static final Pattern escape = Pattern.compile("[" + Pattern.quote("(|)<>%\\") + "]");
    private static final String listPattern = "\\s*,\\s*|\\s+(and|or|, )\\s+";
    private static final Method PARSE_I;

    static {
        Method _PARSE_I = null;
        try {
            _PARSE_I = SkriptParser.class.getDeclaredMethod("parse_i", String.class, int.class, int.class);
            _PARSE_I.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Skript.error("Skript's 'parse_i' method could not be resolved.");
        }
        PARSE_I = _PARSE_I;
    }

    public static @Nullable SkitchCommand parse(@NotNull SectionNode node, @NotNull SkitchScope scope) {

        String command = node.getKey();
        if (command == null) {
            return null;
        }

        command = ScriptLoader.replaceOptions(command);
        Matcher matcher = commandPattern.matcher(command);
        if (!matcher.matches())
            return null;

        int level = 0;
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == '[') {
                level++;
            } else if (command.charAt(i) == ']') {
                if (level == 0) {
                    Skript.error("Invalid placement of [optional brackets]");
                    return null;
                }
                level--;
            }
        }
        if (level > 0) {
            Skript.error("Invalid amount of [optional brackets]");
            return null;
        }

        command = matcher.group(2);

        String arguments = matcher.group(4);
        if (arguments == null)
            arguments = "";

        final StringBuilder pattern = new StringBuilder();

        final List<Argument<?>> currentArguments = new ArrayList<>();
        final Matcher m = argumentPattern.matcher(arguments);
        int lastEnd = 0;
        int optionals = 0;

        for (int i = 0; m.find(); i++) {
            pattern.append(escape("" + arguments.substring(lastEnd, m.start())));
            optionals += StringUtils.count(arguments, '[', lastEnd, m.start());
            optionals -= StringUtils.count(arguments, ']', lastEnd, m.start());

            lastEnd = m.end();

            ClassInfo<?> c;
            c = Classes.getClassInfoFromUserInput("" + m.group(2));
            final NonNullPair<String, Boolean> p = Utils.getEnglishPlural("" + m.group(2));
            if (c == null) {
                Skript.error("Unknown type '" + m.group(2) + "'");
                return null;
            }
            final Parser<?> parser = c.getParser();
            if (parser == null || !parser.canParse(ParseContext.COMMAND)) {
                Skript.error("Can't use " + c + " as argument of a command");
                return null;
            }

            final Argument<?> arg = Argument.newInstance(
                m.group(1),
                c,
                m.group(3),
                i,
                !p.getSecond(),
                optionals > 0);
            if (arg == null)
                return null;
            currentArguments.add(arg);

            if (arg.isOptional() && optionals == 0) {
                pattern.append('[');
                optionals++;
            }
            pattern.append("%" + (arg.isOptional() ? "-" : "") + Utils.toEnglishPlural(c.getCodeName(), p.getSecond()) + "%");
        }

        pattern.append(escape("" + arguments.substring(lastEnd)));
        optionals += StringUtils.count(arguments, '[', lastEnd);
        optionals -= StringUtils.count(arguments, ']', lastEnd);
        for (int i = 0; i < optionals; i++)
            pattern.append(']');

        node.convertToEntries(0);
        if (!(node.get("trigger") instanceof SectionNode)) {
            return null;
        }

        final SectionNode trigger = (SectionNode) node.get("trigger");
        if (trigger == null)
            return scope.error("No trigger found for command '" + command + "'", node);

        final String rawAliases = scope.parseEntry(node, "aliases");
        final List<String> aliases = rawAliases == null ? new ArrayList<>() : Arrays.asList(rawAliases.split(listPattern));
        if (!aliases.contains(command))
            aliases.add(command);

        List<Expression<String>> prefixes = new ArrayList<>();
        String rawPrefixes = ScriptLoader.replaceOptions(node.get("prefixes", ""));
        if (rawPrefixes.isEmpty()) {
            if (command.length() == 1) {
                prefixes.add(new SimpleLiteral<>("", false));
            } else {
                prefixes.add(new SimpleLiteral<>(String.valueOf(command.charAt(0)), false));
                command = command.substring(1);
            }
        } else {
            for (String prefix : rawPrefixes.split(listPattern)) {
                if (prefix.startsWith("\"") && prefix.endsWith("\""))
                    prefix = prefix.substring(1, prefix.length() - 1);
                Expression<String> prefixExpr = VariableString.newInstance(prefix, StringMode.MESSAGE);
                try {
                    if (((VariableString) prefixExpr).isSimple())
                        prefixExpr = new SimpleLiteral<>(prefix, false);
                } catch (NullPointerException ignored) { }
                prefixes.add(prefixExpr);
            }
        }

        String roleString = ScriptLoader.replaceOptions(node.get("roles", ""));
        List<String> roles = roleString.isEmpty() ? null : Arrays.asList(roleString.split(listPattern));

        return new SkitchCommand(prefixes, aliases, command, currentArguments, ScriptLoader.loadItems(trigger), pattern.toString());
    }

    public static boolean parseArguments(String args, SkitchCommand command, Event event) {
        SkriptParser parser = new SkriptParser(args, SkriptParser.PARSE_LITERALS, ParseContext.COMMAND);
        SkriptParser.ParseResult res = null;
        try {
            res = (SkriptParser.ParseResult) PARSE_I.invoke(parser, command.getPattern(), 0, 0);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (res == null) {
            return false;
        }
        List<Argument<?>> as = command.getArguments();
        assert as.size() == res.exprs.length;
        for (int i = 0; i < res.exprs.length; i++) {
            if (res.exprs[i] == null) {
                as.get(i).setToDefault(event);
            } else {
                as.get(i).set(event, res.exprs[i].getArray(event));
            }
        }
        return true;
    }

    private static String escape(final String s) {
        return "" + escape.matcher(s).replaceAll("\\\\$0");
    }

}
