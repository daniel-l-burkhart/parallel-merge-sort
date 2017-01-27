package model;

/**
 * Sorter thread that sets up a new sort method for the current sub array
 * 
 * @author Daniel Burkhart.
 */
public class Sorter implements Runnable {

	private int threadCount;
	private Merge mergeSort;
	private Integer[] newArray;

	/**
	 * Constructor that initializes new sorting algorithm
	 * 
	 * @param array
	 *            The current subarray
	 * @param threadCount
	 *            The number of threads in the program
	 */
	public Sorter(Integer[] array, int threadCount) {
		this.newArray = array;
		this.threadCount = threadCount;
		this.mergeSort = new Merge(this.threadCount);

	}

	@Override
	public void run() {
		this.mergeSort.multiSort(this.newArray);
	}
}