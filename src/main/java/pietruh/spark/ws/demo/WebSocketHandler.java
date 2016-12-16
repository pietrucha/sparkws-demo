package pietruh.spark.ws.demo;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ppietrucha on 2016-12-16.
 */
@WebSocket
public class WebSocketHandler {

    private Set<Session> sessions = new HashSet<>();
    private ScheduledExecutorService ex;
    private Random r = new Random();

    /**
     *
     * {
     "type": "Feature",
     "geometry": {
     "type": "Point",
     "coordinates": [125.6, 10.1]
     },
     "properties": {
     "name": "Dinagat Islands"
     }
     }
     *
     * @param user
     * @throws Exception
     */

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        sessions.add(user);
        System.out.println("Connect user = [" + user + "]");
        if (ex == null) {
            ex = Executors.newScheduledThreadPool(1);
            Runnable task = () -> {
                double latD = r.nextDouble() * .001 + 49.74d;
                double lanD = r.nextDouble() * .001 + 21.42d;
                //                String msg = latD + "," + lanD;
                String msg="{ \"type\": \"Feature\", "
                    + "\"geometry\": { "
                        + "\"type\": \"Point\","
                        + "\"coordinates\": ["+lanD+","+latD+"] "
                    + "},"
                    + "\"properties\": { "
                        + "\"name\": \"Dinagat Islands\""
                    + "}"
                + "}";

//                String msg = "{ \"type\": \"Point\", \"coordinates\": [" + latD + ", " + lanD + "] }";
                sessions.stream().filter(Session::isOpen).forEach(session -> {
                    try {
                        System.out.println("user = [" + session.getRemoteAddress().getHostName() + "] send:" + msg);
                        session.getRemote().sendString(String.valueOf(msg));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            };
            ex.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);
        }
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Close user = [" + user + "], statusCode = [" + statusCode + "], reason = [" + reason + "]");
        sessions.remove(user);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        //        sendMessage(user.getRemoteAddress().getHostString() + message);
    }

    private void sendMessage(String message) {
        sessions.stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
