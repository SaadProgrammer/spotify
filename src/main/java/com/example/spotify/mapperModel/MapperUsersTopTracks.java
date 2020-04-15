package com.example.spotify.mapperModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class MapperUsersTopTracks {

    private String idTrack;
    private String trackName;
    private Integer trackPopuarity;
    private String spotify;
    private String uri;

}
