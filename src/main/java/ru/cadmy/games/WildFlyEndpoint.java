package ru.cadmy.games;

import javax.inject.Singleton;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * ChatServer
 * @author Jiji_Sasidharan
 */
@ServerEndpoint(value="/game_endpoint")
@Singleton
public class WildFlyEndpoint {

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("New request received. Id: " + userSession.getId());
        userSession.getAsyncRemote().sendText("Hello websockets");
    }

    /**
     * Callback hook for Message Events.
     *
     * This method will be invoked when a client send a message.
     *
     * @param message The text message
     * @param userSession The session of the client
     */
    @OnMessage
    public void onMessage(String message, Session userSession) {
        System.out.println("Message Received: " + message);
        for (Session session : userSession.getOpenSessions()) {
            if (session.isOpen())
                session.getAsyncRemote().sendText(message);
        }
    }
}
