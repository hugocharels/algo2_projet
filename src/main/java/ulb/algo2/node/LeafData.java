package ulb.algo2.node;

import org.locationtech.jts.geom.Polygon;


public class LeafData {

	private String label;
	private Polygon polygon;

	// Constructor
	public LeafData(String label, Polygon polygon) {
		this.label = label;
		this.polygon = polygon;
	}

	// Setters
	public void setLabel(String label) { this.label = label; }
	public void setPolygon(Polygon polygon) { this.polygon = polygon; }

	// Getters
	public String getLabel() { return label; }
	public Polygon getPolygon() { return polygon; }

}
