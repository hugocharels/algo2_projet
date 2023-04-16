package ulb.algo2;

public class Execution {

	private static long startTime;

	public static void start(String name, String action) {
		System.out.println(name + " " + action + " has started");
		startTime = System.currentTimeMillis();
	}

	public static long end() {
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		System.out.println("Done.\nExecution time is " + duration + " ms");
		return duration;
	}

}
