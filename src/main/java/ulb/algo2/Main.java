package ulb.algo2;

import java.awt.*;
import java.io.File;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.operation.projection.AzimuthalEquidistant.Abstract;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;

import org.geotools.swing.JMapFrame;
import ulb.algo2.node.LeafData;

import ulb.algo2.rtrees.LinearRectangleTree;
import ulb.algo2.rtrees.QuadraticRectangleTree;
import ulb.algo2.rtrees.RectangleTreeBuilder;
import ulb.algo2.rtrees.AbstractRectangleTree;
import ulb.algo2.rtrees.GuttmanTree;


public class Main {

	public long statTimeToBuildTree(AbstractRectangleTree tree, SimpleFeatureCollection features) {
		long startTime = System.currentTimeMillis();
		RectangleTreeBuilder.buildTree(tree, features);
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		return duration;
		// System.out.println("Tree built in " + duration + " ms");
	}
	
	public long timeToFindLeaf(AbstractRectangleTree tree, double x, double y) {
		long startTime = System.currentTimeMillis();
		tree.find(x, y);
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		return duration;
		// System.out.println("Leaf found in " + duration + " ms");
	}

	public static long statTimeToFind(AbstractRectangleTree tree) {
	    int N = 10000;	
	    long startTime = System.currentTimeMillis();
	    long totalDuration = 0;

	    for(int i=0; i<N; i++) {
		double x = Math.random() * 100000;
		double y = Math.random() * 100000;
		// System.out.println("Searching for (" + x + ", " + y + ")");
		tree.find(x, y);
		totalDuration += (System.currentTimeMillis() - startTime);
	    }
		// tree.find(x, y);
	    long duration = totalDuration / N;
	    return duration;
		// System.out.println("Leaf found in " + duration + " ms");
    }

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
		long duration, startTime, endTime;
		System.out.println("N = " + N);

		System.out.println("Building linear R-Tree...");
		LinearRectangleTree linearTree = new LinearRectangleTree (N);
		duration = RectangleTreeBuilder.buildTree(linearTree, allFeatures);
		System.out.println("Linear R-Tree built in " + duration + " ms");
		
		System.out.println("Building quadratic R-Tree...");
		QuadraticRectangleTree quadraticTree = new QuadraticRectangleTree (N);
		duration = RectangleTreeBuilder.buildTree(quadraticTree, allFeatures);
		System.out.println("Quadratic R-Tree built in " + duration + " ms");

		System.out.println("Building Guttman R-Tree...");
		GuttmanTree guttmanTree = new GuttmanTree (N);
		duration = RectangleTreeBuilder.buildTree(guttmanTree, allFeatures);
		System.out.println("Guttman R-Tree built in " + duration + " ms");
		System.out.println("Done.");


		System.out.println("Searching for a leaf...");
		System.out.println("Linear R-Tree:");
		startTime = System.currentTimeMillis();
		linearTree.find(147306.96, 166818.79);
		duration = (System.currentTimeMillis() - startTime);
		System.out.println("Linear R-Tree found the leaf in " + duration + " ms");
		System.out.println("Quadratic R-Tree:");
		startTime = System.currentTimeMillis();
		quadraticTree.find(147306.96, 166818.79);
		duration = (System.currentTimeMillis() - startTime);
		System.out.println("Quadratic R-Tree found the leaf in " + duration + " ms");
		System.out.println("Guttman R-Tree:");
		startTime = System.currentTimeMillis();
		guttmanTree.find(147306.96, 166818.79);
		duration = (System.currentTimeMillis() - startTime);
		System.out.println("Guttman R-Tree found the leaf in " + duration + " ms");
		System.out.println("Done.");

		System.out.println("mean time to find a leaf in a linear R-Tree of size " + N );
		// duration = linearTree.statTimeToFind();	
		duration = statTimeToFind(linearTree);
		System.out.println("mean time to find a leaf in a linear R-Tree of size " + N + " is " + duration + " ms");
		duration = statTimeToFind(quadraticTree);
		System.out.println("mean time to find a leaf in a quadratic R-Tree of size " + N + " is " + duration + " ms");
		duration = statTimeToFind(guttmanTree);
		System.out.println("mean time to find a leaf in a Guttman R-Tree of size " + N + " is " + duration + " ms");

		/*
		// Print the trees
		System.out.println("Linear R-Tree:");
		linearTree.print();
		System.out.println("");
		System.out.println("Quadratic R-Tree:");
		quadraticTree.print();
		System.out.println("");
		*/

		/*

		// Create the map
		MapContent map = new MapContent();
		map.setTitle("Projet INFO-F203");
		Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Layer layer = new FeatureLayer(featureSource, style);
		map.addLayer(layer);
		ListFeatureCollection collection = new ListFeatureCollection(featureSource.getSchema());
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureSource.getSchema());

		// Find the bounding box of the map
		LeafData result = linearTree.find(152183, 167679);

		// Add the bounding box to the map
		collection.add(featureBuilder.buildFeature(result.getPolygon()));

		Style style2 = SLD.createLineStyle(Color.red, 2.0f);
		Layer layer2 = new FeatureLayer(collection, style2);
		map.addLayer(layer2);

		// Display the map
		MapFrame.showMap(map);

		*/
		System.exit(0);
	}

}
