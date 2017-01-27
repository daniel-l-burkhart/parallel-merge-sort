package model;

import java.util.Arrays;

/**
 * implements the mergesort algorithm on arrays of type Integer as a single
 * thread and in parallel with N threads
 * 
 * @author Daniel Burkhart
 */
public class Merge {

	private int numberOfThreads;
	private Integer[] leftSide;
	private Integer[] rightSide;
	private Thread leftThread;
	private Thread rightThread;
	private Integer[] originalArray;

	/**
	 * Constructor that keeps the number of threads
	 * 
	 * @param threadCount
	 *            The number of threads.
	 */
	public Merge(int threadCount) {
		this.numberOfThreads = threadCount;
	}

	/**
	 * Single threaded merge sort
	 * 
	 * @param data
	 *            The current array
	 */
	public void singleSort(Integer[] data) {
		Integer[] tmp = new Integer[data.length];
		this.single(data, tmp, 0, data.length);
	}

	private void single(Integer[] data, Integer[] buffer, int min, int max) {

		if (max - min <= 1) {
			return;
		}

		int mid = (min + max) / 2;
		this.single(data, buffer, min, mid);
		this.single(data, buffer, mid, max);
		this.merge(data, buffer, min, mid, max);
	}

	private void merge(Integer[] data, Integer[] buffer, int min, int mid, int max) {
		int i = min;
		int j = mid;
		int current = min;
		while ((i < mid) && (j < max)) {
			if (data[i] < data[j]) {
				buffer[current++] = data[i++];
			} else {
				buffer[current++] = data[j++];
			}
		}
		while (i < mid) {
			buffer[current++] = data[i++];
		}
		while (j < max) {
			buffer[current++] = data[j++];
		}
		for (current = min; current < max; current++) {
			data[current] = buffer[current];
		}
	}

	/**
	 * Multi-threaded merge sort
	 * 
	 * @param data
	 *            The current array
	 */
	public void multiSort(Integer[] data) {

		if (data == null) {
			throw new IllegalArgumentException("Data cannot be null");
		}

		this.originalArray = data;

		Integer[] temp = new Integer[this.originalArray.length];

		if (this.numberOfThreads <= 1) {
			this.single(this.originalArray, temp, 0, this.originalArray.length);

		} else if (this.originalArray.length >= 2) {

			this.splitArray();
			this.startThreads();
			this.mergeBack(this.leftSide, this.rightSide, this.originalArray);
		}
	}

	private void splitArray() {
		this.leftSide = Arrays.copyOfRange(this.originalArray, 0, this.originalArray.length / 2);
		this.rightSide = Arrays.copyOfRange(this.originalArray, this.originalArray.length / 2,
				this.originalArray.length);

	}

	private void startThreads() {

		this.leftThread = new Thread(new Sorter(this.leftSide, this.numberOfThreads / 2));
		this.rightThread = new Thread(new Sorter(this.rightSide, this.numberOfThreads / 2));

		this.leftThread.start();
		this.rightThread.start();

		try {
			this.leftThread.join();
			this.rightThread.join();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	private void mergeBack(Integer[] left, Integer[] right, Integer[] originalData) {

		if (left.length + right.length != originalData.length) {
			throw new IllegalStateException("Invalid length of arrays: left, " + left.length + " right, " + right.length
					+ " orig, " + originalData.length);
		}

		int indexOne = 0;
		int indexTwo = 0;
		for (int i = 0; i < originalData.length; i++) {
			if (indexTwo >= right.length || (indexOne < left.length && left[indexOne] < right[indexTwo])) {
				originalData[i] = left[indexOne];
				indexOne++;
			} else {
				originalData[i] = right[indexTwo];
				indexTwo++;
			}
		}
	}
}
