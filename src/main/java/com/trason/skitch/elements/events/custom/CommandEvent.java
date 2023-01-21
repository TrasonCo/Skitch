package com.trason.skitch.elements.events.custom;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.trason.skitch.util.commands.Argument;
import com.trason.skitch.util.commands.SkitchCommand;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandEvent extends Event {

    private final SkitchCommand command;
    private final ChannelMessageEvent event;

    private String usedPrefix;
    private String arguments;

    public static final HandlerList handlers = new HandlerList();

    public CommandEvent(SkitchCommand command, ChannelMessageEvent event) {
        this.command = command;
        this.event = event;
    }

    public SkitchCommand getCommand() {
        return command;
    }

    public ChannelMessageEvent getEvent() {
        return event;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getArguments() {
        return arguments;
    }

    public String getUsedPrefix() {
        return usedPrefix;
    }

    public void setUsedPrefix(String usedPrefix) {
        this.usedPrefix = usedPrefix;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
}
