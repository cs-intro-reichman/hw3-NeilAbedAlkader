/** Functions for checking if a given string is an anagram. */
public class Anagram {
	public static void main(String args[]) {
		// Tests the isAnagram function.
		System.out.println(isAnagram("silent", "listen")); // true
		System.out.println(isAnagram("William Shakespeare", "I am a weakish speller")); // true
		System.out.println(isAnagram("Madam Curie", "Radium came")); // true
		System.out.println(isAnagram("Tom Marvolo Riddle", "I am Lord Voldemort")); // true

		// Tests the preProcess function.
		System.out.println(preProcess("What? No way!!!"));

		// Tests the randomAnagram function.
		System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");

		// Performs a stress test of randomAnagram
		String str = "1234567";
		Boolean pass = true;
		//// 10 can be changed to much larger values, like 1000
		for (int i = 0; i < 10; i++) {
			String randomAnagram = randomAnagram(str);
			System.out.println(randomAnagram);
			pass = pass && isAnagram(str, randomAnagram);
			if (!pass)
				break;
		}
		System.out.println(pass ? "test passed" : "test Failed");
	}

	// Returns true if the two given strings are anagrams, false otherwise.
	public static boolean isAnagram(String str1, String str2) {
		String processedStr1 = removeWhitespace(preProcess(str1));
		String processedStr2 = removeWhitespace(preProcess(str2));

		if (processedStr1.length() != processedStr2.length()) {
			return false;
		}

		for (int i = 0; i < processedStr1.length(); i++) {
			char c = processedStr1.charAt(i);

			if (countOccurrences(c, processedStr1) != countOccurrences(c, processedStr2)) {
				return false;
			}
		}

		return true;
	}

	// Return the count of the times a given char appears in a string.
	public static int countOccurrences(char c, String str) {
		int count = 0;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == c) {
				count++;
			}
		}

		return count;
	}

	// Returns a preprocessed version of the given string: all the letter characters
	// are converted
	// to lower-case, and all the other characters are deleted, except for spaces,
	// which are left
	// as is. For example, the string "What? No way!" becomes "whatnoway"
	public static String preProcess(String str) {
		String result = "";

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isAlphabetic(c) || Character.isWhitespace(c)) {
				result += Character.toLowerCase(c);
			}
		}

		return result;
	}

	// This function is needed as we got a conflicting instruction of implementing
	// preProcess and the test for isAnagram
	public static String removeWhitespace(String str) {
		String result = "";

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!Character.isWhitespace(c)) {
				result += c;
			}
		}

		return result;
	}

	// Returns a random anagram of the given string. The random anagram consists of
	// the same
	// characters as the given string, re-arranged in a random order.
	public static String randomAnagram(String str) {
		String result = "";

		String strippedStr = str;

		for (int i = 0; i < str.length(); i++) {
			int randomIndex = (int) (Math.random() * strippedStr.length());
			result += strippedStr.charAt(randomIndex);

			// deletes the character from the string so that we will not select it again
			strippedStr = strippedStr.substring(0, randomIndex) + strippedStr.substring(randomIndex + 1);
		}

		return result;
	}
}
