package com.example.spotify.service;

import java.util.Random;

public class Util {

    public static String usersRecentlyPlayedTracks = "10";

    public static String usersTopTracksLimit = "10";

    public static String usersTopArtistsLimit = "10";

    public static String tracksPerArtistsLimit = "10";

    public static String recommendationsTracksLimit = "20";

    public static Integer howManyRelatedArtistsPerOneArtist = 2;

    public static Integer howManyTracksSavedToPlaylist = 50;

    public static int draw(int minimum, int maximum) {
        Random rn = new Random();
        return rn.nextInt(maximum - minimum + 1) + minimum;
    }
}

