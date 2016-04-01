package ww.pjt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {

	/*
	 * 计算score
	 */
	public static Double getScore(ArrayList<Double> vectors) {
		Double result = 0.0;
		for (int i = 0; i < vectors.size(); i++) {
			result += vectors.get(i);
		}
		return result;
	}

	/*
	 * 输出排在前面的docID
	 */
	public static ArrayList<Integer> ranking(HashMap<Integer, ArrayList<Double>> accumulator) {
		ArrayList<Integer> top = new ArrayList<Integer>();

		// Hashmap存doc-score,下一步排序
//		HashMap<Integer, Double> map = new HashMap<Integer, Double>();

		// 取keyList
		ArrayList<Integer> keyList = new ArrayList<Integer>();
		keyList.addAll(accumulator.keySet());

		// 取valueList
		ArrayList<ArrayList<Double>> valueList = new ArrayList<ArrayList<Double>>();
		valueList.addAll(accumulator.values());

		// 给valueList算score
		ArrayList<Double> scoreList = new ArrayList<Double>();
		for (int i = 0; i < valueList.size(); i++) {
			Double temp = getScore(valueList.get(i));
			scoreList.add(temp);
		}

		// BubblingSort
		for (int i = 0; i < scoreList.size(); i++) {
			for (int j = 1; j < scoreList.size() - i; j++) {
				if (scoreList.get(j) > scoreList.get(j - 1)) {
					double tempValue = scoreList.get(j);
					scoreList.set(j, scoreList.get(j - 1));
					scoreList.set(j - 1, tempValue);

					int tempKey = keyList.get(j);
					keyList.set(j, keyList.get(j - 1));
					keyList.set(j - 1, tempKey);

				}
			}
		}

		for (int i = 0; i < 5; i++) {
			top.add(keyList.get(i));
			// System.out.println("score:" + scoreList.get(i));
		}

		return top;
	}

	/*
	 * DBread
	 */

	public static ArrayList<Postings> DBread(String term) {
		String start = term.substring(0,1);
		
		String sql = "select * from "+start+" where term=?";
		PreparedStatement ps = null;
		DBConnect conn = null;
		
		ArrayList<Postings> postings = new ArrayList<Postings>(); 
		Postings pos = new Postings();
		
		try {
			conn = new DBConnect();
			ps = conn.makeConnection().prepareStatement(sql);
			ps.setString(1, term);

			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				pos = new Postings();
				pos.docid = rs.getInt("docid");
				pos.tfidf = rs.getDouble("tfidf");
				
				postings.add(pos);
			}
			
			rs.close();
			ps.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		} finally {
			conn.close();
		}

		return postings;
	}

	/*
	 * 输入：terms. 输出accmulator
	 */
	public static HashMap<Integer, ArrayList<Double>> query(ArrayList<String> terms) {
		// // 把文件读入hashmap - 存储整个invertedindex
		// HashMap<String, ArrayList<Postings>> hashmap = getInvertedIndex();
		// // posingList存储指定term对应的posting
		 ArrayList<Postings> postingList = new ArrayList<Postings>();

		HashMap<Integer, ArrayList<Double>> accumulator = new HashMap<Integer, ArrayList<Double>>();
		ArrayList<Double> tfidfList = new ArrayList<Double>();

		// 遍历terms
		for (int i = 0; i < terms.size(); i++) {

			String temp = terms.get(i);

			// 找出每个term对应的Postings
			// if (hashmap.containsKey(temp)) {
			 postingList = DBread(temp);
			// }

//			 遍历postingList
			 for (int j = 0; j < postingList.size(); j++) {
			
			 // 取出docID,tfidf
			 int docid = postingList.get(j).docid;
			 double tfidf = postingList.get(j).tfidf;
			
			 // 判断accumulator是否包含次docid
			 if (accumulator.containsKey(docid)) {
			 tfidfList = accumulator.get(docid);
			
			 // 把对应term个数位置设为tfidf
			 tfidfList.add(tfidf);
			 // update accumulator
			 accumulator.put(docid, tfidfList);
			 tfidfList = new ArrayList<Double>();
			
			 } else {
			
			 // 新建tfidf，前几位为0
			 for (int k = i; k >= 0; k--) {
			 tfidfList.add(0.0);
			 }
			 tfidfList.set(i, tfidf);
			
			 accumulator.put(docid, tfidfList);
			
			 tfidfList = new ArrayList<Double>();
			 }
			
			 }
			
			 // 如果没有填新的tfidf，补0
			 Collection<ArrayList<Double>> addZero = accumulator.values();
			 Iterator<ArrayList<Double>> iterator = addZero.iterator();
			 while (iterator.hasNext()) {
			 ArrayList<Double> templist = iterator.next();
			 if (templist.size() <= i) {
			 templist.add(0.0);
			 }
			 }

		}

		// 测试-输出accumulator
		// Collection keyset = accumulator.keySet();
		// Collection valueset = accumulator.values();
		//
		// Iterator keyIterator = keyset.iterator();
		// System.out.println("docID");
		// while (keyIterator.hasNext()) {
		// System.out.println(keyIterator.next());
		// }
		//
		// System.out.println("tf-idf");
		// Iterator valueIterator = valueset.iterator();
		// while (valueIterator.hasNext()) {
		// System.out.println(valueIterator.next());
		// }

		return accumulator;
	}

	public static HashMap<String, ArrayList<Postings>> getInvertedIndex() {
		// key是term,value是posting list
		HashMap<String, ArrayList<Postings>> hash = new HashMap<String, ArrayList<Postings>>();

		try {
			// 读posting文件
			FileReader fr = new FileReader("D:/InvertedIndex_sorted.txt");
			BufferedReader br = new BufferedReader(fr);

			// 读取一行
			String line = br.readLine();

			// 用来判断新的term
			boolean start_term = true;
			String term = "";
			ArrayList<Postings> posList = new ArrayList<Postings>();
			Postings pos = new Postings();
			int docID = 0;
			double tfidf = 0;
			ArrayList<Integer> positions = new ArrayList<Integer>();

			// 判断是否没读完
			while (line != null) {
				// 判断是否以“w:”开头
				if (line.startsWith("w:")) {

					// 是否为新的term
					if (!start_term) {
						// 把前一个加入hash
						hash.put(term, posList);
						posList = new ArrayList<Postings>();
					}
					// 截取term
					term = line.substring(2);

				}

				// 判断接下来是不是postings
				if (line.startsWith("P")) {
					start_term = false;

					positions = new ArrayList<Integer>();

					// 读docID
					line = br.readLine();
					docID = Integer.parseInt(line);
					// 读tf-idf
					line = br.readLine();
					tfidf = Double.parseDouble(line);
					// 读position
					line = br.readLine();
					String temp[] = line.split(" ");
					for (int i = 0; i < temp.length; i++) {
						positions.add(Integer.parseInt(temp[i]));
					}

					// 设置postings中的值
					pos = new Postings(docID, tfidf, positions);
					posList.add(pos);
				}
				// 接着读下一行
				line = br.readLine();
				continue;
			}
			hash.put(term, posList);
			// 关闭流
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 测试postings
		// ArrayList<Postings> test2 = hash.get("learning");
		// for (int i = 0; i < test2.size(); i++) {
		// System.out.println("new");
		// System.out.println(test2.get(i).docid);
		// System.out.println(test2.get(i).tfidf);
		// }
		//
		// System.out.println("========================");
		// ArrayList<Postings> test3 = hash.get("mondego");
		// for (int i = 0; i < test3.size(); i++) {
		// System.out.println("new");
		// System.out.println(test3.get(i).docid);
		// System.out.println(test3.get(i).tfidf);
		// }

		return hash;
	}

	public static void main(String[] args) {
		 ArrayList<String> arr = new ArrayList<String>();
		 arr.add("student");
		 arr.add("affairs");
		 HashMap<Integer, ArrayList<Double>> accumulator = query(arr);
//		 Collection<Integer> set = accumulator.keySet();
//		 Iterator<Integer> it = set.iterator();
//		 while(it.hasNext()){
//			 int temp = it.next();
//			 System.out.println(temp);
//			 System.out.println(accumulator.get(temp).toString());
//		 }
		//
		 ArrayList<Integer> docs = ranking(accumulator);
		
		 for (int i = 0; i < docs.size(); i++) {
		 System.out.println(docs.get(i));
		 }

//		System.out.println(DBread("learning"));
	}

}
