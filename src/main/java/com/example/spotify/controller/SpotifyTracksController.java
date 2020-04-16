package com.example.spotify.controller;

import com.example.spotify.mapperModel.*;
import com.example.spotify.modelArtistTopTrack.SpotifyArtistTopTrack;
import com.example.spotify.modelRecentlyPlayedTracks.SpotifyRecentlyPlayedTracks;
import com.example.spotify.modelTracks.SpotifyTracks;
import com.example.spotify.modelUsersTopArtistsAndTracks.UsersTopArtistsAndTracks;
import com.example.spotify.service.Util;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpotifyTracksController {

    @GetMapping("/usersTopTracks")
    public List<MapperUsersTopTracks> usersTopTracks(OAuth2Authentication details) {

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<UsersTopArtistsAndTracks> exchange = restTemplate.exchange("https://api.spotify.com/v1/me/top/tracks?limit=" + Util.usersTopTracksLimit,
                HttpMethod.GET,
                httpEntity,
                UsersTopArtistsAndTracks.class);

        List<MapperUsersTopTracks> mapperUsersTopTracks = exchange.getBody().getItems().stream().map(
                item -> new MapperUsersTopTracks(
                        item.getId(),
                        item.getName(),
                        item.getPopularity(),
                        item.getExternalUrls().getSpotify(),
                        item.getUri()
                )).collect(Collectors.toList());

        return mapperUsersTopTracks;
    }

    @GetMapping("/recentlyPlayedTracks")
    public List<MapperRecentlyPlayedTracks> recentlyPlayedTracks(OAuth2Authentication details) {

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyRecentlyPlayedTracks> exchange = restTemplate.exchange("https://api.spotify.com/v1/me/player/recently-played?type=track&limit=" + Util.usersRecentlyPlayedTracks,
                HttpMethod.GET,
                httpEntity,
                SpotifyRecentlyPlayedTracks.class);

        List<MapperRecentlyPlayedTracks> mapperRecentlyPlayedTracks = exchange.getBody().getItems().stream().map(
                item -> new MapperRecentlyPlayedTracks(
                        item.getTrack().getArtists().get(0).getId(),
                        item.getTrack().getArtists().get(0).getName(),
                        item.getTrack().getId(),
                        item.getTrack().getName(),
                        item.getTrack().getPopularity(),
                        item.getTrack().getExternalUrls().getSpotify(),
                        //item.getTrack().getAlbum().getName(),
                        //item.getTrack().getAlbum().getImages().get(0).getUrl(),
                        item.getTrack().getUri()
                )).collect(Collectors.toList());

        return mapperRecentlyPlayedTracks;
    }

    @GetMapping("/tracks/{artist}")
    public List<MapperTracks> tracks(OAuth2Authentication details, @PathVariable String artist) {

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyTracks> exchange = restTemplate.exchange("https://api.spotify.com/v1/search?q=" + artist + "&type=track&limit=" + Util.tracksPerArtistsLimit,
                HttpMethod.GET,
                httpEntity,
                SpotifyTracks.class);

        List<MapperTracks> mapperTracks = exchange.getBody().getTracks().getItems().stream().map(
                item -> new MapperTracks(
                        item.getArtists().get(0).getId(),
                        item.getArtists().get(0).getName(),
                        item.getId(),
                        item.getName(),
                        item.getPopularity(),
                        item.getExternalUrls().getSpotify(),
                        //item.getAlbum().getName(),
                        //item.getAlbum().getImages().get(0).getUrl(),
                        item.getUri()
                )).collect(Collectors.toList());

        return mapperTracks;
    }

    @GetMapping("/artistTopTrack/{idArtist}")
    public List<MapperArtistTopTrack> artistTopTrack(OAuth2Authentication details, @PathVariable String idArtist) {

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyArtistTopTrack> exchange = restTemplate.exchange("https://api.spotify.com/v1/artists/" + idArtist + "/top-tracks?country=PL",
                HttpMethod.GET,
                httpEntity,
                SpotifyArtistTopTrack.class);

        List<MapperArtistTopTrack> mapperArtistTopTrack = exchange.getBody().getTracks().stream().map(
                item -> new MapperArtistTopTrack(
                        item.getId(),
                        item.getName(),
                        item.getArtists().get(0).getId(),
                        item.getArtists().get(0).getName(),
                        item.getPopularity(),
                        item.getExternalUrls().getSpotify(),
                        //item.getAlbum().getName(),
                        //item.getAlbum().getImages().get(0).getUrl(),
                        item.getUri()
                )).collect(Collectors.toList());

        return mapperArtistTopTrack;
    }
}