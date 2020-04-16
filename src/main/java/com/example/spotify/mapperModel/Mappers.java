package com.example.spotify.mapperModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Component

public class Mappers {

    public String mapperIdArtist(MapperTracks idArtist) {
        String idArtistString = idArtist.getIdArtist();
        return idArtistString;
    }

    public String mapperIdTrack(MapperUsersTopTracks usersTopTracksIdTrack) {
        String idTrackString = usersTopTracksIdTrack.getIdTrack();
        return idTrackString;
    }

    public String mapperArtist(MapperArtist artist) {
        String artistString = artist.getArtistName();
        return artistString;
    }

    public MapperRecommendations mapperTrackRecommendations(MapperTracks mapperTracks) {
        String idTrack = mapperTracks.getIdTrack();
        String trackName = mapperTracks.getTrackName();
        String idArtist = mapperTracks.getIdArtist();
        String artistName = mapperTracks.getArtistName();
        Integer trackPopularity = mapperTracks.getTrackPopularity();
        String listen = mapperTracks.getListen();
        //String albumName = mapperTracks.getAlbumName();
        //String albumImageUrl = mapperTracks.getAlbumImageUrl();
        String uri = mapperTracks.getUri();
        return new MapperRecommendations(idTrack, trackName, idArtist, artistName, trackPopularity, listen, /*albumName, albumImageUrl,*/ uri);
    }

}
