package com.example.spotify.mapperModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class MapperRelatedArtist {

    private String idArtist;
    private String artistName;
    private Integer artistPopularity;
    private String spotify;
    private String albumImageUrl;

}
