package ww.pjt2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombineFile {

	public void combineDomain(String fileName) {
		HashMap<String, Integer> combine_domain = new HashMap<String, Integer>();
		File file = new File(fileName);
		ArrayList<String> domainList = new ArrayList<String>();
		ArrayList<Integer> numList = new ArrayList<Integer>();
		if (!file.exists()) {
			System.out.println("File does not exists");
		} else {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader bf = new BufferedReader(fr);
				String line = "";
				while ((line = bf.readLine()) != null) {
					String data[] = line.split(" : ");
					String domain = data[0];
					int number = Integer.parseInt(data[1]);
					domainList.add(domain);
					numList.add(number);
					
				}

				for (int i = 0; i < domainList.size(); i++) {
					combine_domain.put(domainList.get(i), 0);
				}

				for (int i = 0; i < domainList.size(); i++) {
					combine_domain.put(domainList.get(i), combine_domain.get(domainList.get(i)) + numList.get(i));
				}

				FileWriter fw = new FileWriter("combine answer.txt", true);
				PrintWriter pw = new PrintWriter(fw, true);
				
				List<String> keyList = new ArrayList<String>();
				keyList.addAll(combine_domain.keySet());
				List<Integer> valueList = new ArrayList<Integer>();
				valueList.addAll(combine_domain.values());
				
				
				ArrayList<String> combine = new ArrayList<String>();
				for (int i = 0; i < keyList.size(); i++) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append(keyList.get(i));
					strBuilder.append(" ");
					strBuilder.append(valueList.get(i));
					combine.add(strBuilder.toString());
				}
				
				
				List<String> keyList_Sort = new ArrayList<String>();
				List<String> valueList_Sort = new ArrayList<String>();
				
				Collections.sort(combine);
				for (int i = 0; i < combine.size(); i++) {
					String temp[] = combine.get(i).split(" ");
					keyList_Sort.add(temp[0]);
					valueList_Sort.add(temp[1]);
				}

				
				for (int i = 0; i < keyList.size(); i++) {
					pw.println(keyList_Sort.get(i)+" : "+valueList_Sort.get(i));
					
				}

				pw.close();
				fr.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	
	}

	
	
	public void combineToken(String fileName) {
		Map<String, Integer> combine_token = new HashMap<String, Integer>();
		File file = new File(fileName);
		ArrayList<String> tokenList = new ArrayList<String>();
		ArrayList<Integer> numList = new ArrayList<Integer>();
		if (!file.exists()) {
			System.out.println("File does not exists");
		} else {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader bf = new BufferedReader(fr);
				String line = "";
				while ((line = bf.readLine()) != null) {
					String data[] = line.split(" : ");
					String token = data[0];
					int number = Integer.parseInt(data[1]);
					tokenList.add(token);
					numList.add(number);
				}

				for (int i = 0; i < tokenList.size(); i++) {
					combine_token.put(tokenList.get(i), 0);
				}

				for (int i = 0; i < tokenList.size(); i++) {
					combine_token.put(tokenList.get(i), combine_token.get(tokenList.get(i)) + numList.get(i));
				}

				FileWriter fw = new FileWriter("combine answer.txt", true);
				PrintWriter pw = new PrintWriter(fw, true);
				
				List<String> keyList = new ArrayList<String>();
				keyList.addAll(combine_token.keySet());
				List<Integer> valueList = new ArrayList<Integer>();
				valueList.addAll(combine_token.values());
				
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
				
				for (int i = 0; i < 500; i++) {
					pw.println(keyList.get(i)+" : "+valueList.get(i));
					
				}

				pw.close();
				fr.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	
	}
	
	public void combineThreeGram(String fileName) {
		Map<String, Integer> combine_threegram = new HashMap<String, Integer>();
		File file = new File(fileName);
		ArrayList<String> threegramList = new ArrayList<String>();
		ArrayList<Integer> numList = new ArrayList<Integer>();
		if (!file.exists()) {
			System.out.println("File does not exists");
		} else {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader bf = new BufferedReader(fr);
				String line = "";
				while ((line = bf.readLine()) != null) {
					String data[] = line.split(" : ");
					StringBuilder str = new StringBuilder();
					
					String subdata[] = data[0].split(" ");
					str.append(subdata[0]);
					str.append(" ");
					str.append(subdata[1]);
					str.append(" ");
					str.append(subdata[2]);
					
					String threegram = str.toString();
					int number = Integer.parseInt(data[1]);
					threegramList.add(threegram);
					numList.add(number);
				}

				for (int i = 0; i < threegramList.size(); i++) {
					combine_threegram.put(threegramList.get(i), 0);
				}

				for (int i = 0; i < threegramList.size(); i++) {
					combine_threegram.put(threegramList.get(i), combine_threegram.get(threegramList.get(i)) + numList.get(i));
				}

				FileWriter fw = new FileWriter("combine answer.txt", true);
				PrintWriter pw = new PrintWriter(fw, true);
				
				List<String> keyList = new ArrayList<String>();
				keyList.addAll(combine_threegram.keySet());
				List<Integer> valueList = new ArrayList<Integer>();
				valueList.addAll(combine_threegram.values());
				
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
				
				for (int i = 0; i < 20; i++) {
					pw.println(keyList.get(i)+" : "+valueList.get(i));
					
				}
				

				pw.close();
				fr.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	
	}
	
	public static void main(String[] args) {
		CombineFile cb = new CombineFile();
		cb.combineDomain("domainTest.txt");
		cb.combineToken("tokenTest.txt");
		cb.combineThreeGram("threegramTest.txt");
		
	}

}
