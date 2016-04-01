package ww.pjt;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
public class QueryURl {
	public static String getTitle(String htmlSource) {
		List<String> list = new ArrayList<String>();
		String title = "";

		Pattern pa = Pattern.compile("<title>.*?</title>|<TITLE>.*?</TITLE>");
	
		Matcher ma = pa.matcher(htmlSource);

		while (ma.find())// 寻找符合el的字串
		{
			list.add(ma.group());// 将符合el的字串加入到list中
		}
		for (int i = 0; i < list.size(); i++) {
			title = title + list.get(i);
		}
		return outTag(title);
	}
	
	public static String outTag(String s) {
		return s.replaceAll("<.*?>", "");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		{
	        StringBuffer document = new StringBuffer();
	        try 
	        {
	            URL url = new URL("http://ftp.ics.uci.edu/pub/chimera/");
	            URLConnection conn = url.openConnection();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = null;
	            while ((line = reader.readLine()) != null)
	            document.append(line + " ");
	            reader.close();
	        }
	        catch (MalformedURLException e) 
	        {
	            e.printStackTrace(); 
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace(); 
	        }
	        String title = getTitle(document.toString());
	        
	        System.out.println(title);
	    }
	}

}
