package Pattern;

/**
 * 
 * @author Ruchika Garg
 * @studentid 1430065
 * @email rgarg@scu.edu
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class P1 {
	
	private static int sequenceNo = 1;
	private String text = null;
	private String pattern = null;
	private String validString = "[a-zA-Z]+";
	
/** 
 * method for computation of minimum distance between two strings and their operation sequence 
 **/	
	
	public void computeDistance() {
		
		if (text == null || pattern == null) {
			System.out.println("Error : String can't be null");
			System.exit(0);
		} else if (!text.matches(validString)) {
			System.out.println("Error : Not a Valid String");
			System.exit(0);
		} else if (!pattern.matches(validString)) {
			System.out.println("Error : Not a Valid String");
			System.exit(0);
		}

		int len1 = pattern.length();
		int len2 = text.length();
		int distanceMatrix[][] = new int[len1 + 1][len2 + 1];
		@SuppressWarnings("unchecked")
		ArrayList<String>[][] operationsMatrix = new ArrayList[len1+1][len2+1];  /** Creating 2D matrix of ArrayList of ArrayList **/
		for (int row = 0; row < operationsMatrix.length; row++) {
			for (int col = 0; col < operationsMatrix[row].length; col++) {
				operationsMatrix[row][col] = new ArrayList<String>();
			}
		}

		distanceMatrix[0][0] = 0;			/*** Initialization of distance matrix ***/
		for (int row = 1; row <= len1; row++) {
			distanceMatrix[row][0] = row;
		}
		for (int col = 1; col <= len2; col++) {
			distanceMatrix[0][col] = col;
		}
		operationsMatrix[0][0].add("None");		/*** Initialization of operations matrix ***/
		for (int row = 1; row <= len1; row++) {
			operationsMatrix[row][0].add("Insert");
		}
		for (int col = 1; col <= len2; col++) {
			operationsMatrix[0][col].add("Delete");
		}
		
		/*** calculating minimum distance and operation matrix ***/	
		
		for (int row = 1; row <= len1; row++) {     
			for (int col = 1; col <= len2; col++) {
				if (pattern.charAt(row - 1) == text.charAt(col - 1)) {
					distanceMatrix[row][col] = distanceMatrix[row - 1][col - 1];
						operationsMatrix[row][col].add("None");
				} else {
					distanceMatrix[row][col] = Math.min(distanceMatrix[row - 1][col - 1], distanceMatrix[row][col - 1]);
					distanceMatrix[row][col] = Math.min(distanceMatrix[row - 1][col], distanceMatrix[row][col]);
					distanceMatrix[row][col] = distanceMatrix[row][col] + 1;
					if (distanceMatrix[row][col] - 1 == distanceMatrix[row - 1][col - 1]) {
						operationsMatrix[row][col].add("Replace");
					}
					if (distanceMatrix[row][col] - 1 == distanceMatrix[row - 1][col]) {
						operationsMatrix[row][col].add("Insert");
					}
					if (distanceMatrix[row][col] - 1 == distanceMatrix[row][col - 1]) {
						operationsMatrix[row][col].add("Delete");
					}
				}
			}
		}		
		System.out.println("Number Of operations : " + distanceMatrix[len1][len2]);
		String finalPath = "";
		traversePath(operationsMatrix,distanceMatrix, len1, len2, finalPath);		
	}
	
/** 
* method for backtracking of the matrix to find the minimum paths and printing all the paths
**/
	
	public void traversePath(ArrayList<String>[][] operationsMatrix, int [][] distanceMatrix, int row, int col, String finalPath) {
		if (distanceMatrix[row][col] == 0) {
			System.out.println(sequenceNo++ + ") " + text + " "+ finalPath);
			return;
		}
		ArrayList<String> multiplePaths = operationsMatrix[row][col];
		for (int k = 0; k < multiplePaths.size(); k++) {
			String curr = finalPath;
			String c = multiplePaths.get(k);
			if(c != "None") {
				if(c == "Delete") {
						curr = " " + c + " " + text.charAt(col-1) + " --> " + pattern.substring(0,row) + text.substring(col) + finalPath;
				} else if (c == "Replace") {
					curr = " " + c + " " + text.charAt(col-1) + " by " + pattern.charAt(row-1) + " --> " + pattern.substring(0,row) + text.substring(col) + finalPath;
				} else {
					curr = " "+ c + " " + pattern.charAt(row-1) + " --> " + pattern.substring(0,row) + text.substring(col) + finalPath;
				}
			}
			if (distanceMatrix[row][col]<=0) {				
				System.out.println(sequenceNo++ + ") "+ text + curr);
				return;
			}
			if (c == "Replace" || c == "None") {
				traversePath(operationsMatrix, distanceMatrix, row-1, col-1, curr);
		
			} else if (c == "Insert") {
				traversePath(operationsMatrix, distanceMatrix, row-1, col, curr);
		
			} else if (c == "Delete") {
				traversePath(operationsMatrix, distanceMatrix, row, col-1, curr);		
			}
		}		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s1 = input.readLine();
		String s2 = input.readLine();
		String temp = input.readLine();
		if ( temp != null && !temp.isEmpty() ) {
			System.out.println("Not a valid Input !! Only two strings should be there!!");
			System.exit(0);
		}		
		P1 object = new P1();
		object.text = s1;
		object.pattern = s2;
		object.computeDistance();
	}
}

