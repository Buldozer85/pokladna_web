package Sockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

import Model.Objednavka;

import jakarta.websocket.Session;

/**
 * ObjednavkaSessionHandler
 */

public class ObjednavkaSessionHandler {
    private int objednavkyId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Objednavka> Objednavka = new HashSet<>();
    private final Set<Objednavka> ObjednavkaVydej = new HashSet<>();
    private static ObjednavkaSessionHandler instance;

    public static ObjednavkaSessionHandler getInstance() {
        if (instance == null) {
            instance = new ObjednavkaSessionHandler();

        }
        return instance;
    }

    public Set<Objednavka> getObjednavkaVydej() {
        return ObjednavkaVydej;
    }

    public void addSession(Session session) {

        sessions.add(session);

        for (Objednavka objednavka2 : Objednavka) {
            JsonObject addMessage = createAddMessage(objednavka2);
            sendToSession(session, addMessage);

        }
        for (Objednavka objednavka3 : ObjednavkaVydej) {
            JsonObject addMessage = createAddMessage(objednavka3);
            sendToSession(session, addMessage);

        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);

    }

    public List<Objednavka> getObjednavky() {
        return new ArrayList<>(Objednavka);
    }

    public void addObjednavka(Objednavka objednavka) {
        objednavka.setId(objednavkyId);
        Objednavka.add(objednavka);
        objednavkyId++;

        JsonObject addMessage = createAddMessage(objednavka);
        sendToAllConnectedSessions(addMessage);
    }

    public void vydejObjednavka(int id) {
        Objednavka o = getObjednavkaById(id);
        if (o != null) {
            Objednavka.remove(o);
            ObjednavkaVydej.add(o);

            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder().add("action", "remove").add("id", id).build();
            sendToAllConnectedSessions(removeMessage);
            posliNaVydej(o);
        }
    }

    public void vyzvednutaObjednavka(int id) {
        Objednavka o = getObjednavkaVydejById(id);
        if (o != null) {

            ObjednavkaVydej.remove(o);

            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder().add("action", "vyzvednuta").add("id", id).build();
            sendToAllConnectedSessions(removeMessage);

        }
    }

    public void posliNaVydej(Objednavka o) {

        JsonObject addMessage = createVydejMessage(o);
        sendToAllConnectedSessions(addMessage);
    }

    public void toggleDevice(int id) {

    }

    private Objednavka getObjednavkaById(int id) {
        for (Objednavka o : Objednavka) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    private Objednavka getObjednavkaVydejById(int id) {
        for (Objednavka o : ObjednavkaVydej) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(Objednavka objednavka) {
        JsonProvider provider = JsonProvider.provider();

        JsonObject addMessage = provider.createObjectBuilder().add("action", "add").add("id", objednavka.getId())
                .add("casObjednavky", objednavka.getCasObjednavky()).add("cena", objednavka.getCena())

                .build();
        return addMessage;
    }

    private JsonObject createVydejMessage(Objednavka objednavka) {
        JsonProvider provider = JsonProvider.provider();

        JsonObject addMessage = provider.createObjectBuilder().add("action", "vydej").add("id", objednavka.getId())
                .add("casObjednavky", objednavka.getCasObjednavky()).add("cena", objednavka.getCena())

                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);

        }

    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());

        } catch (IOException ex) {
            sessions.remove(session);

        }

    }
}