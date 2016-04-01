package ww.pjt2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ww.pjt.TextProcessing;

public class Answer {
	
	
	
	
	
	
	
	public static int totalURL = 0;

	public static void main(String[] args) {

		
		tokenizeFile tokenizefile = new tokenizeFile("test.txt");
		tokenizefile.tokenize();
		
		
		
		try {
			// read stopword file
			FileReader fr_stop = new FileReader("StopWord.txt");
			BufferedReader bf_stop = new BufferedReader(fr_stop);

			List<String> StopWordList = new ArrayList<String>();
			String line_stop = "";
			while ((line_stop = bf_stop.readLine()) != null) {
				StopWordList.add(line_stop);
			}
			bf_stop.close();
			fr_stop.close();

			// read result file
			FileReader fr = new FileReader("wwPages.txt");
			BufferedReader bf = new BufferedReader(fr);

			List<String> domainList = new ArrayList<String>();
			List<String> urlList = new ArrayList<String>();
			String line = "";
			while ((line = bf.readLine()) != null) {
				if (line.startsWith("URL")) {
					String str[] = line.split(" ");
					String domain = str[1];
					totalURL++;
					URI uri = new URI(domain);

					domainList.add(uri.getHost());
					urlList.add(line);
				}

			}
			bf.close();
			fr.close();
		
			
			// total url
//			System.out.println(totalURL);

			// domain-pageNum for total page num
			HashMap<String, Integer> domain_pageNum = new HashMap<String, Integer>();

			for (int i = 0; i < domainList.size(); i++) {
				domain_pageNum.put(domainList.get(i), 0);
			}
			// count the number of frequency for each token
			for (int i = 0; i < domainList.size(); i++) {
				domain_pageNum.put(domainList.get(i), domain_pageNum.get(domainList.get(i)) + 1);
			}

			List<String> keyList = new ArrayList<String>();
			keyList.addAll(domain_pageNum.keySet());
			List<Integer> valueList = new ArrayList<Integer>();
			valueList.addAll(domain_pageNum.values());

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
			domain_pageNum.clear();
			// How many subdomains and sort
			for (int i = 0; i < valueList.size(); i++) {
				domain_pageNum.put(keyList.get(i), valueList.get(i));
			}

			// stop word process
			TextProcessing tp = new TextProcessing();
			File file = new File("wwPages.txt");
			List<String> beforeProcessList = tp.tokenizeFile(file);

			List<String> afterProcessList = new ArrayList<String>();
			for (int i = 0; i < beforeProcessList.size(); i++) {
				if (!StopWordList.contains(beforeProcessList.get(i))) {
					afterProcessList.add(beforeProcessList.get(i));
				}
			}

			// What is the longest page
			

			

			//从这里开始改，写入文件在下面
			HashMap<String, Integer> url_tokenNum = new HashMap<String, Integer>();
			for (int i = 0; i < urlList.size(); i++) {

				url_tokenNum.put(urlList.get(i), 0);

			}

			
			HashMap<String,String> url_content=new HashMap<>();
			StringBuilder sb = new StringBuilder();
			int count=0;
			try{
				FileReader reader=new FileReader("wwPages.txt");
				BufferedReader br = new BufferedReader(reader);
				String curr = br.readLine();
				String temp="";
				while(curr!=null){
					if(curr.contains("DOCID ")){
						temp=curr;
						curr=br.readLine();
					}
					else if(curr.contains("URL http")&&temp.contains("DOCID")){
						if(count!=0){
							url_content.put(urlList.get(count-1),sb.toString());
							sb = new StringBuilder();
							temp="";
							curr=br.readLine();
						}
						if(count==0){
							count++;
							temp="";
							curr=br.readLine();
						}
					}
					else if(curr.contains("URL http")&&!temp.contains("DOCID")){
						sb=sb.append(line);
						curr = br.readLine();
						temp="";
					}
					else {
						sb=sb.append(line);
						curr = br.readLine();
						temp="";
					}
				}
				if(line==null){
					url_content.put(urlList.get(urlList.size()-1),sb.toString());

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			
				

			// 500 most common words
			HashMap<String, Integer> word_freq = tp.computeWordFrequencies(afterProcessList);

			tp.print(word_freq, 13);

			// common 3-grams
			HashMap<List<String>, Integer> three_gram_freq = tp.computeThreeGramFrequencies(afterProcessList);

			tp.threeGramsPrint(three_gram_freq, 5);
			
			
			
			//write answer
			
			FileWriter fw = new FileWriter("answer.txt",true);
			PrintWriter pw = new PrintWriter(fw, true);
			
			pw.println("Answer for question1: How much time did it take to crawl the entire domain?");
			pw.println(totalURL);

			
			pw.println("");
			pw.println("Answer for question2: How many unique pages did you find in the entire domain? ");
			
			List<String> keyList_subdomain = new ArrayList<String>();
			keyList_subdomain.addAll(domain_pageNum.keySet());
			List<Integer> valueList_subdomain = new ArrayList<Integer>();
			valueList_subdomain.addAll(domain_pageNum.values());

			// BubbleSorting
			for (int i = 0; i < valueList_subdomain.size(); i++) {
				for (int j = 1; j < valueList_subdomain.size() - i; j++) {
					if (valueList_subdomain.get(j) > valueList_subdomain.get(j - 1)) {
						int tempValue = valueList_subdomain.get(j);
						valueList_subdomain.set(j, valueList_subdomain.get(j - 1));
						valueList_subdomain.set(j - 1, tempValue);

						String tempKey = keyList_subdomain.get(j);
						keyList_subdomain.set(j, keyList_subdomain.get(j - 1));
						keyList_subdomain.set(j - 1, tempKey);

					}
				}
			}

			// print the key-value pair list
			for (int i = 0; i < keyList_subdomain.size(); i++) {
				pw.println(keyList_subdomain.get(i) + " " + valueList_subdomain.get(i));

			}
			
			pw.println("");
			pw.println("Answer for question 4:What is the longest page in terms of number of words? ");
			//在这里写入文件
			
			
			
			pw.println("");
			pw.println("Answer for question 5: What are the 500 most common words in this domain? ");
			
			List<String> keyList_commonword = new ArrayList<String>();
			keyList_commonword.addAll(word_freq.keySet());
			List<Integer> valueList_commonword = new ArrayList<Integer>();
			valueList_commonword.addAll(word_freq.values());

			// BubbleSorting
			for (int i = 0; i < valueList_commonword.size(); i++) {
				for (int j = 1; j < valueList_commonword.size() - i; j++) {
					if (valueList_commonword.get(j) > valueList_commonword.get(j - 1)) {
						int tempValue = valueList_commonword.get(j);
						valueList_commonword.set(j, valueList_commonword.get(j - 1));
						valueList_commonword.set(j - 1, tempValue);

						String tempKey = keyList_commonword.get(j);
						keyList_commonword.set(j, keyList_commonword.get(j - 1));
						keyList_commonword.set(j - 1, tempKey);

					}
				}
			}

			// print the key-value pair list
			for (int i = 0; i < 500; i++) {
				pw.println(keyList_commonword.get(i) + " " + valueList_commonword.get(i));

			}

			
			
			pw.println("");
			pw.println("Answer for question 6: What are the 20 most common 3-grams?");
			List<List<String>> keyList_threegram = new ArrayList<List<String>>();
			keyList_threegram.addAll(three_gram_freq.keySet());
			List<Integer> valueList_threegram = new ArrayList<Integer>();
			valueList_threegram.addAll(three_gram_freq.values());

			// BubblingSort
			for (int i = 0; i < valueList_threegram.size(); i++) {
				for (int j = 1; j < valueList_threegram.size() - i; j++) {
					if (valueList_threegram.get(j) > valueList_threegram.get(j - 1)) {
						int tempValue = valueList_threegram.get(j);
						valueList_threegram.set(j, valueList_threegram.get(j - 1));
						valueList_threegram.set(j - 1, tempValue);

						List<String> tempKey = keyList_threegram.get(j);
						keyList_threegram.set(j, keyList_threegram.get(j - 1));
						keyList_threegram.set(j - 1, tempKey);

					}
				}
			}

			for (int i = 0; i < 20; i++) {
				pw.println(keyList_threegram.get(i) + " " + valueList_threegram.get(i));

			}

			pw.close();
			fw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
