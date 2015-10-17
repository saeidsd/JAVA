package main;

import java.util.Arrays;

/**
 * Implementation of Longest Palidrome Sequence
 * 
 * @author Saeid Samadidana
 * @version 1.0
 *
 */
public class LPS {

	public static void main(String[] args) {

		/**
		 * Recursive Approach
		 */
		String str = "character".toLowerCase();
		String lps_R = recursiveLPS(str);

		System.out.println(lps_R);

		/**
		 * Dynamic Approach
		 */

		int[][] lps_Matrix = dynamicProgramingLPS(str.toCharArray());
		char[] lps_DP = backtrackLPS(str, lps_Matrix);

		System.out.println(Arrays.toString(lps_DP));

	}

	/**
	 * Recursive function for creating Longest palidrome sequence
	 * 
	 * @author Saeid Samadidana
	 * @param str
	 *            the string to get longest its' palidrome
	 * @return longest palidrome of a given string
	 */

	public static String recursiveLPS(String str) {
		if (str == null || str.equals(""))
			return str;
		else {
			if (str.length() == 1)
				return str;
			else {
				if (str.charAt(0) == str.charAt(str.length() - 1))
					return str.charAt(0) + recursiveLPS(str.substring(1, str.length() - 1)) + str.charAt(0);
				else {
					String leftLps = recursiveLPS(str.substring(0, str.length() - 1));
					String rightLps = recursiveLPS(str.substring(1, str.length()));

					if (leftLps.length() > rightLps.length())
						return leftLps;
					else
						return rightLps;
				}
			}
		}
	}

	public static int[][] dynamicProgramingLPS(char[] charArray) {

		int[][] lpsMatrix = new int[charArray.length][charArray.length];

		/**
		 * Each letter a its' LPs. Therefore the diagonal values is set to 1
		 */
		for (int i = 0; i < charArray.length; i++) {
			lpsMatrix[i][i] = 1;
		}

		/**
		 * building the LPS matrix
		 */
		int k = 1;
		while (k < charArray.length) {
			int i = 0;
			int j = k;

			while (j < charArray.length) {
				if (charArray[i] == charArray[j]) {
					lpsMatrix[i][j] = 2 + lpsMatrix[i + 1][j - 1];
				} else {
					lpsMatrix[i][j] = Math.max(lpsMatrix[i + 1][j], lpsMatrix[i][j - 1]);
				}
				i++;
				j++;
			}
			k++;
		}

		return lpsMatrix;

	}

	private static char[] backtrackLPS(String str, int[][] lpsMatrix) {

		char[] lps = new char[lpsMatrix[0][lpsMatrix.length - 1]];
		int ind = lps.length - 1;
		int i = 0;
		int j = lpsMatrix.length - 1;

		while (i < lpsMatrix.length && j > 0) {

			if (lpsMatrix[i][j] == 1) {
				lps[ind] = str.charAt(j);
				break;
			} else if (lpsMatrix[i][j - 1] == lpsMatrix[i][j]) {
				j--;
			} else if (lpsMatrix[i + 1][j] == lpsMatrix[i][j]) {
				i++;
			} else if (lpsMatrix[i][j] == 2 + lpsMatrix[i + 1][j - 1]) {
				lps[ind] = str.charAt(j);
				lps[lps.length - 1 - ind] = str.charAt(j);
				i++;
				j--;
				ind--;
			}

		}
		return lps;
	}

}