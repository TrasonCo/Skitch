package com.skitch.trason.addon;

import com.github.twitch4j.ITwitchClient;
import org.bukkit.plugin.java.JavaPlugin;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

import java.io.IOException;

public final class Skitch extends JavaPlugin {

    public static Skitch instance;
        SkriptAddon addon;

        private ITwitchClient client;

        @Override
        public void onEnable() {
            instance = this;
            addon = Skript.registerAddon(this);
            try {
                //This will register all our syntax for us. Explained below
                addon.loadClasses("com.skitch.trason.addon.elements");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public Skitch getInstance() {
            return instance;
        }

    public SkriptAddon getAddonInstance() {
        return addon;
    }



    @Override
    public void onDisable() {
        if (client != null) {
            client.getEventManager().close();
            client.close();
            client = null;
        }
    }

    public ITwitchClient getTwitchClient() {
        return this.client;
    }

}
