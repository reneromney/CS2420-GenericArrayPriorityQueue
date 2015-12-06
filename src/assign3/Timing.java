package assign3;

/**
 * A class for testing the time of MyPriorityQueue's insert and findMin methods.
 * 
 * @author Jordan Hensley, Romney Doria CS 2420 Fall 2015 jHensley Doria
 *         Assignment 3 9/16/2015
 *
 */
public class Timing {

	public static void main(String[] args) {
		// Do 10000 lookups and use the average running time.
		int timesToLoop = 10000;

		// For each problem size n . . .
		for (int n = 100000; n <= 2000000; n += 100000) {

			// Setup of n size for testing
			long startTime, midpointTime, stopTime;

			// Sets up the MyPriorityQueue to the desired size
			MyPriorityQueue<Double> timingPQ = new MyPriorityQueue<Double>();
			for (int i = 0; i < n; i++) {
				// Randomly inserts a number from 0 to the max integer value
				timingPQ.insert(Math.random() * Integer.MAX_VALUE);
			}

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Now, run the test.
			startTime = System.nanoTime();

			// Run the methods for testing
			for (int i = 0; i < timesToLoop; i++) {
				timingPQ.findMin();
				// Double temp = Math.random()*Integer.MAX_VALUE;
				// timingPQ.insert(temp);
				// timingPQ.deleteMin();
			}

			midpointTime = System.nanoTime();

			// Time it takes to run loop
			for (long i = 0; i < timesToLoop; i++) {
				// Double temp = Math.random()*Integer.MAX_VALUE;
				// timingPQ.deleteMin();
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and doing the lookups.
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(n + "\t" + averageTime);
		}

	}
}
