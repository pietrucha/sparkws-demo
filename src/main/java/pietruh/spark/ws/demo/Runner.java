package pietruh.spark.ws.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import org.geotools.geometry.jts.JTSFactoryFinder;
import pietruh.spark.ws.demo.model.Line;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static spark.Spark.*;

/**
 * Created by ppietrucha on 2016-12-16.
 */
public class Runner {

    public static void main(String[] args) {
        port(9090);
        // staticFileLocation("/public"); //index.html is served at
        // localhost:4567 (default port)
        webSocket("/chat", WebSocketHandler.class);
        get("/lines/:id", (req, res) -> {
            try {
                GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
                String id = req.params(":id");

                Coordinate[] coords =
                      new Coordinate[] { new Coordinate(49.75177, 21.48067), new Coordinate(49.74534, 21.47338), new Coordinate(49.7419, 21.46857),
                            new Coordinate(49.73713, 21.46634) };

                LineString line = geometryFactory.createLineString(coords);
                Line l = new Line();
                l.setName(id);
                l.setGeometry(line);
                l.setCoordinates(line.getCoordinates());
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
                String s1 = gson.toJson(l, Line.class);
                return s1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        get("/lines", (req, res) -> {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
            List<Line> lines = IntStream.range(0, 10).mapToObj(i -> {
                Line l = new Line();
                l.setName("" + i);
                return l;
            }).collect(Collectors.toList());

            return gson.toJson(lines);
        });
        init();
    }
}
