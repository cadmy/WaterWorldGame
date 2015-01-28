package ru.cadmy.games.service;

import org.junit.Assert;

public class WaterWorldServiceTest {
    @org.junit.Test
    public void testGenerateColor() throws Exception {
        Assert.assertEquals(WaterWorldService.generateColor().length(), 7);
    }

}