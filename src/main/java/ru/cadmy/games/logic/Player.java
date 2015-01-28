package ru.cadmy.games.logic;

import javax.websocket.Session;

/**
 * Created by Cadmy on 25.01.2015.
 */
public class Player {
    String sessionName;
    String playerName;
    Position position;
    int width;
    int height;
    String color;

    public Player(Session session, int x, int y, int width, int height, String color) {
        this.sessionName = session.getId();
        this.playerName = sessionName;
        Position position = new Position(x, y);
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (height != player.height) return false;
        if (width != player.width) return false;
        if (color != null ? !color.equals(player.color) : player.color != null) return false;
        if (playerName != null ? !playerName.equals(player.playerName) : player.playerName != null) return false;
        if (position != null ? !position.equals(player.position) : player.position != null) return false;
        if (sessionName != null ? !sessionName.equals(player.sessionName) : player.sessionName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionName != null ? sessionName.hashCode() : 0;
        result = 31 * result + (playerName != null ? playerName.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
