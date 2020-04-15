package com.example.spotify.mapperModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class MapperUsersPlaylists {

    private String idPlaylist;
    private String namePlaylist;
    private Integer numberOfTracks;
    private String spotify;

}
