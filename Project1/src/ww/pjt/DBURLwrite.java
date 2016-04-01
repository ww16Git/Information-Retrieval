package ww.pjt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBURLwrite {

	/*
	 * 写入数据库
	 */
	public static void writeData(int docid, String url) {

		String sql = "INSERT INTO url(docid,url)VALUES(?,?)";
		PreparedStatement ps = null;
		DBConnect conn = null;

		try {
			conn = new DBConnect();
			ps = conn.makeConnection().prepareStatement(sql);
			ps.setInt(1, docid);
			ps.setString(2, url);
//			ps.setString(3, title);

			ps.execute();

			ps.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			conn.close();
		}

	}

	
	/*
	 * 正则：获取title
	 */
//	public static String getTitle(String htmlSource) {
//		List<String> list = new ArrayList<String>();
//		String title = "";
//
//		Pattern pa = Pattern.compile("<title>.*?</title>|<TITLE>.*?</TITLE>");
//	
//		Matcher ma = pa.matcher(htmlSource);
//
//		while (ma.find())
//		{
//			list.add(ma.group());
//		}
//		for (int i = 0; i < list.size(); i++) {
//			title = title + list.get(i);
//		}
//		return outTag(title);
//	}
//
//	public static String outTag(String s) {
//		return s.replaceAll("<.*?>", "");
//	}

	/*
	 * 请求html信息
	 */
//	public static String getHtmlSource(String htmlUrl) {
//		
//		
//		
//		StringBuffer document = new StringBuffer();
//		try {
//			URL url = new URL(htmlUrl);
//			URLConnection conn = url.openConnection();
//			conn.setReadTimeout(200);
//			conn.setConnectTimeout(200);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line = null;
//			while ((line = reader.readLine()) != null)
//				document.append(line + " ");
//			reader.close();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String title = getTitle(document.toString());
//
//
//		return title;
//	}

	public static void main(String[] args) {

		//读文件
		String filename = "D:/docid_url_final.txt";

		int docid = 0;
		String url = "";
		String title = "";

		try {
			FileReader filereader = new FileReader(filename);
			BufferedReader br = new BufferedReader(filereader);

			String line = "";
			while ((line = br.readLine()) != null) {
				String arr[] = line.split(" : ");
				docid = Integer.parseInt(arr[0]);
				url = arr[1];
				
				StringBuilder sb = new StringBuilder();
				sb.append("http://");
				sb.append(url);
				writeData(docid, sb.toString());
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
