package com.example.spotify.controller;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/user").authenticated();
        http.authorizeRequests().antMatchers("/usersPlaylists").authenticated();
        http.authorizeRequests().antMatchers("/newPlaylist").authenticated();
        http.authorizeRequests().antMatchers("/saveTrackInPlaylist").authenticated();

        http.authorizeRequests().antMatchers("/usersTopArtists").authenticated();
        http.authorizeRequests().antMatchers("/recommendations/{artist}").authenticated();
        http.authorizeRequests().antMatchers("/relatedArtist/{artist}").authenticated();
        http.authorizeRequests().antMatchers("/artist/{idTrack}").authenticated();

        http.authorizeRequests().antMatchers("/recentlyPlayedTracks").authenticated();
        http.authorizeRequests().antMatchers("/artistTopTrack/{idArtist}").authenticated();
        http.authorizeRequests().antMatchers("/tracks/{artist}").authenticated();
        http.authorizeRequests().antMatchers("/artistTopTrack/{idArtist}").authenticated();

        http.authorizeRequests().antMatchers("/usersTopTracksRecommendation").authenticated();
        http.authorizeRequests().antMatchers("/usersTopArtistsRecommendation").authenticated();
        http.authorizeRequests().antMatchers("/usersRecentlyPlayedTracksRecommendation").authenticated();

        //test
    }

}
