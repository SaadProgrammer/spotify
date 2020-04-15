
package com.example.spotify.modelRecentlyPlayedTracks;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "track",
    "played_at",
    "context"
})
public class Item {

    @JsonProperty("track")
    private Track track;
    @JsonProperty("played_at")
    private String playedAt;
    @JsonProperty("context")
    private Context context;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("track")
    public Track getTrack() {
        return track;
    }

    @JsonProperty("track")
    public void setTrack(Track track) {
        this.track = track;
    }

    @JsonProperty("played_at")
    public String getPlayedAt() {
        return playedAt;
    }

    @JsonProperty("played_at")
    public void setPlayedAt(String playedAt) {
        this.playedAt = playedAt;
    }

    @JsonProperty("context")
    public Context getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(Context context) {
        this.context = context;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
