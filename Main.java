import java.util.Arrays;
import java.util.ArrayList;

public class Main {

	static int m, n;
	static int d = 6;
	static int[] mass = {2400, 2000, 1200, 2400, 1600, 4000};
	static int[] inSetting = {1, 4, 5, 3, 6, 2};
	static int[] outSetting = {5, 3, 2, 4, 6, 1};
	

		
	public static void main(String[] args) {
		
		boolean[] cycles = new boolean[d];
//		boolean[] onRightPos = new boolean[d];
//		onRightPos = validator(onRightPos); //cell is true if elephant is on its destination place
//		int counter = countTrue(onRightPos); //nr of elephants on their place
		System.out.println(Arrays.toString(cycles));
		
		//permutacje
		int[] perm = permutations(inSetting, outSetting);
		System.out.println(Arrays.toString(perm));
		
		//rozklad na cykle proste
		
		ArrayList<Integer> cycl = new ArrayList<Integer>();
		ArrayList<Integer> cyclAux = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> allCycl = new ArrayList<ArrayList<Integer>>();
		
		int c = 0;
		int x = 0;
		for (int i = 1; i <= d; i++) {
			
			if (!cycles[i]) {
				c++;
				x = i;

				while (!cycles[x]) {
					cycles[x] = true;
					cycl.add(x);
					System.out.println("x: " + x + ", perm[x - 1]: " + perm[x - 1]);
					System.out.println(cycl);
					x = perm[x - 1];

				}
				cyclAux = (ArrayList<Integer>) cycl.clone();
				allCycl.add(cyclAux);
				cycl.clear();
				
			}
			System.out.println("AllCycl: " + allCycl);
			System.out.println(Arrays.toString(cycles));
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
//				validatePos[i] = true;
				validatePos[inSetting[i] - 1] = true;
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
	
	public static int[] permutations(int[] inSetting, int[] outSetting) {
		
		//permutacje
		int[] perm = new int[d];
		for (int i = 0; i < d; i++) {
		perm[i] = inSetting[finder(outSetting, i + 1)];			
		}
		return perm;
	}
}
