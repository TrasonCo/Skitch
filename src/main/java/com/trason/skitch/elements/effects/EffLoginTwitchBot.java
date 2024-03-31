package com.trason.skitch.elements.effects;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.helix.domain.User;
import com.trason.skitch.Skitch;
import com.trason.skitch.TwitchEventHandler;
import com.trason.skitch.util.AsyncEffect;
import com.trason.skitch.util.ConsoleMessages.console;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@Name("Twitch Bot Login")
@Description({"This is to login your Twitch Bot Account"})
@Examples({"on load:",
    "\ttwitch login to client %string% with token %string% with secret %string% to overview %string%",})
@Since("1.0.0")
@RequiredPlugins("Skript 2.6.3+")
public class EffLoginTwitchBot extends AsyncEffect {

    static {
        registerAsyncEffect(EffLoginTwitchBot.class, "twitch login to client %string% with token %string% with secret %string% to overview %string%");
    }

    public static TwitchClient client;
    public static String clientOaToken;


    private Expression<String> exprClientId;
    private Expression<String> exprClientToken;
    private Expression<String> exprClientSecret;
    private Expression<String> exprClientChannel;


    @SuppressWarnings("unchecked")
    @Override
    public boolean initAsync(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        this.exprClientId = (Expression<String>) exprs[0];
        this.exprClientToken = (Expression<String>) exprs[1];
        this.exprClientSecret = (Expression<String>) exprs[2];
        this.exprClientChannel = (Expression<String>) exprs[3];
        return true;
    }

    @Override
    protected void executeAsync(@NotNull Event event) {
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
            .withEnablePubSub(true)
            .withChatAccount(credential)
            .withEnableHelix(true)
            .withDefaultAuthToken(credential)
            .build();

        for (String channel : clientChannels)
            client.getChat().joinChannel(String.valueOf(channel));

        for (String channel : clientChannels) {
            List<User> userName = client.getHelix().getUsers(null, null, Collections.singletonList(channel)).execute().getUsers();
            String userId = userName.get(0).getId();
            client.getPubSub().listenForChannelPointsRedemptionEvents(null, userId);
            client.getPubSub().listenForChannelPredictionsEvents(null, userId);
        }


        // Enable Event Listeners


        client.getClientHelper().enableClipEventListener(Arrays.asList(clientChannels));
        client.getClientHelper().enableFollowEventListener(Arrays.asList(clientChannels));
        client.getClientHelper().enableStreamEventListener(Arrays.asList(clientChannels));

        // Register Event Listener
        client.getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(new TwitchEventHandler(Skitch.getPlugin(Skitch.class)));
        console.SkitchLogin(console.Cc.PURPLE + "____________________________________________");
        console.SkitchLogin(console.Cc.PURPLE + "Twitch Bot Login Successful!");
        console.SkitchLogin(console.Cc.PURPLE + "____________________________________________" + console.Cc.GOLD);

        for (String names : clientChannels) {
            console.SkitchLogin("Joined: " + console.Cc.GOLD + names);
        }
        console.SkitchLogin(console.Cc.GOLD + "____________________________________________" + console.Cc.RESET);

    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        // NEVER return null here! It's used for debugging and more, so it's important to return a valid string representing the effect.
        return "twitch login to client " + exprClientId.toString(e, debug) + " with token " + exprClientToken.toString(e, debug) + " with secret " + exprClientSecret.toString(e, debug) + " to overview " + exprClientChannel.toString(e, debug);
    }

}
