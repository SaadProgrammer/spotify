package com.example.spotify.service;

import org.junit.Assert;
import org.junit.Test;

public class SpotifyServiceTest {

    @Test
    public void fukctionIsCheckingIfDrawFunkctionWorks() {
        //given
        int min = 0;
        int max = 4;
        //when
        int result = Util.draw(min, max);
        //then
        Assert.assertTrue(result > min || result < max);
    }
}