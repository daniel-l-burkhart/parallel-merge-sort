package controller;

import java.util.Arrays;
import java.util.Random;

import model.Merge;

/**
 * Controller class that acts between the model and the view.
 * 
 * @author Daniel Burkhart
 *
 */
public class MergeSortController {

	private Merge mergeSorter;
	private Integer[] data;
	private Integer[] copy;

	/**
	 * Constructor that initializes model vairable.
	 * 
	 * @param numberOfThreads
	 *            The number of threads that are going to be used
	 */
	public MergeSortController(int numberOfThreads) {
		this.mergeSorter = new Merge(numberOfThreads);
	}

	/**
	 * Makes a random array of a size determined by user
	 * 
	 * @param sizeOfArray
	 *            The size of the array.
	 */
	public void makeRandomArray(int sizeOfArray) {

		Random randomizer = new Random();
		this.data = new Integer[sizeOfArray];

		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = randomizer.nextInt(Integer.SIZE - 1);
		}

		this.copy = Arrays.copyOf(this.data, this.data.length);
	}

	/**
	 * Performs the multi-threaded sorting program
	 * 
	 * @return True if the array is sorted. False otherwise
	 */
	public boolean multiSortArray() {
		this.mergeSorter.multiSort(this.data);
		return this.checkArray();
	}

	/**
	 * Performs the merge sort algorithm in one single thread.
	 * 
	 * @return True if array is sorted, false otherwise.
	 */
	public boolean singleSortArray() {
		this.mergeSorter.singleSort(this.data);
		return this.checkArray();
	}

	private boolean checkArray() {
		Arrays.sort(this.copy);
		return Arrays.equals(this.data, this.copy);
	}

}
