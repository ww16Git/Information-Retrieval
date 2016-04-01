package ww.pjt2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ww.pjt.TextProcessing;
/*
 * This class is for question 5 and 6
 * What are the 500 most common words in this domain? (Ignore English stop
words, which can be found, for example, here) Submit the list of the 500
most common words ordered by frequency.

What are the 20 most common 3-grams? (again ignore English stop words) A
2-gram, in this case, is a sequence of 2 words that aren¡¯t stop words and that
haven¡¯t had a stop word in between them. Submit the list of 20 2-grams
ordered by frequency.
 */
public class CommonWord {
	List<String> StopWordList = new ArrayList<String>();

	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader("StopWord.txt");
			BufferedReader bf = new BufferedReader(fr);

			List<String> StopWordList = new ArrayList<String>();
			String line = "";
			while ((line = bf.readLine()) != null) {
				StopWordList.add(line);
			}
			bf.close();
			fr.close();

			TextProcessing tp = new TextProcessing();
			File file = new File("AllText.txt");
			List<String> beforeProcessList = tp.tokenizeFile(file);

			List<String> afterProcessList = new ArrayList<String>();
			for (int i = 0; i < beforeProcessList.size(); i++) {
				if (!StopWordList.contains(beforeProcessList.get(i))) {
					afterProcessList.add(beforeProcessList.get(i));
				}
			}

			// top 500 tokens
			HashMap<String, Integer> hashmap = tp.computeWordFrequencies(afterProcessList);
			
			tp.print(hashmap,500);
			
			
			//top 20 three grams
			HashMap<List<String>, Integer> threeGramFrequenciesMap = tp.computeThreeGramFrequencies(afterProcessList);
			
			tp.threeGramsPrint(threeGramFrequenciesMap, 20);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
