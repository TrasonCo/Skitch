package com.trason.skitch;

import com.github.twitch4j.ITwitchClient;
import org.bukkit.plugin.java.JavaPlugin;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

import java.io.IOException;

public final class Skitch extends JavaPlugin {

    private static Skitch instance;

    private SkriptAddon addon;
    private ITwitchClient client;

    @Override
    public void onEnable() {

        // All you have to do is adding the following two lines in your onEnable method.
        int pluginId = 17842; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);



        instance = this;
        addon = Skript.registerAddon(this);
        try {
            // This will register all our syntax for us. Explained below
            addon.loadClasses("com.trason.skitch.elements");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (client != null) {
            client.getEventManager().close();
            client.close();
            client = null;
        }
    }

    public Skitch getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    public ITwitchClient getTwitchClient() {
        return this.client;
    }

}
