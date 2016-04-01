package ww.pjt2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ww.pjt.TextProcessing;
/*
 * This class if for question 4
 * What is the longest page in terms of number of words? (HTML markup
doesn¡¯t count as words)
 */
public class FindLongestPage {
	public static void main(String args[]) {
		// create dir
		File dir = new File("Pages");
		FindLongestPage flp = new FindLongestPage();
		File longestPage = flp.findLongest(dir);
		System.out.println(longestPage.getName());
	}

	/*
	 * traverse all docs in dir and return the longest one
	 */
	public File findLongest(File dir) {
		int longest = 0;
		File longestFile = null;
		//judgement on the dir
		if (dir.isDirectory()) {
			File next[] = dir.listFiles();
			for (int i = 0; i < next.length; i++) {
				TextProcessing tp = new TextProcessing();

				List<String> tokenList = new ArrayList<String>();
				tokenList = tp.tokenizeFile(next[i]);
				if (tokenList.size() > longest) {
					longestFile = next[i];
				}
			}
		}else{
			System.err.println("File is not a dir");
		}
		return longestFile;
	}
}
