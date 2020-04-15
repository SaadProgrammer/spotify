
package com.example.spotify.modelArtistTopTrack;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tracks"
})
public class SpotifyArtistTopTrack {

    @JsonProperty("tracks")
    private List<Track> tracks = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("tracks")
    public List<Track> getTracks() {
        return tracks;
    }

    @JsonProperty("tracks")
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
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
