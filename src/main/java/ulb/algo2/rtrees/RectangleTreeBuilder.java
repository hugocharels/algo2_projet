package ulb.algo2.rtrees;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.locationtech.jts.geom.MultiPolygon;
import org.opengis.feature.simple.SimpleFeature;
import ulb.algo2.MBR;
import ulb.algo2.node.Leaf;
import ulb.algo2.node.LeafData;
import ulb.algo2.node.Node;


public class RectangleTreeBuilder {

	private static Leaf createLeaf(Node father, SimpleFeature feature) {
		// TODO get label
		LeafData data = new LeafData( "coucou", (MultiPolygon) feature.getDefaultGeometry());
		// TODO create MBR
		MBR<Double> mbr = new MBR<Double>(0., 0., 0., 0.);
		return new Leaf(father, mbr, data);
	}


	public static void buildTree(AbstractRectangleTree tree, SimpleFeatureCollection features) {
		// TODO implement
		Node root = new Node(null, new MBR<Double>(0., 0., 0., 0.));
		tree.setRoot(root);
		// TODO bien faire le for
		for (SimpleFeature feature : features) {
			Leaf leaf = createLeaf(root, feature);
			root.addChild(leaf);
		}
	}


}
