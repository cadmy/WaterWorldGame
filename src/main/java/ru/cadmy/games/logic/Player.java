package ru.cadmy.games.logic;

import javax.websocket.Session;

import lombok.Data;

/**
 * Created by Cadmy on 25.01.2015.
 */
public @Data class Player {
    private String sessionName;
    private String playerName;
    private int x;
    private int y;

    public Player(Session session, int x, int y) {
        this.sessionName = session.getId();
        this.playerName = sessionName;
        this.x = x;
        this.y = y;
    }

}
