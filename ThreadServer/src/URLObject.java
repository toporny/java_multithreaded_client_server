import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class URLObject {
	
	public String url;   // url
	public String html;  // content
	public String time;  // cached date-time 
	
	public URLObject(String url, String html) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		this.url = url;
		this.html = html;
		this.time = dateFormat.format(cal.getTime()); 
		
	}

}
