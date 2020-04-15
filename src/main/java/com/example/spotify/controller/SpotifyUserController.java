package com.example.spotify.controller;

import com.example.spotify.mapperModel.*;
import com.example.spotify.service.SpotifyService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpotifyUserController {

    private SpotifyService spotifyService = new SpotifyService();

    private SpotifyTracksController spotifyTracksController = new SpotifyTracksController();

    private SpotifyArtistsController spotifyArtistsController = new SpotifyArtistsController();

    //localhost:8090/usersTopTracksRecommendation?playlistName=SaadApp

    @GetMapping("/usersTopTracksRecommendation")
    public void usersTopTracksRecommendation(OAuth2Authentication details, @RequestParam(value = "playlistName") String playlistName) {

        spotifyService.eraseSpotifyServiceLists();

        List<MapperUsersTopTracks> usersTopTracksList = spotifyTracksController.usersTopTracks(details);

        List<String> usersTopTracksListOfArtists = spotifyService.artistsListFromTopTracksList(details, usersTopTracksList);
        System.out.println("Liczba artystów z top Tracks: " + usersTopTracksListOfArtists.size());

        List<MapperTracks> artistsTracksList = spotifyService.drawRelatedArtistsAndTheirTracks(details, usersTopTracksListOfArtists);

        List<MapperRecommendations> recommendationsTracksList = spotifyService.takeRecommendationsTracksList(usersTopTracksListOfArtists, details);
        System.out.println("Liczba wybranych utworów z Recommendations: " + recommendationsTracksList.size());

        List<MapperRecommendations> tracksForPlaylist = spotifyService.tracksPreparedForPlaylist(recommendationsTracksList, artistsTracksList);

        spotifyService.saveTracksToPlaylist(details, playlistName, tracksForPlaylist);

        for (MapperRecommendations track : tracksForPlaylist
                ) {
            System.out.println("--");
            System.out.println(track.getArtistName());
            System.out.println(track.getAlbumName());
        }
    }

    //localhost:8090/usersTopArtistsRecommendation?playlistName=SaadApp

    @GetMapping("/usersTopArtistsRecommendation")
    public void usersTopArtistsRecommendation(OAuth2Authentication details, @RequestParam(value = "playlistName") String playlistName) {

        spotifyService.eraseSpotifyServiceLists();

        List<MapperUsersTopArtists> usersTopArtistsList = spotifyArtistsController.usersTopArtists(details);
        System.out.println("Liczba artystów z top Artists: " + usersTopArtistsList.size());

        List<MapperTracks> artistsTracksList = spotifyService.drawRelatedArtistsAndTheirTracks(details, spotifyService.artistsListFromTopArtistsList(usersTopArtistsList));

        List<MapperRecommendations> recommendationsTracksList = spotifyService.takeRecommendationsTracksList(spotifyService.artistsListFromTopArtistsList(usersTopArtistsList), details);
        System.out.println("Liczba wybranych utworów z Recommendations: " + recommendationsTracksList.size());

        List<MapperRecommendations> tracksForPlaylist = spotifyService.tracksPreparedForPlaylist(recommendationsTracksList, artistsTracksList);

        spotifyService.saveTracksToPlaylist(details, playlistName, tracksForPlaylist);

        for (MapperRecommendations track : tracksForPlaylist
                ) {
            System.out.println("----");
            System.out.println(track.getArtistName());
            System.out.println(track.getAlbumName());
        }
    }

    //localhost:8090/usersRecentlyPlayedTracksRecommendation?playlistName=SaadApp

    @GetMapping("/usersRecentlyPlayedTracksRecommendation")
    public void usersRecentlyPlayedTracksRecommendation(OAuth2Authentication details, @RequestParam(value = "playlistName") String playlistName) {

        spotifyService.eraseSpotifyServiceLists();

        List<MapperRecentlyPlayedTracks> recentlyPlayedTracks = spotifyTracksController.recentlyPlayedTracks(details);

        List<String> recentlyPlayedTracksListOfArtists = spotifyService.artistsListFromRecentlyPlayedTracks(recentlyPlayedTracks);
        System.out.println("Liczba artystów z Ostatnio słuchanych utworów: " + recentlyPlayedTracksListOfArtists.size());

        List<MapperTracks> artistsTracksList = spotifyService.drawRelatedArtistsAndTheirTracks(details, recentlyPlayedTracksListOfArtists);

        List<MapperRecommendations> recommendationsTracksList = spotifyService.takeRecommendationsTracksList(recentlyPlayedTracksListOfArtists, details);
        System.out.println("Liczba wybranych utworów z Recommendations: " + recommendationsTracksList.size());

        List<MapperRecommendations> tracksForPlaylist = spotifyService.tracksPreparedForPlaylist(recommendationsTracksList, artistsTracksList);

        spotifyService.saveTracksToPlaylist(details, playlistName, tracksForPlaylist);

        for (MapperRecommendations track : tracksForPlaylist
                ) {
            System.out.println("----");
            System.out.println(track.getArtistName());
            System.out.println(track.getAlbumName());
        }
    }

}

