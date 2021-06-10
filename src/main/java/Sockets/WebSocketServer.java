package Sockets;

import java.io.IOException;
import java.io.StringReader;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.json.*;

import Model.Objednavka;

import jakarta.inject.Inject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/endpoint")
public class WebSocketServer {
    private Session session;
    private static Set<WebSocketServer> endpoints = new CopyOnWriteArraySet<>();

    @Inject
    private ObjednavkaSessionHandler sessionHandler = ObjednavkaSessionHandler.getInstance();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        endpoints.add(this);
        System.out.println("pripojeno" + session.getId());
        sessionHandler.addSession(session);

    }

    @OnClose
    public void onClose(Session session) {

        sessionHandler.removeSession(session);

    }

    @OnMessage
    public void objednavkaReceiver(String message, Session session) throws IOException, EncodeException {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Objednavka o = Objednavka.getAll().getLast();
                sessionHandler.addObjednavka(o);

            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.vydejObjednavka(id);

            }
            if ("vyzvednuta".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.vyzvednutaObjednavka(id);

            }

        }
        System.out.println("přijato");

    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }

}
