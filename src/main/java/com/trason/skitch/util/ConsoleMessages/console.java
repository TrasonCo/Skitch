package com.trason.skitch.util.ConsoleMessages;

import com.trason.skitch.Skitch;
public class console {

    public static void info(String info) {
        Skitch.getInstance().getLogger().info(Cc.GREEN + "| " + info);
    }

    public static void warning(String warning) {
        Skitch.getInstance().getLogger().warning( Cc.WHITE + "| " + Cc.YELLOW + warning);
    }

    public static void error(String error) {
        Skitch.getInstance().getLogger().severe( Cc.WHITE + "| "+ Cc.RED + error);
    }

    public static void PredictionError(String error) {
        Skitch.getInstance().getLogger().severe( "PREDICTION" + Cc.WHITE + "| "+ Cc.RED + error);
    }

    public static void ShoutoutError(String error) {
        Skitch.getInstance().getLogger().severe( "Shoutout" + Cc.WHITE + "| "+ Cc.RED + error);
    }


    @SuppressWarnings("unused")
    public static class Cc {
        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";

        public static final String GOLD = "\u001B[38;5;214m";
    }
}
