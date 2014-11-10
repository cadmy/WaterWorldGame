package ru.cadmy.games;

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

    @OnOpen
    public void onOpen(Session userSession) throws IOException {
        System.out.println("New request received. Id: " + userSession.getId());
        userSession.getBasicRemote().sendText("Hello websockets");
    }

    @OnMessage
    public void onMessage(String message, Session userSession) {
        System.out.println("Message Received: " + message);
        for (Session session : userSession.getOpenSessions()) {
            if (session.isOpen())
                session.getAsyncRemote().sendText(message);
        }
    }
}
