import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.math.BigInteger;

public class Main {
	public static void main(String[] args) {

		ArrayList<ArrayList<Integer>> allCycl = new ArrayList<ArrayList<Integer>>();
		
///////////////////////////////////////////////////////////////////////
		String filename = "slo1"; //////////////////INPUT FILE CHOICE
///////////////////////////////////////////////////////////////////////
		int[][] table = importFromFile(filename); 
		System.out.println("\nFILE IMPORTED");
		
		int d = table[0][0];
		int[] mass = table[1];
		int[] inSetting = table[2];
		int[] outSetting = table[3];

		
		//permutations
		int[] perm = permutations(inSetting, outSetting);
		System.out.println("PERMUTATIONS CALCULATED");
				
		//simple cycles
		allCycl = crCycles(perm, d);
		System.out.println("SIMPLE CYCLES CALCULATED: " + allCycl.size());
		
		//calculating effort
		BigInteger costSum = new BigInteger("0");
		BigInteger aux = new BigInteger("0");
		
		int cyclesAmount = allCycl.size();
		
		for (int j = 0; j < cyclesAmount; j++) {
			aux = aux.valueOf(cost(allCycl.get(j), mass));
			costSum = costSum.add(aux);
			int x = j + 1;
			System.out.println("j: (" + x + " / " + cyclesAmount + ")");
		}
		System.out.println("MIN EFFORT: " + costSum);
		
		
		try {
			exportToFile(filename, costSum);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	//importing data from .in file
	public static int[][] importFromFile(String filename) {
		String workingDirectory = System.getProperty("user.dir") + "/src/";
		String file = workingDirectory + filename + ".in";
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
			
			return table;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[][] table = new int[1][1];
		return table;
	}
	
	//exporting to .out file
	public static void exportToFile(String filename, BigInteger costSum) throws FileNotFoundException {
		String workingDirectory = System.getProperty("user.dir") + "/src/";
		String file = workingDirectory + filename + ".out";
		PrintWriter pr = new PrintWriter(file);
		pr.println(costSum);
		pr.close();
	}
	
	//calculate which method is more efficient and return its "effort" cost
	public static long cost(ArrayList<Integer> cycl, int[] mass) {
		
		long method1 = method1Cost(cycl, mass);
		long method2 = method2Cost(cycl, mass);
		
		if (method1 < method2 && cycl.size() != 1) {
			return method1;
		} else if ((method1 >= method2 && cycl.size() != 1)) {
			return method2;
		}	else {
			return 0;
		}
	}
	
	//calculate cost (effort) of 1st method
	public static long method1Cost (ArrayList<Integer> cycl, int[] mass) {
		return massOfCycle(cycl, mass) + (cycl.size() - 2) * minInCycle(cycl, mass);
	}
	//calculate cost (effort) of 2nd method
	public static long method2Cost (ArrayList<Integer> cycl, int[] mass) {
		int min = minValue(mass);
		return massOfCycle(cycl, mass) + minInCycle(cycl, mass) + (cycl.size() + 1) * min;
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
	
	//function suma(C)
	public static long massOfCycle(ArrayList<Integer> cycl, int[] mass) {
		long sum = 0L;
		for (int i = 0; i < cycl.size(); i++) {
			sum += mass[cycl.get(i) - 1];			
		}
		return sum;
	}
	
	//function min(C)
	public static long minInCycle(ArrayList<Integer> cycl, int[] mass) {
		ArrayList<Integer> masses = new ArrayList<Integer>();
		for (int i = 0; i < cycl.size(); i++) {
			masses.add(mass[cycl.get(i) - 1]);
		}
		return Collections.min(masses);
	}
	
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
	
	//find index of cell in array given its value
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

	//calculates permutations
	public static int[] permutations(int[] inSetting, int[] outSetting) {
		int d = inSetting.length;
		//permutacje
		int[] perm = new int[d];
		for (int i = 0; i < d; i++) {
		perm[i] = inSetting[finder(outSetting, i + 1)];	
		int x = i + 1;
		System.out.println("i: (" + x + " / " + d + ")");
		}
		return perm;
	}
}
