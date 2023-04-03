package ulb.algo2;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.jupiter.api.Test;
import ulb.algo2.rtrees.LinearRectangleTree;
import ulb.algo2.rtrees.QuadraticRectangleTree;
import ulb.algo2.rtrees.RectangleTreeBuilder;
import ulb.algo2.rtrees.GuttmanTree;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RTreeTest {

	@Test
	public void find() throws Exception {

		final int N = 20;
		LinearRectangleTree LTree = new LinearRectangleTree(N);
		QuadraticRectangleTree QTree = new QuadraticRectangleTree(N);
		GuttmanTree GTree = new GuttmanTree(N);
		String filename="../algo2_projet/data/sh_statbel_statistical_sectors_31370_20220101.shp/sh_statbel_statistical_sectors_31370_20220101.shp";

		File file = new File(filename);
		if (!file.exists()) throw new RuntimeException("Shapefile does not exist.");
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();
		// les pays
		SimpleFeatureCollection allFeatures = featureSource.getFeatures();
		store.dispose();


		RectangleTreeBuilder.buildTree(LTree, allFeatures);
		RectangleTreeBuilder.buildTree(QTree, allFeatures);
		RectangleTreeBuilder.buildTree(GTree, allFeatures);	

		// String res =" Leaf : NEPTUNE (AVENUE) I";
		assertTrue(LTree.find(147306.96, 166818.79) != null);
		assertTrue(QTree.find(147306.96, 166818.79) != null);
		assertTrue(GTree.find(147306.96, 166818.79) != null);
		assertTrue(LTree.find(0., 0.) == null);
		assertTrue(QTree.find(0., 0.) == null);
		assertTrue(GTree.find(0., 0.) == null);
		// assertFalse(LTree.find(0., 0.)==null);
		// assertFalse(QTree.find(0., 0.));

		// assertTrue(LTree.find(152183, 167679));
		// assertTrue(QTree.find(147306.96, 166818.79));

	}


}
