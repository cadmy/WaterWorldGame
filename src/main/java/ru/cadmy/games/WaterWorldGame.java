package ru.cadmy.games;

import com.google.gson.Gson;

import org.apache.log4j.Logger;

import ru.cadmy.games.logic.Player;
import ru.cadmy.games.logic.Position;

import javax.inject.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GameServer
 *
 * @author cadmy
 */
@ServerEndpoint(value = "/game_endpoint")
@Singleton
public class WaterWorldGame {

    final static Logger logger = Logger.getLogger(WaterWorldGame.class);
    Map<Session, Player> players = new HashMap<>();
    Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session userSession) throws IOException {
        logger.info("New request received. Id: " + userSession.getId());
        sendPositionsData(userSession);
    }

    @OnClose
    public void onClose() throws IOException {
        logger.info("Connection is closed ");
    }

    @OnMessage
    public void onMessage(String userPosition, Session userSession) {
        logger.info("Message Received: " + userPosition);
        Position position = gson.fromJson(userPosition, Position.class);
        Player player = null;
        if (players.containsKey(userSession)) {
            player = players.get(userSession);
            player.setX(position.getX());
            player.setY(position.getY());
        } else {
            player = new Player(userSession, position.getX(), position.getY());
            players.put(userSession, player);
        }
        sendPositionsDataToEveryone(userSession);
    }

    private void sendPositionsData(Session userSession) {
        logger.info("sendPositionsData()");
        for (Session session : userSession.getOpenSessions()) {
            if (session.isOpen()) {
                for (Player player : players.values()) {
                    if (!session.equals(userSession)
                            || !player.getSessionName().equals(userSession.getId())) {
                        logger.info("Message sent: " + gson.toJson(player));
                        session.getAsyncRemote().sendText(gson.toJson(player));
                    }

                }
            } else {
                if (players.containsKey(session)) {
                    logger.info("Session removed: " + session.getId());
                    players.remove(session);
                }
            }
        }
    }

    private void sendPositionsDataToEveryone(Session userSession) {
        logger.info("sendPositionsData()");
        for (Session session : userSession.getOpenSessions()) {
            if (session.isOpen()) {
                for (Player player : players.values()) {
                    logger.info("Message sent: " + gson.toJson(player));
                    session.getAsyncRemote().sendText(gson.toJson(player));
                }
            } else {
                if (players.containsKey(session)) {
                    logger.info("Session removed: " + session.getId());
                    players.remove(session);
                }
            }
        }
    }
}
