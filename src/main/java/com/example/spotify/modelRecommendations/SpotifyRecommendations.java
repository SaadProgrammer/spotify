
package com.example.spotify.modelRecommendations;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tracks",
    "seeds"
})
public class SpotifyRecommendations {

    @JsonProperty("tracks")
    private List<Track> tracks = null;
    @JsonProperty("seeds")
    private List<Seed> seeds = null;
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

    @JsonProperty("seeds")
    public List<Seed> getSeeds() {
        return seeds;
    }

    @JsonProperty("seeds")
    public void setSeeds(List<Seed> seeds) {
        this.seeds = seeds;
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
