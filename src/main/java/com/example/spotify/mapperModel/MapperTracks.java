package com.example.spotify.mapperModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class MapperTracks {

    private String idArtist;
    private String artistName;
    private String idTrack;
    private String trackName;
    private Integer trackPopularity;
    private String listen;
    private String albumName;
    private String albumImageUrl;
    private String uri;

}
