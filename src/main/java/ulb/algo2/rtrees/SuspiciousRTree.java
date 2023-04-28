package ulb.algo2.rtrees;

import ulb.algo2.node.AbstractNode;
import ulb.algo2.node.Node;

import java.util.Comparator;
import java.util.List;


public class SuspiciousRTree extends AbstractRTree {

	// Constructor
	public SuspiciousRTree(int N) { super(N); }

	@Override
	protected AbstractNodePair pickSeeds(Node node) {
		// take the 4 MBR at each corner and call the quadratic pickseeds on them
		List<AbstractNode> children = new java.util.ArrayList<>(node.getChildren());
		Node newNode = new Node(null, null);
		newNode.addChild(children.stream().min(Comparator.comparingDouble(n -> n.getMBR().getXMin())).orElse(null));
		newNode.addChild(children.stream().min(Comparator.comparingDouble(n -> n.getMBR().getYMin())).orElse(null));
		newNode.addChild(children.stream().max(Comparator.comparingDouble(n -> n.getMBR().getXMax())).orElse(null));
		newNode.addChild(children.stream().max(Comparator.comparingDouble(n -> n.getMBR().getYMax())).orElse(null));
		return super.pickSeeds(newNode);
	}

}
