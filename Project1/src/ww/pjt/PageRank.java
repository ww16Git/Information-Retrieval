package ww.pjt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * ��url��Ӧ��docIDȡ����
 */
public class PageRank {

	
	// ���ļ����data,����hashmap
	public static HashMap<Integer, ArrayList<Integer>> linkRead() {
		// ���ڴ洢docID�Ͷ�Ӧ����docID
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		// �ļ���
		String filename = "D:/Outlinks_final.txt";
		// �洢���ӵ�����
		ArrayList<Integer> outlinks = new ArrayList<Integer>();
		
		int maxValue=0;
		int maxId=0;
		
		try {
			// ���ļ�
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			// ��docָʾ��
			// boolean start_new_page = true;
			String docid = "";
			while ((line = br.readLine()) != null) {
				// tell the start of the page
				if (line.startsWith("DOCID")) {

					// �ָ��ַ���
					String arr[] = line.split(":");
					docid = arr[1];
				}

				else if (line.startsWith("Outlinks")) {
					String arr[] = line.split(":");
					if (arr.length <= 1) {
						map.put(Integer.parseInt(docid), outlinks);
						outlinks = new ArrayList<Integer>();
					} else {
						String links = arr[1];
						String lnkArr[] = links.split(" ");
						
						if(lnkArr.length>maxValue){
							maxId = Integer.parseInt(docid);
							maxValue = lnkArr.length;
						}
						
						for (int i = 0; i < lnkArr.length; i++) {
							outlinks.add(Integer.parseInt(lnkArr[i]));
						}
						map.put(Integer.parseInt(docid), outlinks);
						outlinks = new ArrayList<Integer>();
					}

				}
			}

			System.out.println(maxId+" "+maxValue);
			
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	public static float[][] changeToMatrix(HashMap<Integer, ArrayList<Integer>> map) {
		float[][] matrix = new float[11225][11225];

		// ����һ��1�ĸ���
		int count = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (map.containsKey(i)) {
				ArrayList<Integer> temp = map.get(i);

				for (int j = 0; j < matrix.length; j++) {
					if (temp.contains(j)) {
						count++;
						matrix[i][j] = 1;
					} else {
						matrix[i][j] = 0;
					}
				}
				// һ�����Ϊ1
				if (count != 0) {
					for (int j = 0; j < matrix.length; j++) {
						matrix[i][j] = matrix[i][j] / count;
					}
					count = 0;
				}

			}

		}

		return matrix;
	}

	// ����score
	public static float[] calculateScore(float[][] matrix, float[] tempScore) {

		float result[] = new float[11225];
		for (int i = 0; i < result.length; i++) {
			result[i] = 0;
		}

		for (int i = 0; i < tempScore.length; i++) {
			float sum = 0;
			for (int j = 0; j < tempScore.length; j++) {
				sum += tempScore[j] * matrix[j][i];
				result[i] = sum;
			}
		}

		return result;
	}

	public static void updateData(float pageRank,int docid){
		String sql = "update url set PageRank=? where docid=?";
		PreparedStatement ps = null;
		DBConnect conn = null;

		try {
			conn = new DBConnect();
			ps = conn.makeConnection().prepareStatement(sql);
			ps.setFloat(1, pageRank);
			ps.setInt(2, docid);
			// ps.setString(3, title);

			ps.execute();

			ps.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			conn.close();
		}
	}
	
	public static void main(String args[]) {
		System.out.println("run");
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		map = linkRead();
		System.out.println(map.size());
		// ��map�����ά����
		float[][] matrix = changeToMatrix(map);
		float tempScore[] = new float[11225];
//		float tempScore[] = new float[11239];
		for (int i = 0; i < tempScore.length; i++) {
			tempScore[i] = 0;
		}
		tempScore[1811] = 100000;
			
//		// ArrayList<Float> score = calculateScore(matrix, tempScore, 1);
		float score[] = calculateScore(matrix, tempScore);
		for (int i = 0; i < 50; i++) {
			score = calculateScore(matrix, score);
		}
		for (int i = 1; i < score.length; i++) {
			updateData(score[i], i);
		}

	}
}
