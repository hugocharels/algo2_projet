package ulb.algo2.node;

import org.locationtech.jts.geom.MultiPolygon;


public class LeafData {

	private String label;
	private MultiPolygon polygon;

	// Constructor
	public LeafData(String label, MultiPolygon polygon) {
		this.label = label;
		this.polygon = polygon;
	}

	// Setters
	public void setLabel(String label) { this.label = label; }
	public void setPolygon(MultiPolygon polygon) { this.polygon = polygon; }

	// Getters
	public String getLabel() { return label; }
	public MultiPolygon getPolygon() { return polygon; }

}
