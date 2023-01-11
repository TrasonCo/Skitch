package com.trason.skitch;

import ch.njol.skript.lang.Expression;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.trason.skitch.elements.events.custom.CommandEvent;
import com.trason.skitch.util.commands.SkitchCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class SkitchCommandManager {

    private static final List<SkitchCommand> COMMANDS = new ArrayList<>();

    public static void registerCommand(@NotNull SkitchCommand command) {
        COMMANDS.add(command);
    }

    public static boolean isRegistered(@NotNull String name) {
        return COMMANDS.stream().anyMatch(command -> command.getCommand().equalsIgnoreCase(name));
    }

    public static void callEvent(final ChannelMessageEvent event) {
        final String content = event.getMessage();

        for (SkitchCommand command : COMMANDS) {
            for (Expression<String> prefix : command.getPrefixes()) {
                if (prefix == null)
                    return;

                for (String alias : command.getAliases()) {
                    final CommandEvent commandEvent = new CommandEvent(command, event);

                    String usedCommand = null;
                    final String rawPrefix = prefix.getSingle(commandEvent);

                    if (rawPrefix.endsWith(" ")) {
                        // TODO I'm now questioning the need for the regex replacing, check this out
                        String[] spacedCommand = content.split(" ");
                        String suspectedPrefix = spacedCommand[0];
                        if ((suspectedPrefix + " ").equalsIgnoreCase(rawPrefix))
                            usedCommand = rawPrefix + (spacedCommand.length == 1 ? "" : spacedCommand[1]);

                    } else {
                        usedCommand = content.split(" ")[0];
                    }

                    try {
                        String usedPrefixes = usedCommand.split("")[0].toLowerCase(Locale.ROOT);
                    } catch (Exception ignored) {}
                    String rawCommand = "";

                    // TODO: 29/05/2021 Fix the global prefix system. Hard to interfere with original one :'(
                    if (usedCommand != null) {
                        if (usedCommand.equalsIgnoreCase(rawPrefix + alias)) {
                            commandEvent.setUsedPrefix(rawPrefix);
                            try {
                                commandEvent.setArguments(content.substring((usedCommand).length() + 1));
                            } catch (StringIndexOutOfBoundsException e1) {
                                commandEvent.setArguments(null);
                            }
                            // Because most of bukkit's apis are sync only, make sure to run this on bukkit's thread
                            Bukkit.getScheduler().runTask(Skitch.getProvidingPlugin(Skitch.class), () -> {
                                command.execute(commandEvent);
                            });

                            return;

                        }
                    }

                }

            }
        }
    }

}
