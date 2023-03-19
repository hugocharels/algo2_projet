package ulb.algo2.rtrees;

import ulb.algo2.MBR;
import ulb.algo2.node.*;

// en attendant
class Polygon{}
class Label{}


public abstract class RectangleTree {

	protected Node<MBR<Integer>> root;


	public Node<MBR<Integer>> chooseNode(Node<MBR<Integer>> node, Polygon polygon) {
		// TODO implement
		return null;
	}

	public void addLeaf(Node<MBR<Integer>> node, Label label, Polygon polygon) {
		// TODO implement
	}

	public abstract Node<MBR<Integer>> split(Node<MBR<Integer>> node);

}