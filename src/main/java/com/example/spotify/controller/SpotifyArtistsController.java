package com.example.spotify.controller;

import com.example.spotify.mapperModel.*;
import com.example.spotify.modelArtist.SpotifyArtist;
import com.example.spotify.modelRecommendations.SpotifyRecommendations;
import com.example.spotify.modelRelatedArtist.SpotifyRelatedArtist;
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
public class SpotifyArtistsController {

    @GetMapping("/usersTopArtists")
    public List<MapperUsersTopArtists> usersTopArtists(OAuth2Authentication details) {

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<UsersTopArtistsAndTracks> exchange = restTemplate.exchange("https://api.spotify.com/v1/me/top/artists?limit=" + Util.usersTopArtistsLimit,
                HttpMethod.GET,
                httpEntity,
                UsersTopArtistsAndTracks.class);

        List<MapperUsersTopArtists> mapperUsersTopArtists = exchange.getBody().getItems().stream().map(
                item -> new MapperUsersTopArtists(
                        item.getId(),
                        item.getName(),
                        item.getPopularity(),
                        item.getExternalUrls().getSpotify()
                )).collect(Collectors.toList());

        return mapperUsersTopArtists;
    }

    @GetMapping("/recommendations/{artist}")
    public List<MapperRecommendations> tracksRecommendations(OAuth2Authentication details, @PathVariable String artist, SpotifyTracksController spotifyTracksController, Mappers mappers) {

        MapperTracks idArtistSource = spotifyTracksController.tracks(details, artist).get(0);
        String idArtist = mappers.mapperIdArtist(idArtistSource);

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyRecommendations> exchange = restTemplate.exchange("https://api.spotify.com/v1/recommendations?limit=" + Util.recommendationsTracksLimit + "&seed_artists=" + idArtist,
                HttpMethod.GET,
                httpEntity,
                SpotifyRecommendations.class);

        List<MapperRecommendations> mapperRecommendations = exchange.getBody().getTracks().stream().map(
                item -> new MapperRecommendations(
                        item.getArtists().get(0).getId(),
                        item.getArtists().get(0).getName(),
                        item.getId(),
                        item.getName(),
                        item.getPopularity(),
                        item.getExternalUrls().getSpotify(),
                        item.getAlbum().getName(),
                        item.getAlbum().getImages().get(0).getUrl(),
                        item.getUri()
                )).collect(Collectors.toList());

        return mapperRecommendations;
    }

    @GetMapping("/relatedArtist/{artist}")
    public List<MapperRelatedArtist> relatedArtists(OAuth2Authentication details, @PathVariable String artist, SpotifyTracksController spotifyTracksController, Mappers mappers) {

        MapperTracks idArtistSource = spotifyTracksController.tracks(details, artist).get(0);
        String idArtist = mappers.mapperIdArtist(idArtistSource);

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyRelatedArtist> exchange = restTemplate.exchange("https://api.spotify.com/v1/artists/" + idArtist + "/related-artists",
                HttpMethod.GET,
                httpEntity,
                SpotifyRelatedArtist.class);

        List<MapperRelatedArtist> mapperRelatedArtist = exchange.getBody().getArtists().stream().map(
                artist1 -> new MapperRelatedArtist(
                        artist1.getId(),
                        artist1.getName(),
                        artist1.getPopularity(),
                        artist1.getExternalUrls().getSpotify(),
                        artist1.getImages().get(0).getUrl()
                )).collect(Collectors.toList());

        return mapperRelatedArtist;
    }

    @GetMapping("/artist/{idTrack}")
    public MapperArtist trackOwner(OAuth2Authentication details, @PathVariable String idTrack) {

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyArtist> exchange = restTemplate.exchange("https://api.spotify.com/v1/tracks/" + idTrack,
                HttpMethod.GET,
                httpEntity,
                SpotifyArtist.class);

        MapperArtist mapperTrack = new MapperArtist(
                exchange.getBody().getArtists().get(0).getId(),
                exchange.getBody().getArtists().get(0).getName());

        return mapperTrack;
    }
}
