package ulb.algo2;


// To show and get execution time (put a function between start and end)
public class Execution {

	private static double startTime;

	public static void start(String name, String action) {
		System.out.println(name + " " + action + " has started");
		startTime = System.currentTimeMillis();
	}

	public static double end() {
		double endTime = System.currentTimeMillis();
		double duration = (endTime - startTime);
		System.out.println("Done.\nExecution time is " + duration + " ms");
		return duration;
	}

}
