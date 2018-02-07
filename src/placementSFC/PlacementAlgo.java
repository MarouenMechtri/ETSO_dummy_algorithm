package placementSFC;

import java.io.IOException;

import dummy_algo.dummy_algo;



public class PlacementAlgo {

	
	
	public static void rundummyalgo(int nbNodeRG, int nbServerIG, int indexRG, int indexIG) throws IOException {
		String[] argdummy = { String.valueOf(nbNodeRG), String.valueOf(nbServerIG), String.valueOf(indexRG),
				String.valueOf(indexIG) };
		dummy_algo.main(argdummy);
	}
		
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		int nbNodeRG=-1, nbServerIG=-1;
		int indexRG=-1, indexIG=-1;
		boolean dummy = false;
		

		for (int i = 0; i < args.length; i++) {
			String[] params = args[i].split("=");
			switch (params[0]) {
			case "indexIG":
				System.out.println(params[0] + " " + params[1]);
				indexIG = Integer.parseInt(params[1]);
				break;
			case "nbServerIG":
				nbServerIG = Integer.parseInt(params[1]);
				System.out.println(params[0] + " " + params[1]);
				break;
			case "indexRG":
				System.out.println(params[0] + " " + params[1]);
				indexRG = Integer.parseInt(params[1]);
				break;
			case "nbNodeRG":
				nbNodeRG = Integer.parseInt(params[1]);
				System.out.println(params[0] + " " + params[1]);
				break;
			case "dummy":
				System.out.println(params[0] + " " + params[1]);
				dummy = Boolean.parseBoolean(params[1]);
				break;
			
				
			default:
				break;
			}
		}

		if (dummy) {
			rundummyalgo(nbNodeRG, nbServerIG, indexRG, indexIG);
		}

	}

}