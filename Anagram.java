/** Functions for checking if a given string is an anagkram. */
public class Anagram {
	public static void main(String args[]) {
		// Tests the isAnagram function.
		System.out.println(isAnagram("silent", "listen"));  // true
		System.out.println(isAnagram("William Shakespeare", "I am a weakish speller")); // true
		System.out.println(isAnagram("Madam Curie", "Radium came")); // true
		System.out.println(isAnagram("Tom Marvolo Riddle", "I am Lord Voldemort")); // true

		System.out.println(preProcess("What? No way!!!"));

		System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");

		String str = "1234567";
		Boolean pass = true;
		for (int i = 0; i < 10; i++) {
			String randomAnagram = randomAnagram(str);
			System.out.println(randomAnagram);
			pass = pass && isAnagram(str, randomAnagram);
			if (!pass) break;
		}
		System.out.println(pass ? "test passed" : "test Failed");
	}

	private static String preProcess(String str) {
		StringBuilder sb = new StringBuilder();

		for (char c : str.toCharArray()) {
			if (Character.isLetter(c)) {
				sb.append(Character.toLowerCase(c));
			}
		}

		return sb.toString();
	}




		// Returns true if the two given strings are anagrams, false otherwise.
		public static boolean isAnagram(String str1, String str2) {
			str1 = str1.replaceAll(" ", "").toLowerCase();
			str2 = str2.replaceAll(" ", "").toLowerCase();

			if (str1.length() != str2.length()) {
				return false;
			}

			for (char c = 'a'; c <= 'z'; c++) {
				if (countOccurrences(str1, c) != countOccurrences(str2, c)) {
					return false;
				}
			}

			return true;
		}

		private static int countOccurrences(String str, char c) {
			int count = 0;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == c) {
					count++;
				}
			}
			return count;
		}

		// Returns a random anagram of the given string.
		public static String randomAnagram(String str) {
			str = str.replaceAll(" ", "");
			StringBuilder result = new StringBuilder(str);

			for (int i = 0; i < str.length(); i++) {
				int j = pseudoRandom(i, str.length());

				char temp = result.charAt(i);
				result.setCharAt(i, result.charAt(j));
				result.setCharAt(j, temp);
			}

			return result.toString();
		}

		private static int pseudoRandom(int seed, int limit) {
			long time = System.currentTimeMillis();
			long random = (time + seed * 31) % limit;
			return (int) Math.abs(random);
		}


	}



