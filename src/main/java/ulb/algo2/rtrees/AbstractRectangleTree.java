package ulb.algo2.rtrees;

import org.locationtech.jts.geom.Polygon;

import ulb.algo2.MBR;
import ulb.algo2.node.*;



public abstract class AbstractRectangleTree {

	protected Node root;
	protected final int N;


	// Constructor
	public AbstractRectangleTree(int N) {
		this.N = N;
	}

	// Modifiers
	public Node chooseNode(Node node, Polygon polygon) {
		// TODO implement
		return null;
	}

	public Node addLeaf(Node node, String label, Polygon polygon) {
		if (node.getChildrenNb() == 0 || node.getChild(0).isLeaf() ) {
			node.addChild(new Leaf(node, new MBR(polygon), new LeafData(label, polygon)));
		} else {
			Node n = chooseNode(node, polygon);
			Node new_node = addLeaf(n, label, polygon);
			if (new_node != null) { node.addChild(new_node); }
		}
		node.expandMBR(new MBR(polygon));
		if (node.getChildrenNb() >= N) { return split(node); }
		return null;
	}

	public abstract Node split(Node node);


	// Setter
	public void setRoot(Node root) { this.root = root; }


}