package ulb.algo2;

import java.io.File;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;

import ulb.algo2.rtrees.LinearRectangleTree;
import ulb.algo2.rtrees.AbstractRectangleTree;
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
		store.dispose();


		// Build R-Trees
		LinearRectangleTree linearTree = new LinearRectangleTree();
		RectangleTreeBuilder.buildTree(linearTree, allFeatures);
		QuadraticRectangleTree quadraticTree = new QuadraticRectangleTree();
		RectangleTreeBuilder.buildTree(quadraticTree, allFeatures);

		// Find the bounding box of the map
		// TODO: find the bounding box of the map


		// Display the map

	}

}
