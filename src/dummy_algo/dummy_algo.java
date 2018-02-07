package dummy_algo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.jblas.DoubleMatrix;

public class dummy_algo {

	static int real_size_of_IG;

	final static int pservers = 0;
	final static int pswitches = 1;
	final static int pvnfp = 2;

	final static int vvms = 0;
	final static int vswitches = 1;
	final static int vvnfp = 2;
	final static int vvnfc = 3;

	final static int fW = 0;
	final static int proxy = 1;
	final static int nat = 2;
	final static int IDS = 3;

	final static int noreuse = 0;
	final static int reuse = 1;

	public static WeightedGraph weightedgraphRG;

	public static HashMap<Integer, Server> Pservers;
	public static HashMap<Integer, Server> Pswitches;
	public static HashMap<Integer, Server> Pvnfp;
	public static HashMap<Integer, Server> Pnodes;

	public static HashMap<Integer, Server> Vvms;
	public static HashMap<Integer, Server> Vswitches;
	public static HashMap<Integer, Server> Vvnfp;
	public static HashMap<Integer, Server> Vvnfc;
	public static HashMap<Integer, Server> Vnodes;
	public static double INFINI = Double.MAX_VALUE;

	public static DoubleMatrix read_RG_graph(String type, int size, int index) throws IOException {

		FileInputStream fstream = new FileInputStream("instance" + type + size + "-" + index);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String str;
		boolean N = true;
		boolean L = false;
		br.readLine();
		br.readLine();
		str = br.readLine();
		DoubleMatrix matrix = new DoubleMatrix(size, size);
		Server[] serverRG = new Server[size];
		Cost[][] adjaMatrixGraph = new Cost[size][size];
		int nbservers = 0;
		Pservers = new HashMap<Integer, Server>();
		Pswitches = new HashMap<Integer, Server>();
		Pvnfp = new HashMap<Integer, Server>();
		Pnodes = new HashMap<Integer, Server>();

		while ((str = br.readLine()) != null) {
			if (str.equals("EDGES")) {
				N = false;
				L = true;
				str = br.readLine();
			}

			if (N) {
				String[] a = str.split(" ");
				matrix.put(Integer.parseInt(a[0]), Integer.parseInt(a[0]), Float.parseFloat(a[2]));
				Server tempserver = new Server();
				switch (Integer.parseInt(a[3])) {
				case pservers:
					tempserver = new Server(nbservers, (char) (index + 65) + a[0], a[1], Float.parseFloat(a[2]),
							Float.parseFloat(a[2]), Float.parseFloat(a[2]), pservers, -1, -1, -1, -1);
					Pservers.put(Integer.parseInt(a[0]), tempserver);
					Pnodes.put(Integer.parseInt(a[0]), tempserver);
					break;
				case pswitches:
					tempserver = new Server(nbservers, (char) (index + 65) + a[0], a[1], Float.parseFloat(a[2]),
							Float.parseFloat(a[2]), Float.parseFloat(a[2]), pswitches, -1, -1, -1, -1);
					Pswitches.put(Integer.parseInt(a[0]), tempserver);
					Pnodes.put(Integer.parseInt(a[0]), tempserver);
					break;
				case pvnfp:
					tempserver = new Server(nbservers, (char) (index + 65) + a[0], a[1], Float.parseFloat(a[2]),
							Float.parseFloat(a[2]), Float.parseFloat(a[2]), pvnfp, Integer.parseInt(a[4]), -1, -1, -1);
					Pvnfp.put(Integer.parseInt(a[0]), tempserver);
					Pnodes.put(Integer.parseInt(a[0]), tempserver);
					break;
				default:
					System.out.println("there is no type assigned to node: " + Integer.parseInt(a[0]));
				}
				nbservers++;
				serverRG[Integer.parseInt(a[0])] = tempserver;

			}
			if (L) {
				String[] a = str.split(" ");
				matrix.put(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Float.parseFloat(a[2]));
				matrix.put(Integer.parseInt(a[1]), Integer.parseInt(a[0]), Float.parseFloat(a[2]));
				adjaMatrixGraph[Integer.parseInt(a[0])][Integer.parseInt(a[1])] = new Cost(Float.parseFloat(a[2]),
						Float.parseFloat(a[2]), Float.parseFloat(a[2]));
				adjaMatrixGraph[Integer.parseInt(a[1])][Integer.parseInt(a[0])] = new Cost(Float.parseFloat(a[2]),
						Float.parseFloat(a[2]), Float.parseFloat(a[2]));
			}

		}
		in.close();
		
		weightedgraphRG = new WeightedGraph(adjaMatrixGraph, serverRG);
		
		weightedgraphRG.buildMatrixWithdirectLink(weightedgraphRG);
		
		return matrix;

	}

