package ww.pjt2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	// set filters
	public static FileWriter fw;
	public static FileWriter lw;
	public static PrintWriter plw;
	public static PrintWriter pw;
	public static int count = 0;

	public final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|" + "gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|"
					+ "pdf|rm|smil|wmv|swf|jpg|webm|tar|wma|zip|rar|gz|xz|bz|lz|7z|"
					+ "dmg|pptx?|bin|tar|ico|c|o|h|rss|apk|arc|arj|jar|tgz|dat|mkv"
					+ "doc|docx|pfm|ogg|3pg|acc|amr|au|vox|ps|txt|xml|py|bw|java))$");

	// hold the visited url to avoid repeat access
	public HashSet<String> hashset = new HashSet<String>();

	public MyCrawler() {
		try {
			this.lw = new FileWriter("D:/StartOver/sconce-Links.txt", true);
			this.plw = new PrintWriter(lw, true);
			this.fw = new FileWriter("D:/StartOver/sconce-Crawler.txt", true);
			this.pw = new PrintWriter(fw, true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implement this function to specify whether the given url should be
	 * crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		// get the url
		String href = url.getURL().toLowerCase();

		// make judgment on repeat access to same url
		String domain = "";
		try {
			URI uri = new URI(href);
			domain = uri.getHost();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!hashset.contains(href) && domain.contains("sconce.ics.uci.edu")) {

			hashset.add(href);

			// avoid "archive.ics.uci.edu"
//			if (href.contains("archive.ics.uci.edu")) {
//				return false;
//			}
//
//			// avoid "archive.ics.uci.edu"
//			if (href.contains("djp3-pc2.ics.uci.edu")) {
//				return false;
//			}
//
////			 avoid "calendar.ics.uci.edu"
//			 if (href.contains("calendar.ics.uci.edu")) {
//			 return false;
//			 }
//
//			// avoid "duttgroup.ics.uci.edu"
//			if (href.contains("duttgroup.ics.uci.edu")) {
//				return false;
//			}
//			// avoid "student-council.ics.uci.edu"
//			if (href.contains("student-council.ics.uci.edu")) {
//				return false;
//			}
//
//			// if
//			// (href.contains("http://www.ics.uci.edu/prospective/zh-tw/opportunities/entrepreneurship/"))
//			// {
//			// return false;
//			// }
//
//			if (href.contains("http://drzaius.ics.uci.edu")) {
//				return false;
//			}
//			if (href.contains("zh-tw")) {
//				return false;
//			}
//			if ((!FILTERS.matcher(href).matches() && href.contains("ics.uci.edu"))) {
//				System.out.println("should visit URL: " + href);
//			}

			return !FILTERS.matcher(href).matches();

		} else {

			return false;
		}

	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {

		String url = page.getWebURL().getURL();
		System.out.println("visit URL: " + url);
		Set<WebURL> outgoinglinks = page.getParseData().getOutgoingUrls();

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();

			pw.println("DOCID " + page.getWebURL().getDocid());
			pw.println("URL " + url);
			pw.println("TITILE: " + ((HtmlParseData) page.getParseData()).getTitle());
			pw.println(text);

			plw.println("DOCID " + page.getWebURL().getDocid());
			plw.println("URL " + url);
			plw.println("OutLinks:");
			Iterator iter = outgoinglinks.iterator();
			String link = "";
			while (iter.hasNext()) {
				link = iter.next().toString();
				if(link.contains(".ics.uci.edu")){
					plw.println(link);
				}
			}

			this.count++;
			System.out.println(count);

		}
	}
}