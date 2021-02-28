import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.math.BigInteger;

//author: Bartłomiej Szamota

public class SlonieIO {
	public static void main(String[] args) {

		// read from standard input
		String inputData = null;
		try {
			inputData = new String(new BufferedInputStream(System.in).readAllBytes());
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//check if user passed a data file
		if (inputData.isEmpty()) {
			System.out.println("NO FILE!");
			System.exit(0);
		}
		
		// split standard input into consecutive rows
		String[] strArrRow = inputData.split("\n");

		// split rows and fill in an array
		String[] massAux = strArrRow[1].split(" ");
		String[] inSettingAux = strArrRow[2].split(" ");
		String[] outSettingAux = strArrRow[3].split(" ");

		// data dimension
		int d = Integer.parseInt(strArrRow[0]);

		// check if data dimensions are correct
		if (massAux.length != d) {
			System.out.println("2nd line has wrong dimesion");
			System.exit(0);
		} else if (inSettingAux.length != d) {
			System.out.println("3rd line has wrong dimesion");
			System.exit(0);
		} else if (outSettingAux.length != d) {
			System.out.println("4th line has wrong dimesion");
			System.exit(0);
		}

		// transfer "String" numbers to int arrays
		int[] mass = new int[d];
		int[] inSetting = new int[d];
		int[] outSetting = new int[d];

		for (int i = 0; i < d; i++) {
			mass[i] = Integer.parseInt(massAux[i]);
			inSetting[i] = Integer.parseInt(inSettingAux[i]);
			outSetting[i] = Integer.parseInt(outSettingAux[i]);
		}

		// permutations
		int[] perm = permutations(inSetting, outSetting);
//		System.out.println("PERMUTATIONS CALCULATED");

		// simple cycles
		ArrayList<ArrayList<Integer>> allCycl = new ArrayList<ArrayList<Integer>>();
		allCycl = crCycles(perm, d);
//		System.out.println("SIMPLE CYCLES CALCULATED: " + allCycl.size());

		// calculating effort
		BigInteger costSum = new BigInteger("0");
		BigInteger aux = new BigInteger("0");

		// how many cycles there are
		int cyclesAmount = allCycl.size();

		for (int j = 0; j < cyclesAmount; j++) {
			aux = BigInteger.valueOf(cost(allCycl.get(j), mass));
			costSum = costSum.add(aux);
//			int x = j + 1;
//			System.out.println("j: (" + x + " / " + cyclesAmount + ")");
		}
//		System.out.println("MIN EFFORT:");
		System.out.println(costSum);
	}

	// calculate which method is more efficient and return its "effort" cost
	public static long cost(ArrayList<Integer> cycl, int[] mass) {

		long method1 = method1Cost(cycl, mass);
		long method2 = method2Cost(cycl, mass);

		if (method1 < method2 && cycl.size() != 1) {
			return method1;
		} else if ((method1 >= method2 && cycl.size() != 1)) {
			return method2;
		} else {
			return 0;
		}
	}

	// calculate cost (effort) of 1st method
	public static long method1Cost(ArrayList<Integer> cycl, int[] mass) {
		return massOfCycle(cycl, mass) + (cycl.size() - 2) * minInCycle(cycl, mass);
	}

	// calculate cost (effort) of 2nd method
	public static long method2Cost(ArrayList<Integer> cycl, int[] mass) {
		int min = minValue(mass);
		return massOfCycle(cycl, mass) + minInCycle(cycl, mass) + (cycl.size() + 1) * min;
	}

	// find minimal value within an array
	public static int minValue(int[] arr) {
		int min = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < min) {
				min = arr[i];
			}
		}
		return min;
	}

	// function suma(C)
	public static long massOfCycle(ArrayList<Integer> cycl, int[] mass) {
		long sum = 0L;
		for (int i = 0; i < cycl.size(); i++) {
			sum += mass[cycl.get(i) - 1];
		}
		return sum;
	}

	// function min(C)
	public static long minInCycle(ArrayList<Integer> cycl, int[] mass) {
		ArrayList<Integer> masses = new ArrayList<Integer>();
		for (int i = 0; i < cycl.size(); i++) {
			masses.add(mass[cycl.get(i) - 1]);
		}
		return Collections.min(masses);
	}

	// method gathering all the data about cycles into an ArrayList of ArrayList
	// (matrix like)
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<Integer>> crCycles(int[] perm, int d) {
		ArrayList<Integer> cycl = new ArrayList<Integer>();
		ArrayList<Integer> cyclAux = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> allCycl = new ArrayList<ArrayList<Integer>>();

		boolean[] cycles = new boolean[d];
		int x = 0;
		for (int i = 1; i <= d; i++) {

			if (!cycles[i - 1]) {
				x = i;

				while (!cycles[x - 1]) {
					cycles[x - 1] = true;
					cycl.add(x);
					x = perm[x - 1];

				}
				cyclAux = (ArrayList<Integer>) cycl.clone();
				allCycl.add(cyclAux);
				cycl.clear();
			}
		}
		return allCycl;
	}

	// find index of cell in array given its value
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

	// calculates permutations
	public static int[] permutations(int[] inSetting, int[] outSetting) {
		int d = inSetting.length;
		int[] perm = new int[d];
		for (int i = 0; i < d; i++) {
			perm[i] = inSetting[finder(outSetting, i + 1)];
//		int x = i + 1;
//		System.out.println("i: (" + x + " / " + d + ")");
		}
		return perm;
	}
}
