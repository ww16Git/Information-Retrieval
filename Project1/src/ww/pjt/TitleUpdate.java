package ww.pjt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;

public class TitleUpdate {
	public static void writeData(int docid, String title) {

		String sql = "update url set title=? where docid=?";
		PreparedStatement ps = null;
		DBConnect conn = null;

		try {
			conn = new DBConnect();
			ps = conn.makeConnection().prepareStatement(sql);
			ps.setString(1, title);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ���ļ�
		String filename = "D:/docid_title_final.txt";

		int docid = 0;
		String title = "";

		try {
			FileReader filereader = new FileReader(filename);
			BufferedReader br = new BufferedReader(filereader);

			String line = "";
			while ((line = br.readLine()) != null) {
				String arr[] = line.split(" : ");
				docid = Integer.parseInt(arr[0]);
				if(arr.length<=1){
					continue;
				}
				title = arr[1];

				writeData(docid, title);
			}
			br.close();
			filereader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
