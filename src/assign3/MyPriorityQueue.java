package assign3;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Jordan Hensley, Romney Doria CS 2420 Fall 2015 jHensley Doria
 *         Assignment 3 9/16/2015
 *
 * @param Generic
 *            <E>
 * 
 *            Creates a PriorityQueue that takes a generic type E and gives
 *            functionality of inserting, finding size, inserting a collection,
 *            finding the minimum element, deleting that minimum element,
 *            clearing the entire priority queue and the ability to create an
 *            iterator for the priority queue.
 */
public class MyPriorityQueue<E> implements PriorityQueue<E> {

	// Instance variables to hold details of the Priority list
	private E[] holderArray;
	private int size;
	private Comparator<? super E> comp = null;

	/**
	 * A constructor for creating the new MyPriorityQueue that assumes the
	 * generic type that has been passed in implements comparable
	 */
	@SuppressWarnings("unchecked")
	public MyPriorityQueue() {
		size = 0;
		holderArray = (E[]) new Object[100];
	}

	/**
	 * A constructor for MyPriorityQueue that allows for the user to pass in a
	 * comparator to be used for the sorting
	 */
	@SuppressWarnings("unchecked")
	public MyPriorityQueue(Comparator<? super E> comp) {
		holderArray = (E[]) new Object[100];
		size = 0;
		this.comp = comp;
	}

	/**
	 * A compare method that uses the comparator method that was passed in, or
	 * if one is not passed in uses the natural compareTo ordering. Returns -1
	 * if the item is less than what its compared to, 0 if it is equal to it, or
	 * 1 if it is greater than it
	 * 
	 * @param holderObject
	 * @param Generic
	 *            item that
	 * @return Integer value that returns the result of the comparison
	 */
	@SuppressWarnings("unchecked")
	private int compare(E holderObject, E that) {

		// Comparator is used if one is found
		if (comp == null) {
			return ((Comparable<E>) holderObject).compareTo(that);
		} else
			return comp.compare(holderObject, that);
	}

	/**
	 * Retrieves, but does not remove, the minimum element in this priority
	 * queue.
	 * 
	 * @return the minimum element or null if the queue is empty
	 */
	@Override
	public E findMin() {

		if (size == 0)
			return null;
		else
			// minimum element is the last element
			return holderArray[size - 1];
	}

	/**
	 * Retrieves and removes the minimum element in this priority queue.
	 * 
	 * @return the minimum element or null if the queue is empty
	 */
	@Override
	public E deleteMin() {

		if (size == 0)
			return null;
		else {
			// Returns the object and decrements the size
			E tempObject = holderArray[size - 1];
			size--;
			return tempObject;
		}
	}

	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @param item
	 *            -- the element to insert
	 */
	public void insert(E item) {

		E temp;

		// Extends the array if necessary
		if (size >= holderArray.length - 2) {
			extendArray();
		}

		// If this is the first item add it to index zero
		if (size == 0) {
			holderArray[0] = item;
			size++;

			// If only one element is in the queue, just compares item to that
			// element
		} else if (size == 1) {
			if (compare(holderArray[0], item) < 0) {
				temp = holderArray[0];
				holderArray[0] = item;
				holderArray[1] = temp;
				size++;
			} else {
				holderArray[1] = item;
				size++;
			}
		} else {
			int high = 0;
			int low = size;

			// Binary search starts here
			while (high < low) {
				if (compare(holderArray[0], item) <= 0) {
					swap(high, item);
					size++;
					break;
				}
				int mid = ((low + high) / 2);

				// If mid index = item, insert item and shift rest by 1
				if (holderArray[mid] == item) {
					swap(mid, item);
					size++;
					break;
				}
				// If item is greater than mid index, insert element
				if (compare(holderArray[mid], item) < 0)
					low = mid;
				else
					high = mid + 1;

				if (high == low) {
					swap(low, item);
					size++;
					break;
				}
			}
		}

	}

	/**
	 * Private method created to insert the given item and push all elements
	 * after it back one index
	 * 
	 * @param index
	 * @param item
	 */
	private void swap(int index, E item) {

		E temp = holderArray[index];
		holderArray[index] = item;

		// Push all items back
		for (int i = index + 1; i < holderArray.length; i++) {
			E nextTemp = holderArray[i];
			holderArray[i] = temp;
			temp = nextTemp;
		}
	}

	/**
	 * Inserts the specified elements into this priority queue.
	 * 
	 * @param coll
	 *            -- the collection of elements to insert
	 */
	@SuppressWarnings("unchecked")
	public void insertAll(Collection<? extends E> coll) {
		for (Object element : coll)
			insert((E) element);
	}

	/**
	 * Private method created to double the size of the array and copy all of
	 * the elements over to the new array
	 */
	@SuppressWarnings("unchecked")
	private void extendArray() {

		E[] newArray = (E[]) new Object[holderArray.length * 2];
		for (int i = 0; i < holderArray.length; i++) {
			// Store values in the new temp array
			newArray[i] = holderArray[i];
		}
		// holder array must now be the new array
		holderArray = newArray;
	}

	/**
	 * @return the number of elements in this priority queue
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * @return true if this priority queue contains no elements
	 */
	@Override
	public boolean isEmpty() {
		if (size() == 0)
			return true;
		return false;
	}

	/**
	 * Removes all of the elements from this priority queue. The queue will be
	 * empty when this call returns.
	 */
	@Override
	public void clear() {
		size = 0;
	}

	/**
	 * @return an iterator over the elements in this priority queue, where the
	 *         elements are returned in sorted (descending) order
	 */
	@Override
	public Iterator<E> iterator() {

		return new Iterator<E>() {

			int index = 0;
			boolean okToRemove = false;

			/**
			 * Returns true if there is a next element in the priority queue
			 */
			@Override
			public boolean hasNext() {

				return index < size;
			}

			/**
			 * Returns the next element. If there is not a next element throws a
			 * NoSuchElementException
			 */
			@Override
			public E next() {
				if (!hasNext())
					throw new NoSuchElementException();

				// Only okay if priority queue has next element
				okToRemove = true;

				return (E) holderArray[index++];
			}

			/**
			 * Removes the current element from priority queue. If there is no
			 * element, throws an IllegalStateException
			 */
			@Override
			public void remove() {
				if (!okToRemove)
					throw new IllegalStateException();
				
				//Must push all elements back one.
				for (int i = index; i < size; i++)
					holderArray[i - 1] = holderArray[i];

				size--;
				index--;

				okToRemove = false;
			}
		};
	}

}
