package ww.pjt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DBwrite {

	public static void writeData(String start,String term, int docid, double tfidf) {
		
		String sql = "INSERT INTO "+start+"(term,docid,tfidf)VALUES(?,?,?)";
		PreparedStatement ps = null;
		DBConnect conn = null;

		try {
			conn = new DBConnect();
			ps = conn.makeConnection().prepareStatement(sql);
			ps.setString(1, term);
			ps.setInt(2, docid);
			ps.setDouble(3, tfidf);
			
			ps.execute();

			ps.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			conn.close();
		}

	}

	public static void main(String[] args) {
		try {
			// 读posting文件
			FileReader fr = new FileReader("D:/index_final_plusTitle.txt");
			BufferedReader br = new BufferedReader(fr);

			// 读取一行
			String line = br.readLine();

			//term,start(区分表)
			String term = "";
			String start = "";
			
			//
			int docid = 0;
			double tfidf = 0;
			ArrayList<Integer> positions = new ArrayList<Integer>();
			
			StringBuilder strbuilder = new StringBuilder();
			
			while (line != null) {
				// 判断是否以“w:”开头
				if (line.startsWith("w:")) {
					term = line.substring(2);
					start = term.substring(0, 1);
				}

				if (line.startsWith("P")) {
					
					
					// 读docID
					line = br.readLine();
					docid = Integer.parseInt(line);
					
					
					
					// 读tf-idf
					line = br.readLine();
					tfidf = Double.parseDouble(line);
					
					
					// 读position
					line = br.readLine();
					String temp[] = line.split(" ");
					for (int i = 0; i < temp.length; i++) {
						positions.add(Integer.parseInt(temp[i]));
					}		
					
					writeData(start, term, docid, tfidf);
				}
				line = br.readLine();
				continue;
			}
			
			br.close();
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