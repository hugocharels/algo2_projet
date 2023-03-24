package ulb.algo2.rtrees;

import ulb.algo2.MBR;
import ulb.algo2.node.AbstractNode;
import ulb.algo2.node.Node;

import java.util.List;


public class QuadraticRectangleTree extends AbstractRectangleTree {

	// Constructor
	public QuadraticRectangleTree(int N) { super(N); }


	private static class AbstractNodePair {
		public AbstractNode n1;
		public AbstractNode n2;

		public AbstractNodePair(AbstractNode n1, AbstractNode n2) {
			this.n1 = n1;
			this.n2 = n2;
		}
	}

	private AbstractNodePair pickSeeds(Node node) {
		AbstractNodePair nodes = new AbstractNodePair(node.getChild(0), node.getChild(1));
		MBR biggest = nodes.n1.getMBR().getUnion(nodes.n2.getMBR());
		for (int i = 1; i < node.getChildren().size(); i++) {
			for (int j = i + 1; j < node.getChildren().size(); j++) {
				if (i==j) { continue; }
				MBR union = node.getChild(i).getMBR().getUnion(node.getChild(j).getMBR());
				if (union.getArea() > biggest.getArea()) {
					nodes.n1 = node.getChild(i);
					nodes.n2 = node.getChild(j);
					biggest = union;
				}
			}
		}
		return nodes;
	}

	public void pickNext(Node n1, Node n2, List<AbstractNode> children) {
		for (AbstractNode child : children) {
			if (n1.getMBR().getExpansion(child.getMBR()) < n2.getMBR().getExpansion(child.getMBR())) {
				n1.addChild(child);
				n1.expandMBR(child.getMBR());
			} else {
				n2.addChild(child);
				n2.expandMBR(child.getMBR());
			}
		}
	}

	@Override
	public Node split(Node node) {
		// Get the two seeds
		AbstractNodePair seeds = this.pickSeeds(node);
		// Create a copy of the children
		List<AbstractNode> children = new java.util.ArrayList<>(node.getChildren());
		node.removeChildren();
		// Sets mbr of nodes
		node.setMBR(seeds.n1.getMBR());
		Node new_node = new Node(node.getFather(), new MBR(seeds.n2.getMBR()));
		this.pickNext(node, new_node, children);
		return new_node;
	}
}
