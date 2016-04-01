package edu.ww.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.mysql.fabric.xmlrpc.base.Array;

import edu.ww.dao.QueryDao;
import edu.ww.entity.Document;
import edu.ww.entity.Postings;
import edu.ww.util.DBConnect;

public class QueryDaoImpl implements QueryDao {
	/*
	 * 计算score
	 */
	public Double getScore(ArrayList<Double> vectors) {
		Double result = 0.0;
		for (int i = 0; i < vectors.size(); i++) {
			result += vectors.get(i);
		}

		// cosine to calculate
		// Double diancheng =0.0;
		// Double len = 0.0;
		// Double norLen = (double) vectors.size();
		//
		// for (int i = 0; i < vectors.size(); i++) {
		// diancheng += vectors.get(i)*1;
		// len += vectors.get(i)*vectors.get(i);
		// }
		//
		// norLen = Math.sqrt(norLen);
		// len = Math.sqrt(len);
		//
		// result = diancheng/(norLen*len);
		return result;
	}

	/*
	 * 输出排在前面的docID
	 */
	public ArrayList<Integer> ranking(HashMap<Integer, ArrayList<Double>> accumulator) {
		ArrayList<Integer> top = new ArrayList<Integer>();

		// 取keyList
		ArrayList<Integer> keyList = new ArrayList<Integer>();
		keyList.addAll(accumulator.keySet());

		// 取pageRankList
		// ArrayList<Float> pageRankList = pageRankRead(keyList);

		// 取valueList
		ArrayList<ArrayList<Double>> valueList = new ArrayList<ArrayList<Double>>();
		valueList.addAll(accumulator.values());

		// 给valueList算score
		ArrayList<Double> scoreList = new ArrayList<Double>();
		for (int i = 0; i < valueList.size(); i++) {
			Double temp = getScore(valueList.get(i));
			// temp = temp+pageRankList.get(i)/10;
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

		// 加page Rank
		ArrayList<Integer> tempKeyList = new ArrayList<Integer>();
		ArrayList<Double> tempValueList = new ArrayList<Double>();
		for (int i = 0; i < 10; i++) {
			tempKeyList.add(keyList.get(i));
			tempValueList.add(scoreList.get(i));
		}

		
		
		//得到pageRank的分数
		ArrayList<Float> prList = pageRankRead(tempKeyList);

		
		// BubblingSort
				for (int i = 0; i < tempKeyList.size(); i++) {
					for (int j = 1; j < tempKeyList.size() - i; j++) {
						if (prList.get(j) > prList.get(j - 1)) {
							float tempPRValue = prList.get(j);
							prList.set(j, prList.get(j - 1));
							prList.set(j - 1, tempPRValue);

							int tempKey = tempKeyList.get(j);
							tempKeyList.set(j, tempKeyList.get(j - 1));
							tempKeyList.set(j - 1, tempKey);

							double tempValue = tempValueList.get(j);
							tempValueList.set(j, tempValueList.get(j - 1));
							tempValueList.set(j - 1, tempValue);
						}
					}
				}
		
				//加上pageRank的分数
		for (int i = 0; i < 5; i++) {
			tempValueList.set(i, tempValueList.get(i) + 1);
		}
		for (int i = 5; i < 10; i++) {
			tempValueList.set(i, tempValueList.get(i) + 0.5);
		}
		
		// BubblingSort
		for (int i = 0; i < tempKeyList.size(); i++) {
			for (int j = 1; j < tempKeyList.size() - i; j++) {
				if (tempValueList.get(j) > tempValueList.get(j - 1)) {
					double tempValue = tempValueList.get(j);
					tempValueList.set(j, tempValueList.get(j - 1));
					tempValueList.set(j - 1, tempValue);

					int tempKey = tempKeyList.get(j);
					tempKeyList.set(j, tempKeyList.get(j - 1));
					tempKeyList.set(j - 1, tempKey);

				}
			}
		}

		for (int i = 0; i < 5; i++) {
			top.add(tempKeyList.get(i));
		}

		return top;
	}

	/*
	 * URLread
	 */
	public ArrayList<Document> docinfoRead(ArrayList<Integer> docidList) {
		ArrayList<Document> dlist = new ArrayList<Document>();

		String sql = "select * from url where docid=?";
		PreparedStatement ps = null;
		DBConnect conn = null;

		Document doc = new Document();

		for (int i = 0; i < docidList.size(); i++) {
			try {
				conn = new DBConnect();
				ps = conn.makeConnection().prepareStatement(sql);
				ps.setInt(1, docidList.get(i));

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					doc = new Document();
					doc.docid = rs.getInt("docid");
					doc.url = rs.getString("url");
					doc.title = rs.getString("title");

					dlist.add(doc);
				}

				rs.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// TODO: handle exception
			} finally {
				conn.close();
			}
		}

		return dlist;
	}

	// pageRank Read
	public ArrayList<Float> pageRankRead(ArrayList<Integer> docidList) {
		ArrayList<Float> pageRanklist = new ArrayList<Float>();

		String sql = "select * from url where docid=?";
		PreparedStatement ps = null;
		DBConnect conn = null;

		for (int i = 0; i < docidList.size(); i++) {
			try {
				conn = new DBConnect();
				ps = conn.makeConnection().prepareStatement(sql);
				ps.setInt(1, docidList.get(i));

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					float pagerank = rs.getFloat("PageRank");

					pageRanklist.add(pagerank);
				}

				rs.close();
				ps.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// TODO: handle exception
			} finally {
				conn.close();
			}
		}

		return pageRanklist;
	}
	//
	/*
	 * DBread
	 */

	public ArrayList<Postings> DBread(String term) {
		String start = term.substring(0, 1);

		String sql = "select * from " + start + " where term=?";
		PreparedStatement ps = null;
		DBConnect conn = null;

		ArrayList<Postings> postings = new ArrayList<Postings>();
		Postings pos = new Postings();

		try {
			conn = new DBConnect();
			ps = conn.makeConnection().prepareStatement(sql);
			ps.setString(1, term);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
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
	public HashMap<Integer, ArrayList<Double>> query(ArrayList<String> terms) {
		// // 把文件读入hashmap - 存储整个invertedindex
		// HashMap<String, ArrayList<Postings>> hashmap = getInvertedIndex();
		// // posingList存储指定term对应的posting
		for (int i = 0; i < terms.size(); i++) {
			System.out.println(terms.get(i));
		}

		ArrayList<Postings> postingList = new ArrayList<Postings>();

		HashMap<Integer, ArrayList<Double>> accumulator = new HashMap<Integer, ArrayList<Double>>();
		ArrayList<Double> tfidfList = new ArrayList<Double>();

		// 遍历terms
		for (int i = 0; i < terms.size(); i++) {

			String temp = terms.get(i);

			// 找出每个term对应的Postings
			postingList = DBread(temp);

			// 遍历postingList
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

		return accumulator;
	}

}