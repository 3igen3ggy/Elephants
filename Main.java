import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {



	




	
	public static void main(String[] args) {
		

		ArrayList<ArrayList<Integer>> allCycl = new ArrayList<ArrayList<Integer>>();

		int[][] table = importFromFile("slo1.in");
		
		int d = table[0][0];
		int[] mass = table[1];
		int[] inSetting = table[2];
		int[] outSetting = table[3];
		
		
		int min = min(mass);
		//permutacje
		 int[] perm = permutations(inSetting, outSetting);
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int costSum = 0;
		
		System.out.println("Permutations: " + Arrays.toString(perm));
		
		//rozklad na cykle proste
		
		allCycl = crCycles(perm, d);
		System.out.println(allCycl);
		
		for (int i = 0; i < allCycl.size(); i++) {
			
			costSum += cost(allCycl.get(i), mass);
			
		}
		System.out.println(costSum);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	
	public static int[][] importFromFile(String filename) {
		String workingDirectory = System.getProperty("user.dir") + "/src/";
		String file = workingDirectory + filename;
		String line = "";
	
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			//generate matrix for imported values
			int[][] table = new int [4][];
			
			//get first line (number of elephants)
			line = br.readLine();
			
			int[] dMatrix = {Integer.parseInt(line)};
			table[0] = dMatrix;
			int d = table[0][0];
			
			//get second line (masses)
			line = br.readLine();
			int[] massAux = new int[d];
			String[] strMass = line.split(" ");
			
			for (int i = 0; i < d; i++) {
				massAux[i] = Integer.parseInt(strMass[i]);
			}
			int[] mass = Arrays.copyOf(massAux, d);
			table[1] = mass;

			
			//get third line (initial setting)
			line = br.readLine();
			int[] inSettingAux = new int[d];
			String[] strInSetting = line.split(" ");
			
			for (int i = 0; i < d; i++) {
				inSettingAux[i] = Integer.parseInt(strInSetting[i]);
			}
			int[] inSetting = Arrays.copyOf(inSettingAux, d);
			table[2] = inSetting;

			
			//get fourth line (final setting)
			line = br.readLine();
			int[] outSettingAux = new int[d];
			String[] strOutSetting = line.split(" ");
			
			for (int i = 0; i < d; i++) {
				outSettingAux[i] = Integer.parseInt(strOutSetting[i]);
			}
			int[] outSetting = Arrays.copyOf(outSettingAux, d);
			table[3] = outSetting;
					
			System.out.println("d: " + d);
			System.out.println("Masses: " + Arrays.toString(mass));
			System.out.println("In: " + Arrays.toString(inSetting));
			System.out.println("Out: " + Arrays.toString(outSetting));
			
			return table;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[][] table = new int[1][1];
		return table;
	}
	
	public static int cost(ArrayList<Integer> cycl, int[] mass) {
		
		int method1 = method1Cost(cycl, mass);
		int method2 = method2Cost(cycl, mass);
		
		if (method1 < method2) {
			return method1;
		} else {
			return method2;
		}	
	}
	
	public static int method1Cost (ArrayList<Integer> cycl, int[] mass) {
		return massOfCycle(cycl, mass) + (cycl.size() - 2) * minInCycle(cycl, mass);
	}
	
	public static int method2Cost (ArrayList<Integer> cycl, int[] mass) {
		int min = minValue(mass);
		return massOfCycle(cycl, mass) + minInCycle(cycl, mass) + (cycl.size() + 1) * min;
	}
		
	//najmnieszy slon
	public static int min(int[] mass) {
		int min = mass[0];
		for (int i = 0; i < mass.length; i++) {	
			if (mass[i] < min) {
				min = mass[i];
			}
		}
		System.out.println("min: " + min);
		return min;
	}
	
	
	//funkcja suma(C)
	public static int massOfCycle(ArrayList<Integer> cycl, int[] mass) {
		int sum = 0;
		for (int i = 0; i < cycl.size(); i++) {
			sum += mass[cycl.get(i) - 1];			
		}
		System.out.println("suma(C): " + sum);
		return sum;
	}
	
	//funkcja min(C)
	public static int minInCycle(ArrayList<Integer> cycl, int[] mass) {
		ArrayList<Integer> masses = new ArrayList<Integer>();
		for (int i = 0; i < cycl.size(); i++) {
			masses.add(mass[cycl.get(i) - 1]);
		}
		System.out.println("min(C): " + Collections.min(masses));
		return Collections.min(masses);
	}
	
	public static ArrayList<ArrayList<Integer>> crCycles(int[] perm, int d) {
		ArrayList<Integer> cycl = new ArrayList<Integer>();
		ArrayList<Integer> cyclAux = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> allCycl = new ArrayList<ArrayList<Integer>>();
		
		boolean[] cycles = new boolean[d];
		int c = 0;
		int x = 0;
		for (int i = 1; i <= d; i++) {
			
			if (!cycles[i - 1]) {
				c++;
				x = i;

				while (!cycles[x - 1]) {
					cycles[x - 1] = true;
					cycl.add(x);
					System.out.println(cycl);
					System.out.println(Arrays.toString(cycles));
					x = perm[x - 1];

				}
				cyclAux = (ArrayList<Integer>) cycl.clone();
				allCycl.add(cyclAux);
				cycl.clear();
				
			}
		}
		return allCycl;
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
		
		int d = inSetting.length;
		//permutacje
		int[] perm = new int[d];
		for (int i = 0; i < d; i++) {
		perm[i] = inSetting[finder(outSetting, i + 1)];			
		}
		return perm;
	}
}
