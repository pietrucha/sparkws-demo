package pietruh.spark.ws.demo;

import static spark.Spark.*;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by ppietrucha on 2016-12-16.
 */
public class Runner {

	public static void main(String[] args) {
		port(9090);
		// staticFileLocation("/public"); //index.html is served at
		// localhost:4567 (default port)
		webSocket("/chat", WebSocketHandler.class);
		get("/line", (req, res) -> {
			Gson gson = new Gson();
			gson.toJson(src)
			return null;
		});
		init();
	}
}
