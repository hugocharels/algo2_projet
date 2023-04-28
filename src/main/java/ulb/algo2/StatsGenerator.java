package ulb.algo2;

import java.util.Random;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.locationtech.jts.geom.Point;

import ulb.algo2.rtrees.*;

public class StatsGenerator {

	final String buildingPrefix = "building_graph_";
	final String researchPrefix = "research_graph_";
	String MapName;

	int maxN;

	ReferencedEnvelope bounds;
	int nbPoints = 5000;
	Point[] points = new Point[nbPoints];
	SimpleFeatureCollection features;

	GraphGenerator researchGraphGenerator;
	GraphGenerator buildingGraphGenerator;


	// Constructor
	public StatsGenerator() {}

	public StatsGenerator(String mapName, SimpleFeatureCollection features) {
		this(mapName, features, 5000);
	}
	public StatsGenerator(String mapName, SimpleFeatureCollection features, int nbPoints) {
		this.MapName = mapName;
		this.features = features;
		this.bounds = features.getBounds();
		this.nbPoints = nbPoints;
		this.setMaxN();
		this.setPoints();

		this.buildingGraphGenerator = new GraphGenerator("Building Duration Time", buildingPrefix + mapName + ".png");
		this.buildingGraphGenerator.setColsName("Dimension N");
		this.buildingGraphGenerator.setRowsName("Duration Time (ms)");

		this.researchGraphGenerator = new GraphGenerator("Research Duration Time", researchPrefix + mapName + ".png");
		this.researchGraphGenerator.setColsName("Dimension N");
		this.researchGraphGenerator.setRowsName("Duration Time (ms)");

	}

	public void generate(int differentN) {
		for (int i = 1; i <= differentN; i++) {
			final int N = i * this.maxN / differentN;
			{
				// Linear
				AbstractRTree lRTree = this.buildLinearRTree(N);
				Execution.start("Linear R-Tree", "searching");
				for (int j = 0; j < nbPoints; j++) { lRTree.find(points[j]); }
				this.researchGraphGenerator.addLinearValue(Execution.end(), N);
			}
			{
				// Quadratic
				AbstractRTree qRTree = this.buildQuadraticRTree(N);
				Execution.start("Quadratic R-Tree", "searching");
				for (int j = 0; j < nbPoints; j++) { qRTree.find(points[j]); }
				this.researchGraphGenerator.addQuadraticValue(Execution.end(), N);
			}
			{
				// Suspicious
				AbstractRTree sRTree = this.buildSuspiciousRTree(N);
				Execution.start("Suspicious R-Tree", "searching");
				for (int j = 0; j < nbPoints; j++) { sRTree.find(points[j]); }
				this.researchGraphGenerator.addSuspiciousValue(Execution.end(), N);
			}
		}
	}


	private AbstractRTree buildLinearRTree(int N) {
		AbstractRTree rtree = new LinearRTree(N);
		Execution.start("Linear R-Tree", "building");
		RTreeBuilder.buildTree(rtree, features);
		this.buildingGraphGenerator.addLinearValue(Execution.end(), N);
		return rtree;
	}

	private AbstractRTree buildQuadraticRTree(int N) {
		AbstractRTree rtree = new LinearRTree(N);
		Execution.start("Quadratic R-Tree", "building");
		RTreeBuilder.buildTree(rtree, features);
		this.buildingGraphGenerator.addQuadraticValue(Execution.end(), N);
		return rtree;
	}

	private AbstractRTree buildSuspiciousRTree(int N) {
		AbstractRTree rtree = new SuspiciousRTree(N);
		Execution.start("Suspicious R-Tree", "building");
		RTreeBuilder.buildTree(rtree, features);
		this.buildingGraphGenerator.addSuspiciousValue(Execution.end(), N);
		return rtree;
	}

	private void setMaxN() {
		AbstractRTree rtree = new LinearRTree(10000);
		this.maxN = RTreeBuilder.buildTree(rtree, features);
	}

	private void setPoints() {
		Random r = new Random();
		for (int i = 0; i < nbPoints; i++) {
			points[i] = new GeometryBuilder().point(r.nextInt((int) bounds.getMinX(), (int) bounds.getMaxX()),
					r.nextInt((int) bounds.getMinY(), (int) bounds.getMaxY()));
		}
	}

	public void saveGraph() {
		this.researchGraphGenerator.saveGraph();
		this.buildingGraphGenerator.saveGraph();
	}

}
