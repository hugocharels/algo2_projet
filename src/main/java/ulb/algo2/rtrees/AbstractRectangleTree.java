package ulb.algo2.rtrees;

import org.locationtech.jts.geom.Polygon;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

import ulb.algo2.MBR;
import ulb.algo2.node.*;


public abstract class AbstractRectangleTree {

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

	protected AbstractNodePair pickSeeds(Node node) {
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

	private void pickNext(Node n1, Node n2, List<AbstractNode> children) {
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


	// Display
	public void print() {
		List<Node> nodeList = new java.util.ArrayList<>();
		Queue<AbstractNode> queue = new LinkedList<AbstractNode>();
		queue.add(root);
		while (!queue.isEmpty()) {
			AbstractNode temp = queue.poll();
			if (temp.isLeaf()) { continue; }
			nodeList.add((Node)temp);
			queue.addAll(((Node) temp).getChildren());
		}
		for (Node n : nodeList) {
			System.out.println(n.toString());
			if (!n.hasNoChildren() && n.getChild(0).isLeaf()) {
				for (AbstractNode leaf : n.getChildren()) {
					System.out.println("\t" + leaf.toString());
				}
			}
		}

	}


}