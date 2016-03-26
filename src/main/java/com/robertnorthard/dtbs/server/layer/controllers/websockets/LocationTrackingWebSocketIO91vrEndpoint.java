package com.robertnorthard.dtbs.server.layer.controllers.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robertnorthard.dtbs.server.common.dto.TaxiLocationEventDto;
import com.robertnorthard.dtbs.server.layer.service.LocationTrackingService;
import com.robertnorthard.dtbs.server.layer.utils.LocationTrackingObserver;
import com.robertnorthard.dtbs.server.layer.utils.datamapper.DataMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Web socket (bidirectional endpoint) for broadcasting location events to all
 * connected clients.
 *
 * @author robertnorthard
 */
@Singleton
@ServerEndpoint(value = "/ws/v1/locations/taxi/IO91vr")
public class LocationTrackingWebSocketIO91vrEndpoint implements LocationTrackingObserver {

    private static final Logger LOGGER = Logger.getLogger(
            LocationTrackingWebSocketEndpoint.class.getName());

    private static final Set<Session> observers = Collections.synchronizedSet(new HashSet<Session>());

    @Inject
    private LocationTrackingService locationTrackingService;

    @PostConstruct
    void init() {
        this.locationTrackingService.registerObserver(this);
    }

    @OnOpen
    public void onOpen(Session session) {
        observers.add(session);
    }

    @OnClose
    public void onClose(Session session) {

        LOGGER.log(Level.FINE, "Removing client {0}", session.getId());

        Iterator<Session> iterator = observers.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(session.getId())) {
                iterator.remove();
                break;
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Empty - communication one way. Server to client.
    }

    @OnError
    public void onError(Throwable t) {
        LOGGER.log(Level.WARNING, t.getMessage());
    }

    @Override
    public void update(Object obj) {
        for (Session o : observers) {
            if (o.isOpen()) {
                if (obj instanceof TaxiLocationEventDto) {
                    try {
                        TaxiLocationEventDto e = (TaxiLocationEventDto) obj;
                        o.getAsyncRemote().sendObject(DataMapper.getInstance().writeValueAsString(e));
                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(LocationTrackingWebSocketIO91vsEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public String getGridReference() {
        return "IO91vr";
    }
}