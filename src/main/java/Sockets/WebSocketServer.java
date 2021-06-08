package Sockets;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import Model.Objednavka;
import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import jdk.internal.net.http.websocket.MessageEncoder;

@ServerEndpoint(value = "/endpoint")
public class WebSocketServer {

    private static PushTimeService pst;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen::" + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" + session.getId());
    }

    @OnMessage
    public void objednavkaReceiver(Session session, Objednavka objednavka) throws IOException, EncodeException {
        try {
            session.getBasicRemote().sendObject(objednavka);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }
}
