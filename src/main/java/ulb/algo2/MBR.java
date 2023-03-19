package ulb.algo2;

public class MBR<T extends Number> {

	private final T xMin;
	private final T xMax;
	private final T yMin;
	private final T yMax;


	// Constructor
	public MBR(T xMin, T xMax, T yMin, T yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	// Verifiers
	public boolean contains(T x, T y) {
		return (xMin.doubleValue() <= x.doubleValue() && x.doubleValue() <= xMax.doubleValue() && yMin.doubleValue() <= y.doubleValue() && y.doubleValue() <= yMax.doubleValue());
	}

	public boolean contains(MBR<T> mbr) {
		return (xMin.doubleValue() <= mbr.xMin.doubleValue() && mbr.xMax.doubleValue() <= xMax.doubleValue() && yMin.doubleValue() <= mbr.yMin.doubleValue() && mbr.yMax.doubleValue() <= yMax.doubleValue());
	}

	// Return the percentage of the area of the MBR that is covered by the given MBR
	public double percentageCovered(MBR<T> mbr) {
		double area = (xMax.doubleValue() - xMin.doubleValue()) * (yMax.doubleValue() - yMin.doubleValue());
		double areaCovered = (Math.min(xMax.doubleValue(), mbr.xMax.doubleValue()) - Math.max(xMin.doubleValue(), mbr.xMin.doubleValue())) * (Math.min(yMax.doubleValue(), mbr.yMax.doubleValue()) - Math.max(yMin.doubleValue(), mbr.yMin.doubleValue()));
		return areaCovered / area;
	}

	// Getters
	public T getXMin() { return xMin; }
	public T getXMax() { return xMax; }
	public T getYMin() { return yMin; }
	public T getYMax() { return yMax; }



}
