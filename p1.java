package Pattern;

import java.util.ArrayList;

public class Assignment1 {

	public void computeDistance(String text, String pattern) {

		int len1 = pattern.length();
		int len2 = text.length();
		int editDistance[][] = new int[len1 + 1][len2 + 1];

		ArrayList<Character>[][] pathMatrix = new ArrayList[len1][len2];
		for (int row = 0; row < pathMatrix.length; row++) {
			for (int col = 0; col < pathMatrix[row].length; col++) {
				pathMatrix[row][col] = new ArrayList<Character>();
			}
		}

		editDistance[0][0] = 0;

		for (int row = 1; row <= len1; row++) {
			editDistance[row][0] = Integer.MAX_VALUE;
		}

		for (int col = 1; col <= len2; col++) {
			editDistance[0][col] = Integer.MAX_VALUE;
		}

		for (int row = 1; row <= len1; row++) {
			for (int col = 1; col <= len2; col++) {

				if (pattern.charAt(row - 1) == text.charAt(col - 1)) {
					editDistance[row][col] = Math.min(editDistance[row - 1][col - 1], editDistance[row][col - 1]);
					editDistance[row][col] = Math.min(editDistance[row - 1][col], editDistance[row][col]);
					if (row == 1 && col !=1 ) {
						if (editDistance[row][col] == editDistance[row - 1][col - 1]) {
							pathMatrix[row-1][col-1].add('R');
						}

						if (editDistance[row][col] == editDistance[row - 1][col]) {
							pathMatrix[row-1][col-1].add('I');
						}

						if (editDistance[row][col] == editDistance[row][col - 1]) {
							pathMatrix[row-1][col-1].add('D');
						}
					} else {
						pathMatrix[row-1][col-1].add('N');
					}

				} else {
					editDistance[row][col] = Math.min(editDistance[row - 1][col - 1], editDistance[row][col - 1]);
					editDistance[row][col] = Math.min(editDistance[row - 1][col], editDistance[row][col]);
					editDistance[row][col] = editDistance[row][col] + 1;
					if (editDistance[row][col] - 1 == editDistance[row - 1][col - 1]) {
						pathMatrix[row-1][col-1].add('R');
					}
					if (editDistance[row][col] - 1 == editDistance[row - 1][col]) {
						pathMatrix[row-1][col-1].add('I');
					}
					if (editDistance[row][col] - 1 == editDistance[row][col - 1]) {
						pathMatrix[row-1][col-1].add('D');
					}
				}
			}
		}
		for (int row = 0; row < pathMatrix.length; row++) {
			for (int col = 0; col < pathMatrix[row].length; col++) {
				// for(char c:pathMatrix[row][col] )
				System.out.print(pathMatrix[row][col]);
			}
			System.out.println("");
		}
		for (int row = 1; row < editDistance.length; row++) {
			for (int col = 1; col < editDistance[row].length; col++) {
				// for(char c:pathMatrix[row][col] )
				System.out.print(editDistance[row][col]);
			}
			System.out.println("");
		}
		System.out.println(editDistance[len1][len2]);
		String finalPath ="";
		traversePath(pathMatrix,editDistance, len1-1, len2-1, finalPath);
		//System.out.println(finalPath);

		// System.out.print(traversePath(pathMatrix,len1-1,len2-1));
	}

	public void traversePath(ArrayList<Character>[][] pathMatrix, int [][] editDistance,int row, int col, String finalPath) {
		if (row == -1 || col == -1) {
			System.out.println(finalPath);
			return;
		}
		ArrayList<Character> path = pathMatrix[row][col];
		// System.out.print(path);
		for (int k = 0; k < path.size(); k++) {
			String curr = finalPath;
			char c = path.get(k);
		//	finalPath.append(path.get(k));
			if(c != 'N') {
				curr = c + finalPath;
			}
			if (row == 0 && editDistance[row+1][col+1]<=1) {
				System.out.println(curr);
				return;
			}
			if (c == 'R' || c == 'N') {
				
				traversePath(pathMatrix,editDistance, row-1, col-1, curr);
			//	row = row - 1;
			//	col = col - 1;
			} else if (c == 'I') {
				traversePath(pathMatrix,editDistance, row-1, col, curr);

			//	row = row - 1;
			} else if (c == 'D') {
				traversePath(pathMatrix,editDistance, row, col-1, curr);
			//	col = col - 1;
			}
		//	traversePath(pathMatrix,editDistance, row, col, curr);
		}
		// System.out.println(finalPath.reverse().toString());
		// return finalPath.reverse().toString();
	}

	public static void main(String args[]) {
		Assignment1 ob = new Assignment1();
		ob.computeDistance("paris", "alice");
	}

}

// return word.matches("[A-Z]+|[a-z]+|[A-Z][a-z]+");
