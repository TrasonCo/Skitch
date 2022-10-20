package com.skitch.trason.addon.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.skitch.trason.addon.Skitch;
import com.skitch.trason.addon.TwitchEventHandler;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;
import java.util.Arrays;


@Name("Twitch Bot Login")

@Description({"This is to login your Twitch Bot Account"})

@Examples({"on load:",
    "\ttwitch login to client %string% with token %string% with secret %string% to overview %string%",})

@Since("1.0.0")

@RequiredPlugins("Skript 2.6.3+")
public class EffLoginTwitchBot extends Effect {

    static {
        Skript.registerEffect(EffLoginTwitchBot.class, "twitch login to client %string% with token %string% with secret %string% to overview %string%");
    }



    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.exprClientId = (Expression<String>) exprs[0];
        this.exprClientToken = (Expression<String>) exprs[1];
        this.exprClientSecret = (Expression<String>) exprs[2];
        this.exprClientChannel = (Expression<String>) exprs[3];
        return true;
    }

    public static TwitchClient client;
    private Expression<String> exprClientId;
    private Expression<String> exprClientToken;
    private Expression<String> exprClientSecret;
    private Expression<String> exprClientChannel;
    @Override

    protected void execute(Event event) {

        // We need to check if the client is set or not, else it'll throw an error
        if (client != null) {
            client.getEventManager().close();
            client.close();
        }

        String ClientId = exprClientId.getSingle(event);
        String ClientSecret = exprClientSecret.getSingle(event);
        String ClientToken = exprClientToken.getSingle(event);
        String ClientChannel = exprClientChannel.getSingle(event);
        String[] clientChannels = ClientChannel.split(", ");
        if (ClientId == null || ClientSecret == null || ClientToken == null)
            return;




        OAuth2Credential credential = StringUtils.isNotBlank(ClientToken) ? new OAuth2Credential("twitch", ClientToken) : null;

        this.client = TwitchClientBuilder.builder()
            .withClientId(ClientId)
            .withClientSecret(ClientSecret)
            .withEnableChat(true)
            .withChatAccount(credential)
            .withEnableHelix(true)
            .withDefaultAuthToken(credential)
            .build();

        for (String channel : clientChannels)
            client.getChat().joinChannel(String.valueOf(channel));

        client.getClientHelper().enableFollowEventListener(Arrays.asList(clientChannels));
        client.getClientHelper().enableStreamEventListener(Arrays.asList(clientChannels));

        client.getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new TwitchEventHandler(Skitch.getPlugin(Skitch.class)));

    }

    @Override
    public String toString(Event e, boolean debug) {
        // NEVER return null here! It's used for debugging and more, so it's important to return a valid string representing the effect.
        return "twitch login to client " + exprClientId.toString(e, debug) + " with token " + exprClientToken.toString(e, debug) + " with secret " + exprClientSecret.toString(e, debug) + " to overview " + exprClientChannel.toString(e, debug);
    }


}
