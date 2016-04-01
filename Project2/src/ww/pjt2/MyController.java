package ww.pjt2;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MyController {
	public static void main(String[] args) throws Exception {
        
		//Set crawl storage folder
		String crawlStorageFolder = "D:/crawler";
        
		//set number of crawlers thread
        int numberOfCrawlers = 15;

        //configure
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        //set user agent
//        config.setUserAgentString("IR W16 WebCrawler 70881144 56603849");
        
        /*
         * Be polite and set time delay to crawl
         */
        config.setPolitenessDelay(300);

        /*
         * Set the maximum crawl depth. (-1 may lead to dead lock)
         */
        config.setMaxDepthOfCrawling(50);

        /*
         * set the maximum number of pages to crawl.
         */
        config.setMaxPagesToFetch(500);

//        config.setConnectionTimeout(5000);
//        config.setSocketTimeout(5000);
        /*
         * resumable crawling
         */
        config.setResumableCrawling(true);
        
        config.setIncludeBinaryContentInCrawling(false);
        
        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         *  add seed urls.
         */
		controller.addSeed("http://sconce.ics.uci.edu");
//        controller.addSeed("http://www.ics.uci.edu/prospective/en/contact/student-affairs/");
//        controller.addSeed("https://www.ics.uci.edu/ugrad/");
//        controller.addSeed("http://www.ics.uci.edu/grad/sao/");
//        controller.addSeed("https://www.ics.uci.edu/ugrad/sao/");
//        controller.addSeed("http://www.ics.uci.edu/about/visit/");
//        controller.addSeed("https://www.ics.uci.edu/about/about_contact.php");
//        controller.addSeed("https://www.ics.uci.edu/grad/");
//        controller.addSeed("https://www.ics.uci.edu/about/search/search_dean.php/");
        /*
         * Start the crawler. 
         */
        
        //time calculate
        System.out.println("start");
        long timeBeforeCrawl = System.currentTimeMillis();
        controller.start(MyCrawler.class, numberOfCrawlers);
        
        System.out.println("finished");
        long timeAfterCrawl = System.currentTimeMillis();
        long timeToCrawl = (timeAfterCrawl - timeBeforeCrawl) / 1000;
        System.out.println(timeToCrawl);

    }
}
