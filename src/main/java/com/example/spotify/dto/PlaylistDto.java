package com.example.spotify.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public class PlaylistDto {

    private String name;
    private String description;
}

/*{
        "name": "Recommendations",
        "description": "Recommendations made by SaadApp",
        "public": true
        }*/
