package JDTest;

import java.io.IOException;
import java.util.Date;

import org.apache.http.ParseException;

public class getThrityInfo {

	public static void main(String[] args) throws ParseException, IOException {
		Date date = new Date();
		long d=date.getTime();
		//1548388671
		System.out.println(d/1000);
		// String url =
		// "https://search.jd.com/s_new.php?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&psort=3&cid2=653&cid3=655&page=8&s=175&scrolling=y&log_id=";
		//
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// String htmlSource = null;
		// try {
		// HttpGet httpGet = new HttpGet(url);
		// CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// try {
		// HttpEntity entity1 = response1.getEntity();
		// htmlSource = EntityUtils.toString(entity1, "utf-8") ;
		// } finally {
		// response1.close();
		// }
		// } finally {
		// httpclient.close();
		// }
		// System.out.println(htmlSource);
	}

}
