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
		Node best = null;
		for (AbstractNode n : node.getChildren()) {
			if (best == null) { best = (Node) n; continue; }
			double best_area = best.getMBR().getArea();
			double n_area = n.getMBR().getArea();
			double best_expansion = best.getMBR().getExpansion(new MBR(polygon));
			double n_expansion = n.getMBR().getExpansion(new MBR(polygon));
			if (n_expansion < best_expansion) { best = (Node) n; }
			else if (n_expansion == best_expansion && n_area < best_area) { best = (Node) n; }
		}
		return best;
	}

	public Node addLeaf(Node node, String label, Polygon polygon) {
		if (node.hasNoChildren() || node.getChild(0).isLeaf() ) {
			node.addChild(new Leaf(node, new MBR(polygon), new LeafData(label, polygon)));
		} else {
			Node n = chooseNode(node, polygon);
			Node new_node = addLeaf(n, label, polygon);
			if (new_node != null) { node.addChild(new_node); }
		}
		node.expandMBR(new MBR(polygon));
		if (node.maxChildrenReach(N)) { return split(node); }
		return null;
	}

	public abstract Node split(Node node);


	// Setter
	public void setRoot(Node root) { this.root = root; }


}