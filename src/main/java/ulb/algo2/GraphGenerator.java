
package ulb.algo2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.GeometryBuilder;
import org.locationtech.jts.geom.Point;
import org.opengis.filter.temporal.During;

import ulb.algo2.rtrees.*;

public class GraphGenerator{
	String title;
	Boolean quadratic;
	AbstractRTree rtree;
	SimpleFeatureCollection allFeatures;
	int nMax = 1000;
	static int nbPoints = 1000;
	static Point[] points = new Point[nbPoints];
	String filename;
	String nameOfFile;
	JFreeChart lineChart;

	// Constructor
	public GraphGenerator(String title){
	this.title = title;
	}

	public void init(String filename, Boolean quadratic) throws Throwable {
		this.filename = filename;
		this.quadratic = quadratic;
		this.decodeShapefile();
		this.generateTree(2);
		this.generateNameOfFile();
		this.nMax = RTreeBuilder.buildTree(this.rtree, this.allFeatures);
	}

	public static void setPoints() {
		final double min = 20000;
		final double max = 300000;
		Random random = new Random();
		for (int i = 0; i < nbPoints; i++) {
			points[i] = new GeometryBuilder().point(min + random.nextDouble() * (max - min), min + random.nextDouble() * (max - min));
		}
	}

	public void generateGraph(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		final int differentN = 50;
		for (int i = 1; i<differentN + 1; i++) {
			// for ( int i : new int[]{ 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000, 1000000}) {
			//for ( int i : new int[]{ 2, 10, 50, 100, 200, 500, 1000}) {
			final int N = i * this.nMax / differentN;
			generateTree(N);
			this.nMax = RTreeBuilder.buildTree(this.rtree, this.allFeatures);
			long startTime = System.currentTimeMillis();
			for (int x = 0; x < 5; x++) {
				for (int j = 0; j < nbPoints; j++) {
					this.rtree.find(points[j]);
				}
			}
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime) / 5;
			System.out.println("Average time for " + N + " dimensions: " + duration);
			dataset.addValue(duration, "Average time", Integer.toString(N));
		}
		// this.lineChart = ChartFactory.createLineChart("Average time to find a point in a RTree", "Dimensions", "Time (ns)", dataset);
		this.lineChart = ChartFactory.createLineChart(title, "Dimensions", "Time (ms)", dataset);
		this.saveGraph();
	}

	// Privates
	private void decodeShapefile() throws Throwable{
		File file = new File(filename);
		if (!file.exists()) throw new RuntimeException("Shapefile does not exist.");
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();
		SimpleFeatureCollection allFeatures = featureSource.getFeatures();
		this.allFeatures = allFeatures;
	}

	private void generateTree(int dimension){
		if (this.quadratic) this.rtree = new QuadraticRTree(dimension);
		else this.rtree = new LinearRTree(dimension);
	}

	private void generateNameOfFile(){
		if (this.quadratic) this.nameOfFile = "graph_quadratique_";
		else this.nameOfFile = "graph_lineaire_";
		String[] tmp = this.title.split(" ");
		this.nameOfFile += (tmp[0] + ".png");
		// this.nameOfFile = this.nameOfFile + this.filename;
	}

	private void saveGraph() {
		File lineChartFile = new File(this.nameOfFile);
		try {
			ChartUtils.saveChartAsJPEG(lineChartFile, lineChart, 800, 600);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}

}
