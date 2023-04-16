package ulb.algo2;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.jupiter.api.Test;
import ulb.algo2.rtrees.LinearRTree;
import ulb.algo2.rtrees.SuspiciousRTree;
import ulb.algo2.rtrees.QuadraticRTree;
import ulb.algo2.rtrees.RTreeBuilder;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RTreeTest {

	@Test
	public void find() throws Exception {

		final int N = 20;
		SuspiciousRTree LTree = new SuspiciousRTree(N);
		QuadraticRTree QTree = new QuadraticRTree(N);
		LinearRTree GTree = new LinearRTree(N);
		String filename="../algo2_projet/data/sh_statbel_statistical_sectors_31370_20220101.shp/sh_statbel_statistical_sectors_31370_20220101.shp";

		File file = new File(filename);
		if (!file.exists()) throw new RuntimeException("Shapefile does not exist.");
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();
		// les pays
		SimpleFeatureCollection allFeatures = featureSource.getFeatures();
		store.dispose();


		RTreeBuilder.buildTree(LTree, allFeatures);
		RTreeBuilder.buildTree(QTree, allFeatures);
		RTreeBuilder.buildTree(GTree, allFeatures);

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
