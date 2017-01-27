package view;

import java.util.Scanner;

import controller.MergeSortController;

/**
 * Driver class that contains the view and the main method
 * 
 * @author Daniel Burkhart
 * @version Spring 2017
 */
public class ApplicationDriver {

	private MergeSortController controller;
	private Scanner input;
	private String userInput;
	private boolean result;
	private long startTime;
	private long endTime;
	private long difference;

	/**
	 * Constructor that initializes necessary variables
	 */
	public ApplicationDriver() {
		this.controller = new MergeSortController(8);
		this.input = new Scanner(System.in);
	}

	/**
	 * Runs the UI of the program.
	 */
	public void runProgram() {
		this.createArray();
		this.singleOrMultiSortInput();
		this.sortArray();
		this.outputResult();
	}

	private void createArray() {
		System.out.println("How many random elements do you want in the array?");
		this.userInput = this.input.next();
		boolean notNumber = true;

		while (notNumber) {
			try {
				int sizeOfArray = Integer.parseInt(this.userInput);
				notNumber = false;
				this.controller.makeRandomArray(sizeOfArray);

			} catch (NumberFormatException exception) {
				notNumber = true;
			}
		}

	}

	private void singleOrMultiSortInput() {
		System.out.println("Would you like to sort this array in one thread or multiple?");
		System.out.println("Enter single or multi");
		this.userInput = this.input.next();
		this.userInput = this.userInput.toLowerCase();

		while (!this.userInput.equals("single") && !this.userInput.equals("multi")) {
			System.out.println("Invalid input. Please enter single or multi");
		}

	}

	private void sortArray() {

		if (this.userInput.equals("single")) {

			this.startTime = System.currentTimeMillis();
			this.result = this.controller.singleSortArray();
			this.endTime = System.currentTimeMillis();

		} else if (this.userInput.equals("multi")) {

			this.startTime = System.currentTimeMillis();
			this.result = this.controller.multiSortArray();
			this.endTime = System.currentTimeMillis();
		}

		this.difference = this.endTime - this.startTime;
	}

	private void outputResult() {
		if (this.result) {
			System.out.println("Array was sorted correctly");
			System.out.println("Time elapsed: " + this.difference + " ms");
		} else if (!this.result) {
			System.out.println("Array was sorted incorrectly");
			System.out.println("Time elapsed: " + this.difference + " ms");
		}
	}

	/**
	 * Main method of program. Entry point
	 * 
	 * @param args
	 *            The args of the program
	 */
	public static void main(String[] args) {
		ApplicationDriver driver = new ApplicationDriver();
		driver.runProgram();
	}

}
