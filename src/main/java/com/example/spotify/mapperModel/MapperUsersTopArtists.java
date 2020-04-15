package com.example.spotify.mapperModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class MapperUsersTopArtists {

    private String idArtist;
    private String artistName;
    private Integer artistPopularity;
    private String spotify;
}
