package ulb.algo2.rtrees;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.simple.SimpleFeature;

import ulb.algo2.node.MBR;
import ulb.algo2.node.Node;


public class RTreeBuilder {


	public static int buildTree(AbstractRTree tree, SimpleFeatureCollection features) {
		return buildTree(tree, features, Double.POSITIVE_INFINITY);
	}

	public static int buildTree(AbstractRTree tree, SimpleFeatureCollection features, double maxPolygon) {
		ReferencedEnvelope bounds = features.getBounds();
		Node root = new Node(null, new MBR(bounds.getMinX(), bounds.getMaxX(), bounds.getMinY(), bounds.getMaxY()));
		tree.setRoot(root);
		int polygonAdded = 0;
		try (SimpleFeatureIterator iterator = features.features()) {
			for (SimpleFeature feature = iterator.next(); iterator.hasNext(); feature = iterator.next()) {
				MultiPolygon multiPolygon = (MultiPolygon) feature.getDefaultGeometry();
				if (polygonAdded >= maxPolygon) break;
				for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
					if (polygonAdded >= maxPolygon) break;
					Polygon polygon = (Polygon) multiPolygon.getGeometryN(i);
					polygonAdded++;
					tree.addLeaf(root, feature.getProperty("T_SEC_FR").getValue().toString(), polygon);
				}
			}
		} return polygonAdded;
	}

}
