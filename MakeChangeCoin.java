package main;

import java.util.Arrays;

/**
 * 
 * @author Saeid Samadidana
 * @version 1.0
 *
 */
public class MakeChangeCoin {

	public static void main(String[] args) {

		/**
		 * Just a simple example
		 */
		int money = 36;
		int[] denominations = { 1, 10, 25 };
		minChange(money, denominations);

	}

	public static int minChange(int money, int[] denominations) {

		int denomCount = denominations.length;

		int[][] matrix = new int[denomCount][money];
		for (int j = 0; j < money; j++)
			matrix[0][j] = j + 1;
		for (int i = 0; i < denomCount; i++)
			matrix[i][0] = 1;

		for (int d = 1; d < denomCount; d++) {// do for each d in denominations

			for (int j = 1; j < money; j++) { // j denotes current Amount

				int currentMoney = j + 1;// just for more simplicity
				int currentCoin = denominations[d];// just for more simplicity

				if (currentMoney < currentCoin)
					// money amount (currentMoney) is less that this coin.
					// Therefore the
					// coin required does not change
					// even with this coin
					matrix[d][j] = matrix[d - 1][j];
				else if (currentMoney % currentCoin == 0)
					// money amount(currentMoney) is a multiply of this coin
					// then it is obvious that minimum number of coins required
					// is currentMoney/currentCoin
					matrix[d][j] = currentMoney / currentCoin;
				else
					// money (currentMoney) can be changes with or without
					// current coin.
					// Therefore here we compare with previous coin result
					matrix[d][j] = Math.min(matrix[d - 1][j], 1 + matrix[d][currentMoney - currentCoin - 1]);

			}
		}

		printMatrix(matrix);

		return matrix[denomCount - 1][money - 1];

	}

	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}

}
