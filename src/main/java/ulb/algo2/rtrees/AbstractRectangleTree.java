package ulb.algo2.rtrees;

import org.locationtech.jts.geom.MultiPolygon;

import ulb.algo2.MBR;
import ulb.algo2.node.*;



public abstract class AbstractRectangleTree {

	protected Node root;


	// Modifiers
	public Node chooseNode(Node node, MultiPolygon polygon) {
		// TODO implement
		return null;
	}

	public void addLeaf(Node node, String label, MultiPolygon polygon) {
		// TODO implement
	}

	public abstract Node split(Node node);


	// Setter
	public void setRoot(Node root) { this.root = root; }


}