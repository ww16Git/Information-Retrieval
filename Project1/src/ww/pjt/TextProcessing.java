package ww.pjt;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TextProcessing {

	/*
	 * Write a method/function that reads in a text file and returns a list of
	 * the tokens in that file. For the purposes of this project, a token is a
	 * sequence of alphanumeric characters, independent of capitalization (so
	 * Apple, apple are the same token). Method: List<Token>
	 * tokenizeFile(TextFile)
	 */
	public List<String> tokenizeFile(File file) {
		// data structure (ArrayList) to hold tokens, ArrayList is faster than
		// Vector
		List<String> tokenList = new ArrayList<String>();

		// check whether file exists
		if (file.exists()) {

			try {
				// read each line from file
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader bf = new BufferedReader(isr);

				String line = "";
				while ((line = bf.readLine()) != null) {
					if (!line.startsWith("DOCID")&&!line.startsWith("URL")) {
						// replace all characters not a-zA-Z0-9 to blank
						line = line.replaceAll("[^a-zA-Z0-9]", " ");
						// get tokens from line
						StringTokenizer tokenizer = new StringTokenizer(line);
						while (tokenizer.hasMoreTokens()) {
							// change tokens to lowercase
							tokenList.add(tokenizer.nextToken().toLowerCase());
						}
					}

				}
				bf.close();
				isr.close();
				fis.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return tokenList;

	}

	/*
	 * Write another method that prints out a list of tokens onto the screen.
	 * Method: void print(List<Token>)
	 */
	public void print(List<String> tokenList) {
		System.out.println("This is the token list:");
		for (int i = 0; i < tokenList.size(); i++) {
			System.out.println(tokenList.get(i));
		}
	}

	/*
	 * Write a method/function that counts the total number of words and their
	 * frequencies in a token list.
	 */
	public HashMap<String, Integer> computeWordFrequencies(List<String> tokenList) {

		HashMap<String, Integer> wordFrequenciesMap = new HashMap<String, Integer>();
		// initialize the HashMap with value 0
		for (int i = 0; i < tokenList.size(); i++) {
			wordFrequenciesMap.put(tokenList.get(i), 0);
		}
		// count the number of frequency for each token
		for (int i = 0; i < tokenList.size(); i++) {
			wordFrequenciesMap.put(tokenList.get(i), wordFrequenciesMap.get(tokenList.get(i)) + 1);
		}

		return wordFrequenciesMap;

	}

	/*
	 * Write another method that prints out the word frequency counts onto the
	 * screen. The print out should be ordered by decreasing frequency. (so,
	 * highest frequency words first) Method: void print(Pairs<Token,Count>)
	 */
	public void print(HashMap<String, Integer> hashmap) {
		// Sort£ºcreate two lists, one for keyList and one for valueList

		List<String> keyList = new ArrayList<String>();
		keyList.addAll(hashmap.keySet());
		List<Integer> valueList = new ArrayList<Integer>();
		valueList.addAll(hashmap.values());

		// BubbleSorting
		for (int i = 0; i < valueList.size(); i++) {
			for (int j = 1; j < valueList.size() - i; j++) {
				if (valueList.get(j) > valueList.get(j - 1)) {
					int tempValue = valueList.get(j);
					valueList.set(j, valueList.get(j - 1));
					valueList.set(j - 1, tempValue);

					String tempKey = keyList.get(j);
					keyList.set(j, keyList.get(j - 1));
					keyList.set(j - 1, tempKey);

				}
			}
		}

		// print the key-value pair list
		for (int i = 0; i < valueList.size(); i++) {
			System.out.println(keyList.get(i) + " " + valueList.get(i));

		}
	}

	public void print(HashMap<String, Integer> hashmap, int num) {
		// Sort£ºcreate two lists, one for keyList and one for valueList

		List<String> keyList = new ArrayList<String>();
		keyList.addAll(hashmap.keySet());
		List<Integer> valueList = new ArrayList<Integer>();
		valueList.addAll(hashmap.values());

		// BubbleSorting
		for (int i = 0; i < valueList.size(); i++) {
			for (int j = 1; j < valueList.size() - i; j++) {
				if (valueList.get(j) > valueList.get(j - 1)) {
					int tempValue = valueList.get(j);
					valueList.set(j, valueList.get(j - 1));
					valueList.set(j - 1, tempValue);

					String tempKey = keyList.get(j);
					keyList.set(j, keyList.get(j - 1));
					keyList.set(j - 1, tempKey);

				}
			}
		}

		// print the key-value pair list
		for (int i = 0; i < num; i++) {
			System.out.println(keyList.get(i) + " " + valueList.get(i));

		}
	}

	/*
	 * A 3-gram is three words that occur consecutively in a file. For example,
	 * three words that, and words that occur are all 3-grams from the previous
	 * sentence. Write a method/function that counts the total number of 3-grams
	 * and their frequencies in a token list. Method: Pairs<3Gram,Count>
	 * computeThreeGramFrequencies(List<Token>)
	 */
	public HashMap<List<String>, Integer> computeThreeGramFrequencies(List<String> tokenList) {
		// create 3GramRreqMap and 3Gram
		HashMap<List<String>, Integer> threeGramFrequenciesMap = new HashMap<List<String>, Integer>();
		List<List<String>> threeGramLists = new ArrayList<List<String>>();

		// departs tokenList into 3GramList
		for (int i = 0; i < tokenList.size() - 2; i++) {
			List<String> threeGrams = new ArrayList<String>();
			threeGrams.add(tokenList.get(i));
			threeGrams.add(tokenList.get(i + 1));
			threeGrams.add(tokenList.get(i + 2));
			threeGramLists.add(threeGrams);
		}

		// initialize HashMap key-value
		for (int i = 0; i < tokenList.size() - 2; i++) {
			threeGramFrequenciesMap.put(threeGramLists.get(i), 0);
		}

		// update HashMap value according to the count
		for (int i = 0; i < tokenList.size() - 2; i++) {
			List<String> threeGrams = new ArrayList<String>();
			threeGrams.add(tokenList.get(i));
			threeGrams.add(tokenList.get(i + 1));
			threeGrams.add(tokenList.get(i + 2));
			threeGramFrequenciesMap.put(threeGramLists.get(i), threeGramFrequenciesMap.get(threeGramLists.get(i)) + 1);
		}

		return threeGramFrequenciesMap;

	}

	/*
	 * Write another method that prints out the 3-gram frequency counts onto the
	 * screen. The print out should be ordered by decreasing frequency. (so,
	 * highest frequency 3- grams first) Method: void print(Pairs<3Gram,Count>)
	 */
	public void threeGramsPrint(HashMap<List<String>, Integer> hashmap) {
		List<List<String>> keyList = new ArrayList<List<String>>();
		keyList.addAll(hashmap.keySet());
		List<Integer> valueList = new ArrayList<Integer>();
		valueList.addAll(hashmap.values());

		// BubblingSort
		for (int i = 0; i < valueList.size(); i++) {
			for (int j = 1; j < valueList.size() - i; j++) {
				if (valueList.get(j) > valueList.get(j - 1)) {
					int tempValue = valueList.get(j);
					valueList.set(j, valueList.get(j - 1));
					valueList.set(j - 1, tempValue);

					List<String> tempKey = keyList.get(j);
					keyList.set(j, keyList.get(j - 1));
					keyList.set(j - 1, tempKey);

				}
			}
		}

		// print
		for (int i = 0; i < valueList.size(); i++) {
			System.out.println(keyList.get(i) + " " + valueList.get(i));

		}

	}

	public void threeGramsPrint(HashMap<List<String>, Integer> hashmap, int num) {
		List<List<String>> keyList = new ArrayList<List<String>>();
		keyList.addAll(hashmap.keySet());
		List<Integer> valueList = new ArrayList<Integer>();
		valueList.addAll(hashmap.values());

		// BubblingSort
		for (int i = 0; i < valueList.size(); i++) {
			for (int j = 1; j < valueList.size() - i; j++) {
				if (valueList.get(j) > valueList.get(j - 1)) {
					int tempValue = valueList.get(j);
					valueList.set(j, valueList.get(j - 1));
					valueList.set(j - 1, tempValue);

					List<String> tempKey = keyList.get(j);
					keyList.set(j, keyList.get(j - 1));
					keyList.set(j - 1, tempKey);

				}
			}
		}

		// print
		for (int i = 0; i < num; i++) {
			System.out.println(keyList.get(i) + " " + valueList.get(i));

		}

	}

	/*
	 * An anagram is the result of rearranging the letters of a word [or phrase]
	 * to produce a new word [or phrase]. For example: silent is an anagram of
	 * listen. Write a method/function that detects all tokens that have
	 * anagrams in a list of tokens, and lists those anagrams. Method:
	 * Pairs<Token,List<Anagram>> detectAnagrams(List<Token>)
	 */
	public HashMap<String, List<String>> detectAnagrams(List<String> tokenlist) {
		HashMap<String, List<String>> anagramList = new HashMap<String, List<String>>();
		// delete same tokens
		List<String> uniqueTokenList = new ArrayList<String>();
		for (int i = 0; i < tokenlist.size(); i++) {
			String str = tokenlist.get(i);
			if (!uniqueTokenList.contains(str)) {
				uniqueTokenList.add(str);
			}
		}

		// find all anagrams
		for (int i = 0; i < uniqueTokenList.size(); i++) {
			// create list to hold anagram for every tokens
			List<String> anagram = new ArrayList<String>();
			// exchange to char array
			char tokenArrayA[] = uniqueTokenList.get(i).toCharArray();
			// compare with other tokens
			for (int j = 0; j < uniqueTokenList.size(); j++) {
				if (j != i) {
					char tokenArrayB[] = uniqueTokenList.get(j).toCharArray();
					if (tokenArrayB.length == tokenArrayA.length) {
						int letters[] = new int[128];

						// initialize
						for (int k = 0; k < letters.length; k++) {
							letters[k] = 0;
						}
						// add tokenArrayA into letters
						for (int k = 0; k < tokenArrayA.length; k++) {
							letters[tokenArrayA[k]]++;
						}
						// delete tokenArrayB into letters
						for (int k = 0; k < tokenArrayB.length; k++) {
							letters[tokenArrayB[k]]--;
						}

						// check all 0
						boolean check = true;
						for (int e = 0; e < letters.length; e++) {
							if (letters[e] != 0) {
								check = false;
							}
						}
						if (check == true) {
							anagram.add(uniqueTokenList.get(j));
							anagramList.put(uniqueTokenList.get(i), anagram);

						}
					}
				}
			}
		}
		return anagramList;
	}

	// Write another method that prints out the tokens and their anagrams onto
	// the
	// screen. The print out should be ordered alphabetically by token.
	public void printAnagramList(HashMap<String, List<String>> anagramList) {
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(anagramList.keySet());
		List<List<String>> valueList = new ArrayList<List<String>>();
		valueList.addAll(anagramList.values());

		for (int i = 0; i < valueList.size(); i++) {
			System.out.println(keyList.get(i) + " " + valueList.get(i));

		}
	}

	public static void main(String[] args) {
		// user input file name
		System.out.println("Please input file name:");
		Scanner scanner = new Scanner(System.in);
		String filename = scanner.nextLine();
		scanner.close();

		// create file
		File file = new File(filename);

		// create TestProcessing object
		TextProcessing tp = new TextProcessing();

		// utilization
		List<String> tokenList = new ArrayList<String>();
		tokenList = tp.tokenizeFile(file);
		System.out.println("Print token list:");
		tp.print(tokenList);
		System.out.println("");

		// frequency
		HashMap<String, Integer> wordFrequenciesMap = tp.computeWordFrequencies(tokenList);
		System.out.println("Print frequency list:");
		tp.print(wordFrequenciesMap);
		System.out.println("");

		// 3grams
		HashMap<List<String>, Integer> threeGramFrequencies = tp.computeThreeGramFrequencies(tokenList);

		System.out.println("Print 3grams list:");
		tp.threeGramsPrint(threeGramFrequencies);
		System.out.println("");
		//
		// anagram
		// HashMap<String, List<String>> detectAnagrams =
		// tp.detectAnagrams(tokenList);
		//
		// System.out.println("Print anagram list:");
		// tp.printAnagramList(detectAnagrams);
		// System.out.println("");
	}

}
