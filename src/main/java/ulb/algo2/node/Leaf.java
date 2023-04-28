package ulb.algo2.node;

import org.locationtech.jts.geom.Polygon;


public class Leaf extends AbstractNode {

	LeafData data;

	// Constructor
	public Leaf(Node father, MBR mbr, LeafData data) {
		super(father, mbr);
		this.data = data;
	}

	// Verifiers
	@Override
	public boolean isLeaf() { return true; }

	// Setters
	public void setData(LeafData data) { this.data = data; }
	public void setDataPolygon(Polygon polygon) { this.data.setPolygon(polygon); }
	public void setDataLabel(String label) { this.data.setLabel(label); }

	// Getters
	public LeafData getData() { return data; }
	public Polygon getDataPolygon() { return data.getPolygon(); }
	public String getDataLabel() { return data.getLabel(); }

}