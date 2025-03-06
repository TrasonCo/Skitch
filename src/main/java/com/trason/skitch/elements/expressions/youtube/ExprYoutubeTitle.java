package com.trason.skitch.elements.expressions.youtube;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.trason.skitch.elements.effects.EffCheckYouTubeUpload;

public class ExprYoutubeTitle extends SimplePropertyExpression<EffCheckYouTubeUpload.VideoDetails, String> {

    static {
        register(
            ExprYoutubeTitle.class,
            String.class,
            "youtube title",
            "videodetails"
        );
    }

    @Override
    protected String getPropertyName() {
        return "youtube title";
    }

    @Override
    public String convert(EffCheckYouTubeUpload.VideoDetails videoDetails) {
        return videoDetails.title();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }


}
