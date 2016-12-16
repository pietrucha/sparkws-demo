package pietruh.spark.ws.demo;

import static spark.Spark.*;

/**
 * Created by ppietrucha on 2016-12-16.
 */
public class Runner {

    public static void main(String[] args) {
        port(9090);
//        staticFileLocation("/public"); //index.html is served at localhost:4567 (default port)
        webSocket("/chat", WebSocketHandler.class);
        init();
    }
}
