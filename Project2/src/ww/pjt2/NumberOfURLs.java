package ww.pjt2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
/*
 * This class is for quesiton 2
 * How many unique pages did you find in the entire domain? (Uniqueness is
	established by the URL)
 */
public class NumberOfURLs {

	public static void main(String[] args) {
		try {
			// create Set
			Set<String> URLset = new TreeSet<String>();

			// read file
			FileReader fr = new FileReader("URL.txt");
			BufferedReader bf = new BufferedReader(fr);
			String line = "";
			while ((line = bf.readLine()) != null) {
				URLset.add(line);
			}
			bf.close();
			fr.close();

			// count number
			System.out.println("Total number of unique URLs is " + URLset.size());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
