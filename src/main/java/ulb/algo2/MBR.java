package ulb.algo2;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;


public class MBR {

	private final double xMin;
	private final double xMax;
	private final double yMin;
	private final double yMax;


	// Constructor
	public MBR(double xMin, double xMax, double yMin, double yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	public MBR(MultiPolygon polygon) {
		Coordinate[] coord = polygon.getEnvelope().getCoordinates();
		this.xMin = coord[0].x;
		this.xMax = coord[2].x;
		this.yMin = coord[0].y;
		this.yMax = coord[2].y;
	}

	// Verifiers
	public boolean contains(double x, double y) {
		return (xMin <= x && x <= xMax && yMin <= y && y <= yMax);
	}

	public boolean contains(MBR mbr) {
		return (xMin <= mbr.xMin && mbr.xMax <= xMax && yMin <= mbr.yMin && mbr.yMax <= yMax);
	}

	// Return the percentage of the area of the MBR that is covered by the given MBR
	public double percentageCovered(MBR mbr) {
		double area = (xMax - xMin) * (yMax - yMin);
		double areaCovered = (Math.min(xMax, mbr.xMax) - Math.max(xMin, mbr.xMin)) * (Math.min(yMax, mbr.yMax) - Math.max(yMin, mbr.yMin));
		return areaCovered / area;
	}

	// Getters
	public double getXMin() { return xMin; }
	public double getXMax() { return xMax; }
	public double getYMin() { return yMin; }
	public double getYMax() { return yMax; }



}
