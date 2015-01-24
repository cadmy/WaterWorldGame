package ru.cadmy.games;

import org.apache.log4j.Logger;

import javax.inject.Singleton;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * GameServer
 * @author cadmy
 */
@ServerEndpoint(value="/game_endpoint")
@Singleton
public class WildFlyEndpoint {

    final static Logger logger = Logger.getLogger(WildFlyEndpoint.class);

    @OnOpen
    public void onOpen(Session userSession) throws IOException {
        logger.info("New request received. Id: " + userSession.getId());
        userSession.getBasicRemote().sendText("Hello websockets");
    }

    @OnMessage
    public void onMessage(String message, Session userSession) {
        logger.info("Message Received: " + message);
        for (Session session : userSession.getOpenSessions()) {
            if (session.isOpen())
                session.getAsyncRemote().sendText(message);
        }
    }
}
