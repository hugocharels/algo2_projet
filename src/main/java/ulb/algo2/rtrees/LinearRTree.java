package ulb.algo2.rtrees;

// import ulb.algo2.node.MBR;
import ulb.algo2.node.AbstractNode;
import ulb.algo2.node.Node;

import java.util.List;
import java.util.Comparator;


public class LinearRTree extends AbstractRTree {

	// Constructor
	public LinearRTree(int N) { super(N); }

	@Override
	protected AbstractNodePair pickSeeds(Node node) {
		List<AbstractNode> children = new java.util.ArrayList<>(node.getChildren());

		AbstractNode nodeMinOfMaxX = children.stream().min(Comparator.comparingDouble(n -> n.getMBR().getXMax())).orElse(null);
		AbstractNode nodeMaxOfMinX = children.stream().max(Comparator.comparingDouble(n -> n.getMBR().getXMin())).orElse(null);
		AbstractNode nodeMinOfMaxY = children.stream().min(Comparator.comparingDouble(n -> n.getMBR().getYMax())).orElse(null);
		AbstractNode nodeMaxOfMinY = children.stream().max(Comparator.comparingDouble(n -> n.getMBR().getYMin())).orElse(null);

		double smallDistX = nodeMaxOfMinX.getMBR().getXMax() - nodeMinOfMaxX.getMBR().getXMin();
		double bigDistX = nodeMaxOfMinX.getMBR().getXMin() - nodeMinOfMaxX.getMBR().getXMax();
		double smallDistY = nodeMaxOfMinY.getMBR().getYMax() - nodeMinOfMaxY.getMBR().getYMin();
		double bigDistY = nodeMaxOfMinY.getMBR().getYMin() - nodeMinOfMaxY.getMBR().getYMax();

		if (smallDistX/bigDistX > smallDistY/bigDistY) 
			return new AbstractNodePair(nodeMinOfMaxX, nodeMaxOfMinX);
		else
			return new AbstractNodePair(nodeMinOfMaxY, nodeMaxOfMinY);

	}
}
