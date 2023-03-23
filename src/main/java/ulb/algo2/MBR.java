package ulb.algo2;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;


public class MBR {

	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;


	// Constructor
	public MBR(double xMin, double xMax, double yMin, double yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	public MBR(Polygon polygon) {
		Coordinate[] coord = polygon.getEnvelope().getCoordinates();
		this.xMin = coord[0].x;
		this.xMax = coord[2].x;
		this.yMin = coord[0].y;
		this.yMax = coord[2].y;
	}

	// Modifiers
	public void expand(MBR other) {
		xMin = Math.min(xMin, other.xMin);
		xMax = Math.max(xMax, other.xMax);
		yMin = Math.min(yMin, other.yMin);
		yMax = Math.max(yMax, other.yMax);
	}


	// Verifiers
	public boolean contains(double x, double y) {
		return (xMin <= x && x <= xMax && yMin <= y && y <= yMax);
	}

	public boolean contains(MBR other) {
		return (xMin <= other.xMin && other.xMax <= xMax && yMin <= other.yMin && other.yMax <= yMax);
	}

	// Return the percentage of the area of the MBR that is covered by the given MBR
	public double percentageCovered(MBR other) {
		double area = (xMax - xMin) * (yMax - yMin);
		double areaCovered = (Math.min(xMax, other.xMax) - Math.max(xMin, other.xMin)) * (Math.min(yMax, other.yMax) - Math.max(yMin, other.yMin));
		return areaCovered / area;
	}

	// Getters


	public double getArea() {
		return (xMax - xMin) * (yMax - yMin);
	}

	public double getExpansion(MBR other) {
		double xMin = Math.min(this.xMin, other.xMin);
		double xMax = Math.max(this.xMax, other.xMax);
		double yMin = Math.min(this.yMin, other.yMin);
		double yMax = Math.max(this.yMax, other.yMax);
		return (xMax - xMin) * (yMax - yMin) - getArea();
	}

	public double getXMin() { return xMin; }
	public double getXMax() { return xMax; }
	public double getYMin() { return yMin; }
	public double getYMax() { return yMax; }



}
