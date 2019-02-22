package JDTest;

import com.tl.job002.utils.WebDriverUtil;
import com.tl.job002.utils.WebPageDownloadUtil4ChromeDriver;

public class Test {

	public static void main(String[] args) {
		WebPageDownloadUtil4ChromeDriver downloadInterface = new WebPageDownloadUtil4ChromeDriver();
		downloadInterface.setWebDriver(WebDriverUtil.createWebDriver(false));
		downloadInterface.download("http://www.baidu.com");
	}
}
