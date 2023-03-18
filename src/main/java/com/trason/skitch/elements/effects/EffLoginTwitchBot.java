package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.trason.skitch.Skitch;
import com.trason.skitch.TwitchEventHandler;
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

    public static TwitchClient client;
    public static String clientOaToken;


    private Expression<String> exprClientId;
    private Expression<String> exprClientToken;
    private Expression<String> exprClientSecret;
    private Expression<String> exprClientChannel;


    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.exprClientId = (Expression<String>) exprs[0];
        this.exprClientToken = (Expression<String>) exprs[1];
        this.exprClientSecret = (Expression<String>) exprs[2];
        this.exprClientChannel = (Expression<String>) exprs[3];
        return true;
    }

    @Override
    protected void execute(Event event) {
        String clientId = exprClientId.getSingle(event);
        String clientSecret = exprClientSecret.getSingle(event);
        String clientToken = exprClientToken.getSingle(event);

        String clientChannel = exprClientChannel.getSingle(event);
        if (clientId == null || clientSecret == null || clientToken == null || clientChannel == null)
            return;
        // We split this after as we could get a NullPointerException from a null 'clientChannel'#split call.
        String[] clientChannels = clientChannel.split(", ");
        // We need to check if the client is set or not, else it'll throw an error
        // I've moved this down, so we can check the expressions first, so it doesn't get shut unless it has to be c:
        if (client != null) {
            client.getEventManager().close();
            client.close();
        }




        OAuth2Credential credential = StringUtils.isNotBlank(clientToken) ?
            new OAuth2Credential("twitch", clientToken) :
            null;
        clientOaToken = clientToken;
        EffLoginTwitchBot.client = TwitchClientBuilder.builder()
            .withClientId(clientId)
            .withClientSecret(clientSecret)
            .withEnableChat(true)
            .withEnableTMI(true)
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