	public static DoubleMatrix read_IG_graph(String type, int size, int index) throws IOException {

		FileInputStream fstream = new FileInputStream("instance" + type + size + "-" + index);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String str;
		boolean N = true;
		boolean L = false;
		br.readLine();
		String b[] = br.readLine().split(" ");
		int IG_size = Integer.parseInt(b[0]);
		int nbSFC = Integer.parseInt(b[1]);
		int tenantID = Integer.parseInt(b[2]);
		real_size_of_IG = IG_size;
		str = br.readLine();
		DoubleMatrix matrix = new DoubleMatrix(IG_size, IG_size);
		int nbVirtualNode = 0;

		Vvms = new HashMap<Integer, Server>();
		Vswitches = new HashMap<Integer, Server>();
		Vvnfp = new HashMap<Integer, Server>();
		Vvnfc = new HashMap<Integer, Server>();
		Vnodes = new HashMap<Integer, Server>();

		while ((str = br.readLine()) != null) {
			if (str.equals("EDGES")) {
				N = false;
				L = true;
				str = br.readLine();
			}

			if (str.contains("same")) {
				break;
			}
			if (N) {
				String[] a = str.split(" ");
				matrix.put(Integer.parseInt(a[0]), Integer.parseInt(a[0]), Float.parseFloat(a[2]));

				Server tempserver = new Server();
				switch (Integer.parseInt(a[3])) {
				case vvms:
					tempserver = new Server(nbVirtualNode, (char) (index + 65) + a[0], a[1], Float.parseFloat(a[2]),
							Float.parseFloat(a[2]), Float.parseFloat(a[2]), vvms, -1, -1, -1, tenantID);
					Vvms.put(Integer.parseInt(a[0]), tempserver);
					Vnodes.put(Integer.parseInt(a[0]), tempserver);
					break;
				case vswitches:
					tempserver = new Server(nbVirtualNode, (char) (index + 65) + a[0], a[1], Float.parseFloat(a[2]),
							Float.parseFloat(a[2]), Float.parseFloat(a[2]), vswitches, -1, -1, Integer.parseInt(a[4]),
							tenantID);
					Vswitches.put(Integer.parseInt(a[0]), tempserver);
					Vnodes.put(Integer.parseInt(a[0]), tempserver);
					break;
				case vvnfp:
					tempserver = new Server(nbVirtualNode, (char) (index + 65) + a[0], a[1], Float.parseFloat(a[2]),
							Float.parseFloat(a[2]), Float.parseFloat(a[2]), vvnfp, Integer.parseInt(a[4]), -1, -1,
							tenantID);
					Vvnfp.put(Integer.parseInt(a[0]), tempserver);
					Vnodes.put(Integer.parseInt(a[0]), tempserver);
					break;
				case vvnfc:
					tempserver = new Server(nbVirtualNode, (char) (index + 65) + a[0], a[1], Float.parseFloat(a[2]),
							Float.parseFloat(a[2]), Float.parseFloat(a[2]), vvnfc, Integer.parseInt(a[4]),
							Integer.parseInt(a[5]), -1, tenantID);
					Vvnfc.put(Integer.parseInt(a[0]), tempserver);
					Vnodes.put(Integer.parseInt(a[0]), tempserver);
					break;
				default:
					System.out.println("there is no type assigned to virtual node: " + Integer.parseInt(a[0]));
				}
				nbVirtualNode++;
			}
			if (L) {
				String[] a = str.split(" ");
				float SFCbw = 0;
				for (int i = 0; i < nbSFC; i++)
					SFCbw += Float.parseFloat(a[2 + i]);
				matrix.put(Integer.parseInt(a[0]), Integer.parseInt(a[1]), SFCbw);
				// matrix.put(Integer.parseInt(a[1]), Integer.parseInt(a[0]),
				// SFCbw);
			}

		}
		in.close();

		return matrix;

	}

	public static void main(String[] args) throws IOException {

		int RG_size = Integer.parseInt(args[0]);
		int IG_size = Integer.parseInt(args[1]);

		DoubleMatrix RG = read_RG_graph("RG", RG_size, Integer.parseInt(args[2]));

		DoubleMatrix IG = read_IG_graph("IG", IG_size, Integer.parseInt(args[3]));
		IG_size = real_size_of_IG;

		int[] mappingResult = new int[IG_size];
		for (int j = 0; j < IG_size; j++) {
			mappingResult[j] = j;
		}

		
		System.out.println("Successful Mapping.....!!!!");
		for (int j = 0; j < IG_size; j++) {
			System.out.println("V" + j + " =====> P" + mappingResult[j]);
		}

		FileWriter writer = new FileWriter(
				"SolutionMappingDummy-instanceRG" + args[0] + "-" + args[2] + "-instanceIG" + args[1] + "-" + args[3]);

		for (int j = 0; j < IG_size; j++) {
			writer.write(j + " " + mappingResult[j] + "\n");

		}

		for (int j = 0; j < IG_size - 1; j++) {
			writer.write(j + " " + (j + 1) + "\n" + mappingResult[j] + " " + mappingResult[j + 1] + "\n");
		}

		writer.close();

	}

}
