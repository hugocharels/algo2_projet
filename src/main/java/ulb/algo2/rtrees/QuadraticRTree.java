package ulb.algo2.rtrees;


import ulb.algo2.node.AbstractNode;
import ulb.algo2.node.Node;

import java.util.List;

public class QuadraticRTree extends AbstractRTree {

	// Constructor
	public QuadraticRTree(int N) { super(N); }


	@Override
	protected void pickNext(Node n1, Node n2, List<AbstractNode> children) {
		while(!children.isEmpty()) {
			AbstractNode best = null;
			Node father = null;
			double maxDelta = -1;
			for (AbstractNode node : children) {
				double n1Expansion = n1.getMBR().getExpansion(node.getMBR()) - node.getMBR().getArea();
				double n2Expansion = n2.getMBR().getExpansion(node.getMBR()) - node.getMBR().getArea();
				double delta = Math.abs(n1Expansion - n2Expansion);
				if (delta > maxDelta) {
					maxDelta = delta;
					best = node;
					if (n1Expansion < n2Expansion) { father = n1; }
					else { father = n2; }
				}
			}
			children.remove(best);
			father.addChild(best);
			father.expandMBR(best.getMBR());
		}
	}

}
