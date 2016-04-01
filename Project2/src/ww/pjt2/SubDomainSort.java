package ww.pjt2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * This class is for question3
 * How many subdomains did you find? Submit the list of subdomains ordered
	alphabetically and the number of unique pages detected in each subdomain.
	The file should be called Subdomains.txt, and its content should be lines
	containing
	URL, number
	http://vision.ics.uci.edu, 10 ( not the actual number here)
	etc.
 */
public class SubDomainSort {
	public static void main(String args[]) {
		try {
			// read from file "SubDomain.txt"
			FileReader fr = new FileReader("SubDomain.txt");
			BufferedReader bf = new BufferedReader(fr);
			String line = "";

			List<String> subDomainList = new ArrayList<String>();

			while ((line = bf.readLine()) != null) {
				subDomainList.add(line);
			}

			bf.close();
			fr.close();

			
			//create hashmap for statistic and sort
			HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
			
			for (int i = 0; i < subDomainList.size(); i++) {
				hashmap.put(subDomainList.get(i), 0);
			}

			for (int i = 0; i < subDomainList.size(); i++) {
				hashmap.put(subDomainList.get(i), hashmap.get(subDomainList.get(i)) + 1);
			}

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
			
			
			//create file "subDomain.txt"
			FileWriter fw = new FileWriter("SubDomains.txt");
			PrintWriter pw = new PrintWriter(fw, true);

			// print the key-value pair list
			for (int i = 0; i < valueList.size(); i++) {
				System.out.println(keyList.get(i) + " " + valueList.get(i));
				pw.println(keyList.get(i) + " " + valueList.get(i));
			}

			pw.close();
			fw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
