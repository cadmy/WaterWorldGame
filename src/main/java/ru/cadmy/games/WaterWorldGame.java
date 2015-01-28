package ru.cadmy.games;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.cadmy.games.logic.Player;
import ru.cadmy.games.logic.Position;
import ru.cadmy.games.service.WaterWorldService;

import javax.inject.Singleton;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GameServer
 * @author cadmy
 */
@ServerEndpoint(value="/game_endpoint")
@Singleton
public class WaterWorldGame {

    final static Logger logger = Logger.getLogger(WaterWorldGame.class);
    Map<Session, Player> players = new HashMap<>();
    Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session userSession) throws IOException {
        logger.info("New request received. Id: " + userSession.getId());
        Player player = new Player(userSession, 0, 0, 3, 3, WaterWorldService.generateColor());
        players.put(userSession, player);
        sendPositionsData(userSession);
    }

    @OnMessage
    public void onMessage(String userPosition, Session userSession) {
        logger.info("Message Received: " + userPosition);
        Player player = players.get(userSession);
        player.setPosition(gson.fromJson(userPosition, Position.class));
        sendPositionsData(userSession);
    }

    private void sendPositionsData(Session userSession) {

        for (Session session : userSession.getOpenSessions()) {
            if (session.isOpen())
            {
                for (Player player : players.values())
                {
                    gson.toJson(player);
                    session.getAsyncRemote().sendText(gson.toString());
                }
            }
            else
            {
                if ( players.containsKey(session) )
                {
                    players.remove(session);
                }
            }
        }
    }
}
