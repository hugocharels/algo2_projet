package ulb.algo2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;


// To generate a png graph from data added
public class GraphGenerator{
	String title;
	String nameOfFile;
	final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	String colsName;
	String rowsName;

	// Constructor
	public GraphGenerator(String title, String nameOfFile){
		this.title = title;
		this.nameOfFile = nameOfFile;
	}

	public void addLinearValue(double duration, int N) {
		this.dataset.addValue(duration, "Linear", Integer.toString(N));
	}

	public void addQuadraticValue(double duration, int N) {
		this.dataset.addValue(duration, "Quadratic", Integer.toString(N));
	}

	public void addSuspiciousValue(double duration, int N) {
		this.dataset.addValue(duration, "Suspicious", Integer.toString(N));
	}

	public void saveGraph() {
		JFreeChart lineChart = ChartFactory.createLineChart(title, colsName, rowsName, dataset);
		File lineChartFile = new File(this.nameOfFile);
		try { ChartUtils.saveChartAsJPEG(lineChartFile, lineChart, 800, 600);}
		catch (IOException e) { System.err.println("Problem occurred creating chart."); }
	}

	// Setters
	public void setColsName(String colsName) {
		this.colsName = colsName;
	}

	public void setRowsName(String rowsName) {
		this.rowsName = rowsName;
	}


}
