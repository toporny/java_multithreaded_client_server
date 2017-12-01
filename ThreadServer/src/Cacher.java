import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;


public class Cacher {

	// putInCache() is synchronized method in this class
	
	public static ArrayList<URLObject> pages = new ArrayList<URLObject>();
	
	String url_request = "";
	Logger LOGGER = Logger.getLogger("InfoLogging");

	/*
	 * checks if url is in cache
	 *   
	 * @param ArrayList pages
	 * @param ArrayList name
	 * @return URLObject/null
	 */		
	private URLObject isInCache(ArrayList<URLObject> pages, String name) {
	    for (URLObject item : pages) {
	        if (item.url.equals(name)) {
	            return item;
	        }
	    }
	    return null;
	}

	
	/*
	 * get html from cache
	 * if url and content doesn't exist in cache then get from live page and update cache
	 *   
	 * @param String url
	 * @return String
	 */	
	private String getFromCache(String url) {
		URLObject inCache = isInCache(pages, url);
		String s;
		if (inCache == null ) { // if it no cached add to cache
			s = getFromUrl(url);
			putInCache(url, s );
			LOGGER.info("this url ("+url+") wasn't cached so I added");
		} else {
			s = inCache.html;
			System.out.println("this url ("+url+") was in cache (cached datetime = "+inCache.time+") so I retrived.");
		}
		return s;
	}


	private String getFromUrl(String url) {
		String s = getHtmlFromUrl(url);
		return s;
	}

	
    /*
	 * trying find index of url in ArrayList 
	 * if does not exist return -1
	 * 
	 *  @param String url
	 *  @return int    
	 */		
    public int getIndexByUrl(String url)
    {
        for(URLObject _item : pages)
        {
            if(_item.url.equals(url)) {
            	LOGGER.info("I have found index =" + pages.indexOf(_item));
                return pages.indexOf(_item);
            }
        }
        return -1;
    }
    


    /*
	 * gets content from live page and cache
	 * if it is already in cache refresh content only
	 * 
	 *  @param String url
	 *  @return String    
	 */		
	
	private String getFromUrlAndCache(String url) {
		String s = getHtmlFromUrl(url);
		URLObject inCache = isInCache(pages, url);
		if (inCache == null ) { 
			// if it is no cached add to cache
			LOGGER.info("This url ("+url+") wasn't cached so I added");
			putInCache(url, s);
		} else {
			// refresh only!
			int k = getIndexByUrl(url);
			
			if (k == -1) { // url doesn't exist in cache
				LOGGER.info(" url doesn't exist in cache");
				putInCache(url, s);
			} else { // url exists in cache
				URLObject u = new URLObject(url, s);
				pages.set(k, u); 	
			}
			LOGGER.info("This url ("+url+") was in cache before. I refreshed");
		}
		return s;
	}	

	
	
	/*
	 * puts content in cache
	 * this is synchronized method.
	 * before added it make a sure if url really don't exist in cache
	 * 
	 * @param url 
	 */		
	private synchronized void putInCache(String url, String s) {

		if (getIndexByUrl (url) == -1) {
			URLObject u = new URLObject(url, s);
			pages.add(u);
		}
	}

	
	
	/*
	 * checks if url has "http" on the beggining
	 * 
	 * @param url
	 * @return boolean
	 */	
	private boolean checkHttpBegin (String url) {
		if (url.toUpperCase().substring(0, Math.min(4, url.length())).equals("HTTP")) 
			return true;
		else 
			return false;
	}
	
	
	/*
	 * returns a list of all cached URL
	 * 
	 * @return StatusMessage
	 */
	public StatusMessage list() {
		// if list is empty return negative status and message
		if ( pages.size() == 0 ) {
			LOGGER.info("list is empty");
			return new StatusMessage(-1, "list is empty");
		}

		// if list is not empty return cached details (cached time and url)
		String s = "size: "+ pages.size() + "\n";
	    for (URLObject item : pages) {
	    	s = s + item.time + " " + item.url +"\n"; 
	    }
	    LOGGER.info(s);
	    return new StatusMessage(1, s);
	}
	
	
	/*
	 * gets website content from live page or cached
	 * 
	 * @param String arg - 'GET http://dit.ie TRUE' or 'GET http://com.ie FALSE'
	 * @return StatusMessage
	 */		
	
	public StatusMessage get(String arg) {
		String result = "";
		String[] aString = arg.split(" ");
		String url = aString[1];
		
		if (aString.length !=3 ) {
			return new StatusMessage(-1, "Bad arguments. Use 'client help' to see help.");
		}
		
		String preventCache = aString[2];
		 
		LOGGER.info("how many pages cached = "+pages.size());

		// check if it is exactly three arguments (GET http://dit.ie TRUE)
		if (aString.length != 3)
			return new StatusMessage(-1, "Bad arguments. Use 'client help' to see help.");

		// check if url (string) starts from http
		if (!checkHttpBegin(aString[1]))
			return new StatusMessage(-1, "url must begin from http");

		
		// if TRUE get from live server. If FALSE try get from cache. 
		if (preventCache.toLowerCase().equals("false") ) {
			LOGGER.info("getting url ("+url+") from cache..");
			result = getFromCache(url);
		}
		else if (preventCache.toLowerCase().equals("true") ) {
			LOGGER.info("getting url ("+url+") from website (not from cache)");
			result = getFromUrlAndCache(url);
		}
		else {
			return new StatusMessage(-1, "last argument must be TRUE or FALSE!");
		}

		//System.out.println(result);

		return new StatusMessage(1, result);
		
	}
	
	
	/*
	 * flushes cache
	 * 
	 * @return StatusMessage
	 */		
	public StatusMessage flush() {
		if ( pages.size() == 0 )
			return new StatusMessage(-1, "list is already empty. No need to clear.");
		pages.clear();
		return new StatusMessage(1, "list cleared");
	}
	
	
	/*
	 * get html content from live website
	 * 
	 * @param String url
	 * @return String
	 */		
	private String getHtmlFromUrl(String url) {
		System.out.println("trying to reach "+url);
		String tmp_html = "";
		try {
			URL u = new URL (url);
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
			String inputLine;
			while( (inputLine = in.readLine()) != null) {
				tmp_html = tmp_html + inputLine;
			}
			in.close();
		}
		catch (IOException e) {
			System.out.println("Get HTML page error!");
			System.out.println(e.getMessage());
		}		
		
		return tmp_html;
	}



	 
	
}
