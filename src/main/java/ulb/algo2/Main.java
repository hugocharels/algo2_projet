package ulb.algo2;

import java.io.File;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
// import org.geotools.geometry.jts.GeometryBuilder;
// import org.geotools.geometry.jts.ReferencedEnvelope;

// import org.locationtech.jts.geom.Point;
import ulb.algo2.rtrees.LinearRectangleTree;
// import ulb.algo2.rtrees.AbstractRectangleTree;
import ulb.algo2.rtrees.QuadraticRectangleTree;
import ulb.algo2.rtrees.RectangleTreeBuilder;


public class Main {
	public static void main(String[] args) throws Exception {

		// Load the map
		String filename="../algo2_projet/data/sh_statbel_statistical_sectors_31370_20220101.shp/sh_statbel_statistical_sectors_31370_20220101.shp";
		File file = new File(filename);
		if (!file.exists()) throw new RuntimeException("Shapefile does not exist.");
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();
		// les pays
		SimpleFeatureCollection allFeatures = featureSource.getFeatures();
		// store.dispose();
		System.out.println("Building R-Trees...");

		// Build R-Trees
		final int N = 10;
		System.out.println("N = " + N);
		System.out.println("Building linear R-Tree...");
		LinearRectangleTree linearTree = new LinearRectangleTree(N);
		RectangleTreeBuilder.buildTree(linearTree, allFeatures);
		System.out.println("Building quadratic R-Tree...");
		QuadraticRectangleTree quadraticTree = new QuadraticRectangleTree(N);
		RectangleTreeBuilder.buildTree(quadraticTree, allFeatures);
		System.out.println("Done.");
		linearTree.find(147306.96, 166818.79);
		
		System.exit(0);

		}

}
