package com.trason.skitch.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Name("Check YouTube Upload")
@Description("Checks if a specific YouTube channel has uploaded a new video.")
@Examples({"check youtube upload for channel \"Gronkh\" and store result in {_newvideo::*}"})
public class EffCheckYouTubeUpload extends AsyncEffect {

    static {
        Skript.registerEffect(EffCheckYouTubeUpload.class,
            "check youtube upload for channel %string% and store result in %object%");
    }

    private Expression<String> exprChannelName;
    private Expression<Object> exprVariable;
    private static String lastVideoId = "";

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.exprChannelName = (Expression<String>) exprs[0];
        this.exprVariable = (Expression<Object>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        String channelName = exprChannelName.getSingle(event);
        if (channelName == null) return;

        try {
            String channelId = getChannelId(channelName);
            if (channelId == null) return;

            String urlString = "https://www.youtube.com/feeds/videos.xml?channel_id=" + channelId;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new URL(urlString).openStream());

            NodeList entries = doc.getElementsByTagName("entry");
            if (entries.getLength() > 0) {
                Element latestVideo = (Element) entries.item(0);
                String title = latestVideo.getElementsByTagName("title").item(0).getTextContent();
                String description = latestVideo.getElementsByTagName("media:description").item(0).getTextContent();
                String videoId = latestVideo.getElementsByTagName("yt:videoId").item(0).getTextContent();
                String videoUrl = "https://www.youtube.com/watch?v=" + videoId;

                if (!videoId.equals(lastVideoId)) {
                    lastVideoId = videoId;
                    VideoDetails videoDetails = new VideoDetails(title, description, videoUrl);
                    Variables.setVariable(exprVariable.getSingle(event).toString(), videoDetails, event, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getChannelId(String channelName) {
        try {
            String urlString = "https://www.youtube.com/results?search_query=" + channelName.replace(" ", "+");
            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String html = response.toString();
            int start = html.indexOf("channelId\":\"") + 12;
            if (start == 11) return null; // Falls "channelId" nicht gefunden wird
            int end = html.indexOf("\"", start);
            return html.substring(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "check youtube upload for channel " + exprChannelName.toString(event, b) + " and store result in " + exprVariable.toString(event, b);
    }

    public record VideoDetails(String title, String description, String videoUrl) {}
}
