package ru.cadmy.games.service;

import java.util.Random;

/**
 * Created by Cadmy on 25.01.2015.
 */
public class WaterWorldService {
    public static String generateColor() {
        String [] colorParts = { "A", "B", "C", "D", "E", "F",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            sb.append( colorParts [ random.nextInt(15) ] );
        }
        return sb.toString();
    }
}
