package ulb.algo2.rtrees;

import org.locationtech.jts.geom.MultiPolygon;

import ulb.algo2.MBR;
import ulb.algo2.node.*;



public abstract class AbstractRectangleTree {

	protected Node root;


	// Modifiers
	public Node chooseNode(Node node, MultiPolygon polygon) throws Exception {
		// TODO implement
		return null;
	}

	public void Node addLeaf(Node node, String label, MultiPolygon polygon) {
		if (node.getChildrenNb() == 0 || node.getChild(0).isLeaf() ) {
			// TODO MBR
			MBR<Double> mbr = new MBR<Double>(0., 0., 0., 0.);
			node.addChild(new Leaf(node, mbr, new LeafData(label, polygon)));
		} else {
			// TODO je me suis peut être trompé
			Node n = chooseNode(node, polygon);
			Node new_node = addLeaf(n, label, polygon);
			if (new_node != null) { n.addChild(new_node); }
		}
		// TODO expand jsp quoi de l'énoncé
		if (node.getChildrenNb() >= N) { return split(node); }
		else { return null;}
	}

	public abstract Node split(Node node);


	// Setter
	public void setRoot(Node root) { this.root = root; }


}