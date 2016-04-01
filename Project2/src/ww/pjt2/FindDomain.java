package ww.pjt2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FindDomain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<String> hs = new HashSet<String>();

		FileReader fr;
		try {
			fr = new FileReader("URL.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				
				String a[] = line.split(" ");
				String url = a[1];
				URI uri = new URI(url);
				hs.add(uri.getHost());
			}
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

		int count = 0;
		Iterator<String> iterator = hs.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
			count++;
		}
		
		System.out.println(count);
	}

}
