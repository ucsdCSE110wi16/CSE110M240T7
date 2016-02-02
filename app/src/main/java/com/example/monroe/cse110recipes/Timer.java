
public class Timer {
	
	// Timer Variables
	private int timeRemain;
	private long start;
	private long end;

 	/**
	 * Constructor. Calls init() method to initialize timer variables
	 * @param time - The time to start and begin counting down
	 */
	public Timer(int time) {
		init(time);
	}
	
	/**
	 * Public update method to be called every tick of the app (in the run loop).
	 * Reduce the remaining time every second
	 * @noparams
	 */
	public void update() {
	  // Initialize end time every tick to calculate elapsed time
		end = System.nanoTime() / 1000000;
		// If a second has gone by, reset start and reduce timeRemain by 1
		if (start - end < 1000000) {
			timeRemain--;
			start = System.nanoTime() / 1000000;
		}
	}
	
	/**
	 * Private init used in the constructor to initialize values
	 * @param time - Time to start at and begin counting down from
	 */
	private void init(int time) {
		timeRemain = time;
		start = System.nanoTime() / 1000000;
	}
	
}
