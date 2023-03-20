package ulb.algo2.node;

import org.locationtech.jts.geom.MultiPolygon;
import ulb.algo2.MBR;
import ulb.algo2.node.LeafData;


public class Leaf extends AbstractNode {

	LeafData data;

	// Constructor
	public Leaf(Node father, MBR<Double> mbr, LeafData data) {
		super(father, mbr);
		this.data = data;
	}

	// Modifiers
	public void swapData(Leaf other) {
		super.swapData(other);
		LeafData temp = this.data;
		this.data = other.data;
		other.data = temp;
	}

	// Verifiers
	@Override
	public boolean isLeaf() { return true; }


	// Setters
	public void setData(LeafData data) { this.data = data; }
	public void setDataPolygon(MultiPolygon polygon) { this.data.setPolygon(polygon); }
	public void setDataLabel(String label) { this.data.setLabel(label); }


	// Getters
	public MultiPolygon getDataPolygon() { return data.getPolygon(); }
	public String getDataLabel() { return data.getLabel(); }


}
