package ulb.algo2;

import java.util.Random;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.locationtech.jts.geom.Point;

import ulb.algo2.rtrees.*;

public class ResearchGraphGenerator {

	final String prefix = "research_graph_";
	String MapName;

	int maxN;

	ReferencedEnvelope bounds;
	int nbPoints = 5000;
	Point[] points = new Point[nbPoints];
	SimpleFeatureCollection features;

	GraphGenerator graphGenerator;

	// Constructor
	public ResearchGraphGenerator() {}

	public ResearchGraphGenerator(String mapName, SimpleFeatureCollection features) {
		this(mapName, features, 5000);
	}
	public ResearchGraphGenerator(String mapName, SimpleFeatureCollection features, int nbPoints) {
		this.MapName = mapName;
		this.features = features;
		this.bounds = features.getBounds();
		this.nbPoints = nbPoints;
		this.setMaxN();
		this.setPoints();
		this.graphGenerator = new GraphGenerator("Research Duration Time", prefix + mapName + ".png");
		this.graphGenerator.setColsName("Dimension N");
		this.graphGenerator.setRowsName("Duration Time (ms)");
	}

	public void generate(int differentN) {
		for (int i = 1; i <= differentN; i++) {
			final int N = i * this.maxN / differentN;
			AbstractRTree lRTree = this.buildLinearRTree(N);
			AbstractRTree qRTree = this.buildQuadraticRTree(N);
			AbstractRTree sRTree = this.buildSuspiciousRTree(N);

			// Linear
			Execution.start("Linear R-Tree", "searching");
			for (int j = 0; j < nbPoints; j++) { lRTree.find(points[j]); }
			this.graphGenerator.addLinearValue(Execution.end(), N);

			// Quadratic
			Execution.start("Quadratic R-Tree", "searching");
			for (int j = 0; j < nbPoints; j++) { qRTree.find(points[j]); }
			this.graphGenerator.addQuadraticValue(Execution.end(), N);

			// Suspicious
			Execution.start("Suspicious R-Tree", "searching");
			for (int j = 0; j < nbPoints; j++) { sRTree.find(points[j]); }
			this.graphGenerator.addSuspiciousValue(Execution.end(), N);

		}
	}


	private AbstractRTree buildLinearRTree(int N) {
		AbstractRTree rtree = new LinearRTree(N);
		RTreeBuilder.buildTree(rtree, features);
		return rtree;
	}

	private AbstractRTree buildQuadraticRTree(int N) {
		AbstractRTree rtree = new LinearRTree(N);
		RTreeBuilder.buildTree(rtree, features);
		return rtree;
	}

	private AbstractRTree buildSuspiciousRTree(int N) {
		AbstractRTree rtree = new SuspiciousRTree(N);
		RTreeBuilder.buildTree(rtree, features);
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

	public void saveGraph() { this.graphGenerator.saveGraph(); }

}
