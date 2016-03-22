/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author Casvan
 */
@ServerEndpoint(value="/test/notifications",
        encoders = { CartrackerEncoder.class} ,
        decoders = { CartrackerDecoder.class})
public class WebSocket {
        private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
        
    @OnOpen
    public void onOpen(Session session)
    {
        sessions.add(session);
        System.out.println("Max is echt een flikker");
        session.setMaxBinaryMessageBufferSize(80000);
        session.setMaxTextMessageBufferSize(80000);
        int buffersize = session.getMaxBinaryMessageBufferSize();
        
        System.out.println(String.valueOf(buffersize));
    }
    
     @OnMessage
    public void onMessage(CarTrackerDAO message)
    {
        
    }
    
    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
    }
}
