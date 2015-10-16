package main;

public class LPS {

	public static void main(String[] args) {

		String lps = lps("utulsa".toLowerCase());
		System.out.println(lps);
	}

	/**
	 * Recursive function
	 * @author Saeid Samadidana
	 * @param str
	 *            the string to get longest its' palidrome
	 * @return longest palidrome of a given string
	 */

	public static String lps(String str) {
		if (str == null || str.equals(""))
			return str;
		else {
			if (str.length() == 1)
				return str;
			else {
				if (str.charAt(0) == str.charAt(str.length() - 1))
					return str.charAt(0) + lps(str.substring(1, str.length() - 1)) + str.charAt(0);
				else {
					String leftLps = lps(str.substring(0, str.length() - 1));
					String rightLps = lps(str.substring(1, str.length()));

					if (leftLps.length() > rightLps.length())
						return leftLps;
					else
						return rightLps;
				}
			}
		}
	}
}