package ulb.algo2.rtrees;

import ulb.algo2.MBR;
import ulb.algo2.node.AbstractNode;
import ulb.algo2.node.Node;


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


	// Split override
	@Override
	public Node split(Node node) {
		AbstractNodePair seeds = pickSeeds(node);

		return null;
	}


}
