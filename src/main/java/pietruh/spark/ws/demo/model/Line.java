package pietruh.spark.ws.demo.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;

public class Line {

    private String name;
    private LineString geometry;
    private Coordinate coordinates[];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LineString getGeometry() {
        return geometry;
    }

    public void setGeometry(LineString geometry) {
        this.geometry = geometry;
    }

    public Coordinate[] getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coordinate[] coords) {
        this.coordinates = coords;
    }

    @Override
    public String toString() {
        return "Line [name=" + name + ", geometry=" + geometry + "]";
    }

}
