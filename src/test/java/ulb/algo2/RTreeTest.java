package ulb.algo2;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.jupiter.api.Test;
import ulb.algo2.rtrees.LinearRectangleTree;
import ulb.algo2.rtrees.QuadraticRectangleTree;
import ulb.algo2.rtrees.RectangleTreeBuilder;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RTreeTest {

	@Test
	public void find() throws Exception {

		LinearRectangleTree LTree = new LinearRectangleTree(5);
		QuadraticRectangleTree QTree = new QuadraticRectangleTree(5);
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

		assertFalse(LTree.find(0., 0.));
		assertFalse(QTree.find(0., 0.));

		assertTrue(LTree.find(147306.96, 166818.79));
		assertTrue(QTree.find(147306.96, 166818.79));

	}


}