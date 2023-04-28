package ulb.algo2;

import java.io.File;
import java.util.Queue;
import java.util.Random;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.GeometryBuilder;
import org.locationtech.jts.geom.Point;

import ulb.algo2.rtrees.*;

public class Main {


	public static void main(String[] args) throws Throwable {



		graphGen();
	}


	public static void graphGen() throws Throwable {

		/*
		{
			// Generate the research graph for the belgium map
			System.out.println("Generating the research graph for the belgium map...");
			StatsGenerator researchGraphGenerator = new StatsGenerator("belgium", getBelgiumFeatures(), 1000);
			researchGraphGenerator.generate(10);
			researchGraphGenerator.saveGraph();
		}
		{
			// Generate the research graph for the france map
			System.out.println("Generating the research graph for the france map...");
			StatsGenerator researchGraphGenerator = new StatsGenerator("france", getFranceFeatures(), 1000);
			researchGraphGenerator.generate(10);
			researchGraphGenerator.saveGraph();
		}
		{
			// Generate the research graph for the world map
			System.out.println("Generating the research graph for the world map...");
			StatsGenerator researchGraphGenerator = new StatsGenerator("world", getWorldFeatures(), 1000);
			researchGraphGenerator.generate(10);
			researchGraphGenerator.saveGraph();
		}*/
		{
			// Generate the research graph for the USA map
			System.out.println("Generating the research graph for the USA map...");
			StatsGenerator researchGraphGenerator = new StatsGenerator("USA", getUSAFeatures(), 1000);
			researchGraphGenerator.generate(10);
			researchGraphGenerator.saveGraph();
		}

		System.exit(0);
	}



	private static SimpleFeatureCollection getBelgiumFeatures() throws Throwable {
		String filename="../algo2_projet/data/sh_statbel_statistical_sectors_31370_20220101.shp/sh_statbel_statistical_sectors_31370_20220101.shp";
		return getFeatures(filename);
	}

	private static SimpleFeatureCollection getFranceFeatures() throws Throwable {
		String filename="../algo2_projet/data/communes-20220101-shp/communes-20220101.shp";
		return getFeatures(filename);
	}

	private static SimpleFeatureCollection getWorldFeatures() throws Throwable {
		String filename="../algo2_projet/data/WB_countries_Admin0_10m/WB_countries_Admin0_10m.shp";
		return getFeatures(filename);
	}

	public static SimpleFeatureCollection getUSAFeatures() throws Throwable {
		String filename="../algo2_projet/data/StatPlanet_USA_County/map/map.shp";
		return getFeatures(filename);
	}

	private static SimpleFeatureCollection getFeatures(String fileName) throws Throwable {
		File file = new File(fileName);
		if (!file.exists()) throw new RuntimeException("Shapefile does not exist.");
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();
		return featureSource.getFeatures();
	}


	public static void main3(String[] args) throws Throwable {

		// Load the map
		String filename="../algo2_projet/data/sh_statbel_statistical_sectors_31370_20220101.shp/sh_statbel_statistical_sectors_31370_20220101.shp";
		File file = new File(filename);
		if (!file.exists()) throw new RuntimeException("Shapefile does not exist.");
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();
		// les pays
		SimpleFeatureCollection allFeatures = featureSource.getFeatures();
		// store.dispose();


		for ( int N : new int[]{ 2, 10, 50, 100, 200, 500, 1000}) {
		//for ( int N : new int[]{ 500, 1000, 2000, 5000, 10000 }) {
		//for ( int N : new int[]{ 10000, 50000, 100000, 500000, 1000000 }) {

			System.out.println("++++++++++++++++++++++++++++++++++++");

			// Build R-Trees

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

			final int POINTS = 5000;
			final Point[] points = new Point[POINTS];
			Random random = new Random();
			final double min = 20000;
			final double max = 300000;
			for (int i = 0; i < POINTS; i++) {
				points[i] = new GeometryBuilder().point(min + random.nextDouble() * (max - min), min + random.nextDouble() * (max - min));
			}

			System.out.println("---------- Trees research ----------");
			Execution.start("Quadratic R-Tree", "research");
			for (int i = 0; i < POINTS; i++) {
				quadraticRTree.find(points[i]);
			}
			Execution.end();
			System.out.println("------------------------------------");
			Execution.start("Linear R-Tree", "research");
			for (int i = 0; i < POINTS; i++) {
				linearRTree.find(points[i]);
			}
			Execution.end();
			System.out.println("------------------------------------");
			Execution.start("Suspicious R-Tree", "research");
			for (int i = 0; i < POINTS; i++) {
				suspiciousRTree.find(points[i]);
			}
			Execution.end();
			System.out.println("------------------------------------");

		}


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
