package ulb.algo2;

import java.io.File;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.GeometryBuilder;
import org.locationtech.jts.geom.Point;

import ulb.algo2.rtrees.*;
import ulb.algo2.rtrees.AbstractRTree;


public class Main {



	public long timeToFindLeaf(AbstractRTree tree, double x, double y) {
		long startTime = System.currentTimeMillis();
		tree.find(x, y);
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		return duration;
		// System.out.println("Leaf found in " + duration + " ms");
	}

	// public static long statTimeToFind(AbstractRTree tree, int numberOfSearches) {
	public static float statTimeToFind(AbstractRTree tree, double[] posX, double[] posY) {
	    long totalDuration = 0;
	    long startTime;
	    startTime = System.nanoTime();
	    // startTime = System.currentTimeMillis();
	    for(int i=0; i<posX.length; i++) {
		tree.find(posX[i], posY[i]);
	    }
		// totalDuration += (System.currentTimeMillis() - startTime);
		totalDuration += (System.nanoTime() - startTime);
	    long meanDuration = totalDuration / posX.length;
	    long duration = meanDuration / 1000;
	    long dec = meanDuration - duration * 1000;
	    float durationFloat = (float)duration + (float)dec / 1000;
	    // float durationFloat = (float)duration;
	    // float durationFloat = (float)meanDuration/1000 + (float)(meanDuration-(meanDurat  io n/)) / 1000;
	    // System.out.println("Leaf found in " + durationFloat + " ms");
	    // long duration = totalDuration;
	    return durationFloat;
    }

	public static void main(String[] args) throws Throwable {

		// Load the map
		String filename="../algo2_projet/data/sh_statbel_statistical_sectors_31370_20220101.shp/sh_statbel_statistical_sectors_31370_20220101.shp";
		File file = new File(filename);
		if (!file.exists()) throw new RuntimeException("Shapefile does not exist.");
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();
		// les pays
		SimpleFeatureCollection allFeatures = featureSource.getFeatures();
		// store.dispose();


		// Build R-Trees

		final int N = 1000;
		System.out.println("With a N = " + N);

		QuadraticRTree quadraticRTree = new QuadraticRTree(N);
		LinearRTree linearRTree = new LinearRTree(N);
		SuspiciousRTree suspiciousRTree = new SuspiciousRTree(N);
		System.out.println("---------- Trees building ----------");
		Execution.start("Quadratic R-Tree", "building");
		RTreeBuilder.buildTree(quadraticRTree, allFeatures);
		Execution.end();
		System.out.println("------------------------------------");
		Execution.start("Linear R-Tree", "building");
		RTreeBuilder.buildTree(linearRTree, allFeatures);
		Execution.end();
		System.out.println("------------------------------------");
		Execution.start("Suspicious R-Tree", "building");
		RTreeBuilder.buildTree(suspiciousRTree, allFeatures);
		Execution.end();
		System.out.println("------------------------------------");


		// Trees research

		final int POINTS = 1000;
		final Point[] points = new Point[POINTS];
		for (int i=0; i<POINTS; i++) {
			points[i] = new GeometryBuilder().point(Math.random() * 100, Math.random() * 100);
		}

		System.out.println("---------- Trees research ----------");
		Execution.start("Quadratic R-Tree", "research");
		for (int i=0; i<POINTS; i++) { quadraticRTree.find(points[i]); }
		Execution.end();
		System.out.println("------------------------------------");
		Execution.start("Linear R-Tree", "research");
		for (int i=0; i<POINTS; i++) { linearRTree.find(points[i]); }
		Execution.end();
		System.out.println("------------------------------------");
		Execution.start("Suspicious R-Tree", "research");
		for (int i=0; i<POINTS; i++) { suspiciousRTree.find(points[i]); }
		Execution.end();
		System.out.println("------------------------------------");



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
