package ulb.algo2.rtrees;

import org.geotools.geometry.jts.GeometryBuilder;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Point;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.function.BiConsumer;

import ulb.algo2.node.MBR;
import ulb.algo2.node.*;


public abstract class AbstractRTree {

	protected Node root;
	protected final int N;

	protected static class AbstractNodePair {
		public AbstractNode n1;
		public AbstractNode n2;

		public AbstractNodePair() {}
		public AbstractNodePair(AbstractNode n1, AbstractNode n2) {
			this.n1 = n1;
			this.n2 = n2;
		}
	}

	// Constructor
	public AbstractRTree(int N) {
		this.N = N;
	}

	// Modifiers
	public Node chooseNode(Node node, Polygon polygon) {
		Node best = null;
		for (AbstractNode n : node.getChildren()) {
			if (best == null) { best = (Node) n; continue; }
			double bestArea = best.getMBR().getArea();
			double nArea = n.getMBR().getArea();
			double bestExpansion = best.getMBR().getExpansion(new MBR(polygon));
			double nExpansion = n.getMBR().getExpansion(new MBR(polygon));
			if (nExpansion < bestExpansion) { best = (Node) n; }
			else if (nExpansion == bestExpansion && nArea < bestArea) { best = (Node) n; }
		}
		return best;
	}

	public Node addLeaf(Node node, String label, Polygon polygon) {
		if (node.hasNoChildren() || node.getChild(0).isLeaf() ) {
			node.addChild(new Leaf(node, new MBR(polygon), new LeafData(label, polygon)));
		} else {
			Node n = chooseNode(node, polygon);
			Node newNode = addLeaf(n, label, polygon);
			if (newNode != null) { node.addChild(newNode); }
		}
		node.expandMBR(new MBR(polygon));
		if (node.maxChildrenReach(N)) { return split(node); }
		return null;
	}

	public Node split(Node node) {
		// Get the two seeds
		AbstractNodePair seeds = this.pickSeeds(node);
		// Create a copy of the children
		List<AbstractNode> children = new java.util.ArrayList<>(node.getChildren());
		node.removeChildren();
		// Sets mbr of nodes
		node.setMBR(seeds.n1.getMBR());
		Node newNode = new Node(node.getFather(), new MBR(seeds.n2.getMBR()));
		this.pickNext(node, newNode, children);
		// If need to create a new root
		if (node.getFather() == null) {
			root = new Node(null, new MBR(node.getMBR()));
			root.addChild(node);
			root.addChild(newNode);
			root.expandMBR(newNode.getMBR());
			node.setFather(root);
			newNode.setFather(root);
		}
		else { node.getFather().addChild(newNode); }
		return newNode;
	}

	// the quadratic pickSeeds
	protected AbstractNodePair pickSeeds(Node node) {
		AbstractNodePair nodes = new AbstractNodePair(node.getChild(0), node.getChild(1));
		double bestExpansion = 0;
		for (int i = 1; i < node.getChildren().size(); i++) {
			for (int j = i + 1; j < node.getChildren().size(); j++) {
				double expansion = node.getChild(i).getMBR().getExpansion(node.getChild(j).getMBR()) - node.getChild(j).getMBR().getArea();
				if (expansion > bestExpansion) {
					nodes.n1 = node.getChild(i);
					nodes.n2 = node.getChild(j);
					bestExpansion = expansion;
				}
			}
		}
		return nodes;
	}

	// the linear pickNext
	protected void pickNext(Node n1, Node n2, List<AbstractNode> children) {
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

	// Setter
	public void setRoot(Node root) { this.root = root; }

	// Search
	public Leaf find(double x, double y) {
		return find(new GeometryBuilder().point(x, y));
	}

	public Leaf find(Point p) {
		Queue<AbstractNode> queue = new LinkedList<AbstractNode>();
		queue.add(root);
		BiConsumer<Node, Queue<AbstractNode>> addChildrenToQueue = (n, q) -> n.getChildren().stream().filter(child -> child.getMBR().contains(p)).forEach(q::add);
		while (!queue.isEmpty()) {
			AbstractNode temp = queue.poll();
			if (temp.isLeaf()) {
				if (((Leaf)temp).getDataPolygon().contains(p)){
					return (Leaf)temp;
				} else { continue; }
			}
			addChildrenToQueue.accept((Node) temp, queue);
		}
		return null;
	}

}