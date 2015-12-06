package assign3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * 
 * @author Jordan Hensley, Romney Doria
 * CS 2420 Fall 2015
 * jHensley
 * Doria
 * Assignment 3
 * 9/16/2015
 * 
 * A class for testing MyPriorityQueue functionality
 *
 */
public class MyPriorityQueueTests {

	@Test
	public void testInsert() {

		// Test for empty set
		MyPriorityQueue<Integer> testQueue = new MyPriorityQueue<Integer>();
		assertEquals(0, testQueue.size());

		// Test adding duplicates
		testQueue.insert(1);
		testQueue.insert(2);
		testQueue.insert(3);
		testQueue.insert(3);
		testQueue.insert(3);
		testQueue.insert(3);
		testQueue.insert(0);
		assertEquals(7, testQueue.size());

		// Insure the minimum value is correct
		assertEquals(0, testQueue.findMin(), 0);
		testQueue.insert(9);
		testQueue.insert(4);
		testQueue.insert(5);
		testQueue.insert(7);
		testQueue.insert(9);
		testQueue.insert(10);
		testQueue.insert(-5);
		testQueue.insert(-3);

		// Test using negative values
		assertEquals(-5, testQueue.findMin(), 0);
		assertEquals(15, testQueue.size(), 0);
		testQueue.deleteMin();

		// Test deleteMin method
		assertEquals(-3, testQueue.findMin(), 0);
		assertEquals(14, testQueue.size());
	}
	
	@Test
	public void testInsertAll(){
		
		//Create an empty list
		ArrayList<Integer> testEmpty = new ArrayList<Integer>();
		MyPriorityQueue<Integer> myPQ = new MyPriorityQueue<Integer>();
		myPQ.insertAll(testEmpty);
		
		//Assert expected results of adding an empty list
		assertEquals(0, myPQ.size(), 0);
		assertNull(myPQ.findMin());
		
		//Test a list that has items in it
		MyPriorityQueue<Double> testAll = new MyPriorityQueue<Double>();
		ArrayList<Double> testList = new ArrayList<Double>();
		testList.add(232.3);
		testList.add(435.0);
		testList.add(232.1);
		
		testAll.insertAll(testList);
		assertEquals(3, testAll.size());
		assertEquals(232.1, testAll.findMin(), 0);
	}

	@Test
	public void testGeneric() {

		// Test generics using string object
		MyPriorityQueue<String> myQ = new MyPriorityQueue<String>();
		myQ.insert("hello");
		myQ.insert("word");
		myQ.insert("Romney");
		myQ.insert("A String is used");

		// Insure correct sorting takes place
		assertEquals(4, myQ.size());
		assertEquals("A String is used", myQ.findMin());

	}

	// Make sure that ClassCastException is thrown if an object that doesn't
	// implement comparable is passed without a comparator.
	@Test(expected = ClassCastException.class)
	public void testClassCastException() {
		MyPriorityQueue<Point> myQ = new MyPriorityQueue<Point>();
		myQ.insert(new Point(2, 3));
		myQ.insert(new Point(23, 2));
	}

	@Test
	public void testClear() {

		MyPriorityQueue<Double> myQueue = new MyPriorityQueue<Double>();
		myQueue.insert(23.20);
		myQueue.insert(203.2);
		myQueue.insert(3042385.0);

		// Test the clear method
		assertFalse(myQueue.isEmpty());
		myQueue.clear();
		assertTrue(myQueue.isEmpty());
		assertNull(myQueue.deleteMin());
	}

	@Test
	public void testBounds() {

		// Tests an empty priority queue
		MyPriorityQueue<String> myQueue = new MyPriorityQueue<String>();
		assertNull(myQueue.findMin());
		assertNull(myQueue.deleteMin());
		assertTrue(myQueue.isEmpty());

		// Assures the correct behavior with an iterator on an empty priority
		// queue
		Iterator<String> itr = myQueue.iterator();
		assertFalse(itr.hasNext());

	}

	@Test
	public void testIterator() {

		// Insert elements for testing
		MyPriorityQueue<Integer> testSet = new MyPriorityQueue<Integer>();
		testSet.insert(5);
		testSet.insert(2);
		testSet.insert(9);
		testSet.insert(10);

		Iterator<Integer> myItr = testSet.iterator();

		assertTrue(myItr.hasNext());
		myItr.next();

		// Test remove method
		myItr.remove();
		assertEquals(3, testSet.size());
		myItr.next();
		myItr.next();
		myItr.next();

		// Should now remove the last value
		myItr.remove();
		assertEquals(2, testSet.size());
		assertEquals(5, testSet.findMin(), 0);

		assertFalse(myItr.hasNext());
	}

	@Test
	public void testPQDouble() {

		// Test a primitive type for priority queue.
		MyPriorityQueue<Double> testQueue = new MyPriorityQueue<Double>();
		testQueue.insert((1.337));
		testQueue.insert((3.149));
		testQueue.insert((9.2785));
		testQueue.insert((4.1585));
		testQueue.insert((3.22));
		testQueue.insert((1.1527));

		assertEquals(6, testQueue.size());
		assertEquals(1.1527, testQueue.findMin(), 0);
		assertEquals(1.1527, testQueue.deleteMin(), 0);
		assertEquals(1.337, testQueue.findMin(), 0);
	}

	@Test
	public void testPQComparatorTest() {

		// Create a priority queue with an object that does not implement
		// comparable
		MyPriorityQueue<Point> testQueue = new MyPriorityQueue<Point>(new PointCompY());
		Point p1 = new Point(5, 3);
		Point p2 = new Point(1, 9);
		Point p3 = new Point(2, 4);
		Point p4 = new Point(1, 1);
		testQueue.insert(p1);
		testQueue.insert(p2);
		testQueue.insert(p3);
		testQueue.insert(p4);

		// Assure correct results
		assertEquals(4, testQueue.size());
		assertEquals(p4, testQueue.deleteMin());
		assertEquals(p1, testQueue.findMin());
		assertEquals(p1, testQueue.deleteMin());
		assertEquals(2, testQueue.size());

	}

	class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * 
	 * @author Jordan Hensley, Romney Doria
	 * 
	 *         A comparator to be used for means of testing MyPriorityQueue
	 *
	 */
	class PointCompY implements Comparator<Point> {

		@Override
		public int compare(Point o1, Point o2) {
			return o1.y - o2.y;
		}

	}

	@Test(expected = IllegalStateException.class)
	public void testIllegalArgument() {

		// Assures that you can't remove an element that is not there
		MyPriorityQueue<String> test = new MyPriorityQueue<String>();
		Iterator<String> itr = test.iterator();
		itr.remove();
	}

	@Test(expected = NoSuchElementException.class)
	public void testNoSuchElement() {

		// Assures NoSuchElement is returned if next() is called on an iterator
		// that in fact does not have a next element
		MyPriorityQueue<int[]> test = new MyPriorityQueue<int[]>();
		Iterator<int[]> itr = test.iterator();
		itr.next();
	}
}
