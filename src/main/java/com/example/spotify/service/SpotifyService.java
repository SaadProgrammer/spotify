package com.example.spotify.service;

import com.example.spotify.controller.SpotifyArtistsController;
import com.example.spotify.controller.SpotifyPlaylistsController;
import com.example.spotify.controller.SpotifyTracksController;
import com.example.spotify.mapperModel.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class SpotifyService {

    private SpotifyArtistsController spotifyArtistsController = new SpotifyArtistsController();

    private SpotifyTracksController spotifyTracksController = new SpotifyTracksController();

    private SpotifyPlaylistsController spotifyPlaylistsController = new SpotifyPlaylistsController();

    private Mappers mappers = new Mappers();

    //playlists

    public void saveTracksToPlaylist(OAuth2Authentication details, String playlistName, List<MapperRecommendations> tracksForPlaylist) {
        String idPlaylist = findPlaylistIdInUsersPlaylist(details, playlistName);
        for (MapperRecommendations track : tracksForPlaylist
                ) {
            spotifyPlaylistsController.saveTrackInPlaylist(details, idPlaylist, track.getUri());
        }
    }

    private String findPlaylistIdInUsersPlaylist(OAuth2Authentication details, String playlistName) {
        List<MapperUsersPlaylists> playlistsList = spotifyPlaylistsController.usersPlaylists(details);
        String idPlaylist = null;
        for (MapperUsersPlaylists element : playlistsList
                ) {
            if (element.getNamePlaylist().equals(playlistName)) {
                idPlaylist = element.getIdPlaylist();
            }
        }
        return idPlaylist;
    }

    //artists names - String

    public List<String> artistsListFromTopTracksList(OAuth2Authentication details, List<MapperUsersTopTracks> usersTopTracksList) {
        List<String> usersTopTracksListOfArtists = new ArrayList<>();
        for (MapperUsersTopTracks mapperUsersTopTrack : usersTopTracksList
                ) {
            String idTrack = mappers.mapperIdTrack(mapperUsersTopTrack);
            MapperArtist trackArtist = spotifyArtistsController.trackOwner(details, idTrack);
            String artist = mappers.mapperArtist(trackArtist);
            usersTopTracksListOfArtists.add(artist);
        }
        return usersTopTracksListOfArtists;
    }

    public List<String> artistsListFromTopArtistsList(List<MapperUsersTopArtists> usersTopArtistsList) {
        List<String> usersTopArtistsListOfArtists = new ArrayList<>();
        for (MapperUsersTopArtists mapperUsersTopArtist : usersTopArtistsList
                ) {
            String artist = mapperUsersTopArtist.getArtistName();
            usersTopArtistsListOfArtists.add(artist);
        }
        return usersTopArtistsListOfArtists;
    }

    public List<String> artistsListFromRecentlyPlayedTracks(List<MapperRecentlyPlayedTracks> recentlyPlayedTracks) {
        List<String> usersRecentlyPlayedTracksListOfArtists = new ArrayList<>();
        for (MapperRecentlyPlayedTracks mapperRecentlyPlayedTracks : recentlyPlayedTracks
                ) {
            String artist = mapperRecentlyPlayedTracks.getArtistName();
            usersRecentlyPlayedTracksListOfArtists.add(artist);
        }
        return usersRecentlyPlayedTracksListOfArtists;
    }


    //lists of tracks

    public List<MapperRecommendations> takeRecommendationsTracksList(List<String> usersTopTracksListOfArtists, OAuth2Authentication details) {
        for (String artist : usersTopTracksListOfArtists
                ) {
            saveRecommendationsTrack(spotifyArtistsController.tracksRecommendations(details, artist, spotifyTracksController, mappers));
        }
        return recommendationsTracksList;
    }

    private List<MapperRecommendations> recommendationsTracksList = new ArrayList<>();

    private List<MapperRecommendations> saveRecommendationsTrack(List<MapperRecommendations> mapperRecommendations) {
        recommendationsTracksList.addAll(mapperRecommendations);
        return recommendationsTracksList;
    }

    public List<MapperTracks> drawRelatedArtistsAndTheirTracks(OAuth2Authentication details, List<String> usersTopTracksListOfArtists) {
        List<MapperRelatedArtist> drawnReletaedArtistsList = drawRelatedArtists(usersTopTracksListOfArtists, details);
        System.out.println("Liczba wylosowanych artystów z Related: " + drawnReletaedArtistsList.size());

        List<MapperTracks> artistsTracksList = takeTracksToListOfArtists(drawnReletaedArtistsList, details);
        System.out.println("Liczba wybranych utworów od Related artystów: " + artistsTracksList.size());

        return artistsTracksList;
    }

    private List<MapperTracks> takeTracksToListOfArtists(List<MapperRelatedArtist> drawnReletaedArtistsList, OAuth2Authentication details) {
        List<String> relatedArtistList = new ArrayList<>();
        for (MapperRelatedArtist relatedArtists : drawnReletaedArtistsList
                ) {
            relatedArtistList.add(relatedArtists.getArtistName());
        }
        for (String relatedArtist : relatedArtistList
                ) {
            saveArtistTrack(spotifyTracksController.tracks(details, relatedArtist));
        }
        return artistsTracksList;
    }

    private List<MapperTracks> artistsTracksList = new ArrayList<>();

    private List<MapperTracks> saveArtistTrack(List<MapperTracks> mapperArtistTracks) {
        artistsTracksList.addAll(mapperArtistTracks);
        return artistsTracksList;
    }


    //lists of artists

    private List<MapperRelatedArtist> drawRelatedArtists(List<String> usersTopTracksListOfArtists, OAuth2Authentication details) {
        for (String artist : usersTopTracksListOfArtists
                ) {
            List<MapperRelatedArtist> drawnArtistsList = saveDrawnRealtedArtists(spotifyArtistsController.relatedArtists(details, artist, spotifyTracksController, mappers));
            saveDrawnRelatedArtistsListTotal(drawnArtistsList);
        }
        return drawnRelatedArtistsListTotal;
    }

    private List<MapperRelatedArtist> drawnRelatedArtistsListTotal = new ArrayList<>();

    private List<MapperRelatedArtist> saveDrawnRelatedArtistsListTotal(List<MapperRelatedArtist> drawnRelatedArtistsList) {
        drawnRelatedArtistsListTotal.addAll(drawnRelatedArtistsList);
        return drawnRelatedArtistsListTotal;
    }

    private List<MapperRelatedArtist> drawnRelatedArtistsList = new ArrayList<>();

    private List<MapperRelatedArtist> saveDrawnRealtedArtists(List<MapperRelatedArtist> mapperRelatedArtist) {
        drawRelatedArtistsList(mapperRelatedArtist);
        return drawnRelatedArtistsList;
    }

    private void drawRelatedArtistsList(List<MapperRelatedArtist> mapperRelatedArtist) {
        while (drawnRelatedArtistsList.size() != Util.howManyRelatedArtistsPerOneArtist) {
            drawRelatedArtist(mapperRelatedArtist);
        }
    }

    private List<MapperRelatedArtist> drawRelatedArtist(List<MapperRelatedArtist> mapperRelatedArtist) {
        MapperRelatedArtist artist = drawArtist(mapperRelatedArtist);
        if (drawnRelatedArtistsList.contains(artist)) {
            drawRelatedArtist(mapperRelatedArtist);
        } else {
            drawnRelatedArtistsList.add(artist);
        }
        return drawnRelatedArtistsList;
    }

    private MapperRelatedArtist drawArtist(List<MapperRelatedArtist> mapperRelatedArtist) {
        Integer drawnIndex = Util.draw(0, mapperRelatedArtist.size() - 1);
        return mapperRelatedArtist.get(drawnIndex);
    }


    //total tracks

    public List<MapperRecommendations> tracksPreparedForPlaylist(List<MapperRecommendations> recommendationsTracksList, List<MapperTracks> artistsTracksList) {
        List<MapperRecommendations> totalTrack = saveTotalTracksRecommendations(recommendationsTracksList, artistsTracksList, mappers);
        System.out.println("Total utworów: " + totalTrack.size());

        List<MapperRecommendations> tracksForPlaylist = saveTracksToPlaylist(totalTrack);
        System.out.println("Utwory do Playlisty: " + tracksForPlaylist.size());

        return tracksForPlaylist;
    }

    private List<MapperRecommendations> drawnListOfTracks = new ArrayList<>();

    private List<MapperRecommendations> saveTracksToPlaylist(List<MapperRecommendations> totalTracksRecommendations) {
        drawTracksFromTheList(totalTracksRecommendations);
        return drawnListOfTracks;
    }

    private void drawTracksFromTheList(List<MapperRecommendations> totalTracksRecommendations) {
        while (drawnListOfTracks.size() != Util.howManyTracksSavedToPlaylist) {
            drawTracks(totalTracksRecommendations);
        }
    }

    private List<MapperRecommendations> drawTracks(List<MapperRecommendations> totalTracksRecommendations) {
        MapperRecommendations track = drawTrack(totalTracksRecommendations);
        if (drawnListOfTracks.contains(track)) {
            drawTracks(totalTracksRecommendations);
        } else {
            drawnListOfTracks.add(track);
        }
        return drawnListOfTracks;
    }

    private MapperRecommendations drawTrack(List<MapperRecommendations> totalTracksRecommendations) {
        Integer track = Util.draw(0, totalTracksRecommendations.size() - 1);
        return totalTracksRecommendations.get(track);
    }

    private List<MapperRecommendations> totalTracksRecommendations = new ArrayList<>();

    private List<MapperRecommendations> saveTotalTracksRecommendations
            (List<MapperRecommendations> mapperRecommendations, List<MapperTracks> artistTrackList, Mappers mappers) {
        for (MapperTracks track : artistTrackList
                ) {
            totalTracksRecommendations.add(mappers.mapperTrackRecommendations(track));
        }
        totalTracksRecommendations.addAll(mapperRecommendations);
        return totalTracksRecommendations;
    }

    //erase lists

    public void eraseSpotifyServiceLists(){
        eraseRecommendationsTracksList();
        eraseArtistsTracksList();
        eraseDrawnRelatedArtistsListTotal();
        eraseDrawnRelatedArtistsList();
        eraseDrawnListOfTracks();
        eraseTotalTracksRecommendations();
    }

    private List<MapperRecommendations> eraseRecommendationsTracksList() {
        recommendationsTracksList.removeAll(recommendationsTracksList);
        return recommendationsTracksList;
    }

    private List<MapperTracks> eraseArtistsTracksList() {
        artistsTracksList.removeAll(artistsTracksList);
        return artistsTracksList;
    }

    private List<MapperRelatedArtist> eraseDrawnRelatedArtistsListTotal() {
        drawnRelatedArtistsListTotal.removeAll(drawnRelatedArtistsListTotal);
        return drawnRelatedArtistsListTotal;
    }

    private List<MapperRelatedArtist> eraseDrawnRelatedArtistsList() {
        drawnRelatedArtistsList.removeAll(drawnRelatedArtistsList);
        return drawnRelatedArtistsList;
    }

    private List<MapperRecommendations> eraseDrawnListOfTracks() {
        drawnListOfTracks.removeAll(drawnListOfTracks);
        return drawnListOfTracks;
    }

    private List<MapperRecommendations> eraseTotalTracksRecommendations() {
        totalTracksRecommendations.removeAll(totalTracksRecommendations);
        return totalTracksRecommendations;
    }
}