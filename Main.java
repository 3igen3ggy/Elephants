import java.util.Arrays;

import java.util.ArrayList;

public class Main {

	static int m, n;
	static int d = 10;
	static int[] mass = {3015, 4728, 4802, 4361, 135, 4444, 4313, 1413, 4581, 546};
	static int[] inSetting = {3, 10, 1, 8, 9, 4, 2, 7, 6, 5};
	static int[] outSetting = {4, 9, 5, 3, 1, 6, 10, 7, 8, 2};
	
	public static void main(String[] args) {
		
//		int[] outAux = generateLinear(100);
//		int[] inAux = mapper(outSetting, inSetting);
//		System.out.println(Arrays.toString(inAux));
//		System.out.println(Arrays.toString(outAux));
		
		boolean[] validatePos = new boolean[d];
		validatePos = validator(validatePos);
		
		int[] massSorted = bubbleSort(mass);
		int counter = countTrue(validatePos);
		
		int iteration = 0;
		int effort = 0;
		System.out.println("in: " + Arrays.toString(inSetting));
		System.out.println("out: " + Arrays.toString(outSetting));
		System.out.println(Arrays.toString(mass));
		System.out.println(Arrays.toString(massSorted));
		

		
		
		System.out.println();
		System.out.println(Arrays.toString(inSetting));
		System.out.println(Arrays.toString(validatePos));
		System.out.println("Iteration: " + iteration + ", Counter: " + counter + ", Effort: " + effort);
		
		//i: the lightest unsorted elephant index
		int i = 0;
		
		while (counter < d) {
			iteration++;
			System.out.println("i: " + i);
			
			
			int light = finder(mass, massSorted[i]) + 1;
			int lightIndex = finder(inSetting, light);

			int valid = outSetting[lightIndex];
			int validIndex = finder(inSetting, valid);
			
	
			
			effort += mass[light - 1] + mass[valid - 1];
			
			System.out.println("light: " + light + ", lightIndex: " + lightIndex + ", mass: " + mass[light - 1]);
			System.out.println("valid: " + valid + ", validIndex: " + validIndex + ", mass: " + mass[valid - 1]);
			inSetting = changeOrder(inSetting, lightIndex, validIndex);
			
			
			validatePos[lightIndex] = true;
			counter++;
			
				if (inSetting[validIndex] == outSetting[validIndex]) {
					validatePos[validIndex] = true;
					counter++;
				}
			System.out.println();
			System.out.println(Arrays.toString(inSetting));
			System.out.println(Arrays.toString(validatePos));
			System.out.println("Iteration: " + iteration + ", Counter: " + counter + ", Effort: " + effort);

			i++;
		}
		
	}
	
	public static int[] changeOrder(int[] in, int a, int b) {
		int c = in[a];
		in[a] = in[b];
		in[b] = c;
		return in;
	}
	
	//generate sorted array from 1, 2 to d
	public static int[] generateLinear(int d) {
		
		int[] arr = new int[d];
		for (int i = 0; i < d; i++) {
			arr[i] = i + 1;
		}
		return arr;
	}
	
	//find index of cell in array given its input
	public static int finder(int[] arr, int value) {
		int i = 0;
		int d = arr.length;
		
		while (i < d) {
			if (arr[i] == value) {
				return i;
			} else {
				i++;
			}
			
		}
		return -1;	
	}
	// Generate auxiliary array of initial setting such,
	// that when sorted ascending it will answer the question
	// given in the exercise
	public static int[] mapper(int[] outSetting, int[] inSetting) {
		
		int d = inSetting.length;
		int[] inAux = new int[d];
		
		for (int i = 0; i < d; i++) {
			inAux[i] = 1 + finder(outSetting, inSetting[i]);
		}
		return inAux;
		
	}
	
	//find minimal value within an array
	public static int minValue(int[] arr) {
		int min = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < min) {
				min = arr[i];
			}
		}
		return min;
	}
	
	public static void checkMemory() {
		long memo = (Runtime.getRuntime().totalMemory() -
				Runtime.getRuntime().freeMemory()) / (1000 * 1000);
		System.out.println("Meg used = " + memo + "MB");
		
		if (memo > 64) {
			System.out.print(" --- !!! MEMORY OVER 64MB !!! ---");
			System.exit(64);
		}
	}
	
	//mass of particular elephant
	public static int massOf(int n) {
		return mass[n - 1];		
	}
	
	public static int effort(int n, int m) {
		return massOf(n) + massOf(m);
	}
	
	public static int[] bubbleSort(int[] array) {
		int i = 0;
		int j = 1;
		int n = array.length;
		int[] arr = array.clone();
		int x = 0;
		
		while (j == 1) {
			j = 0;
			while (i < n - 1) {
				if (arr[i] > arr[i+1]) {
					x = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = x;
					j = 1;
				}
				i++;
			}
			i = 0;
		}
		return arr;
	}
	
	public static boolean[] validator(boolean[] validatePos) {
		for (int i = 0; i < validatePos.length; i++) {
			if (inSetting[i] == outSetting[i]) {
				validatePos[i] = true;
			}
		}
		return validatePos;
	}

	public static int countTrue(boolean[] validatePos) {
		int counter = 0;
		for (int i = 0; i < validatePos.length; i++) {
			if (validatePos[i]) {
				counter++;
			}
		}
		return counter;
	}
}
