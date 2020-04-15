package com.example.spotify.controller;

import com.example.spotify.mapperModel.MapperUsersPlaylists;
import com.example.spotify.modelUsersPlaylists.SpotifyUsersPlaylists;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpotifyPlaylistsController {

    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/usersPlaylists")
    public List<MapperUsersPlaylists> usersPlaylists(OAuth2Authentication details) {

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyUsersPlaylists> exchange = restTemplate.exchange("https://api.spotify.com/v1/me/playlists",
                HttpMethod.GET,
                httpEntity,
                SpotifyUsersPlaylists.class);

        List<MapperUsersPlaylists> mapperUsersPlaylists = exchange.getBody().getItems().stream().map(
                item -> new MapperUsersPlaylists(
                        item.getId(),
                        item.getName(),
                        item.getTracks().getTotal(),
                        item.getExternalUrls().getSpotify()
                )).collect(Collectors.toList());

        return mapperUsersPlaylists;
    }

    @GetMapping("/newPlaylist")
    public void newPlaylist(OAuth2Authentication details, Principal principal, @RequestParam(value = "name") String name, @RequestParam(value = "description") String description) {

        String userId = user(principal).getName();

        //String jsonToSent="{\"name\":\"Recommendations\",\"description\":\"Recommendations made by SaadApp\"}";

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.add(HttpHeaders.ACCEPT, "application/json");

        LinkedMultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("name", name);
        bodyMap.add("description", description);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(bodyMap, httpHeaders);

        ResponseEntity<String> savePlaylist = restTemplate.exchange("https://api.spotify.com/v1/users/" + userId + "/playlists",
                HttpMethod.POST,
                httpEntity,
                String.class);

        //400 Bad Request: [{ “error” : { “status” : 400, “message” : “Error parsing JSON.” } }]
    }

    @GetMapping("/saveTrackInPlaylist")
    public void saveTrackInPlaylist(OAuth2Authentication details, @PathVariable String idPlaylist, @PathVariable String uriTrack) {

        String jwt = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.add(HttpHeaders.ACCEPT, "application/json");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        restTemplate.exchange("https://api.spotify.com/v1/playlists/" + idPlaylist + "/tracks?uris=" + uriTrack,
                HttpMethod.POST,
                httpEntity,
                String.class);
    }
}