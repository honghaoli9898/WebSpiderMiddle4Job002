package JDTest;

import org.openqa.selenium.WebDriver;

import com.tl.job002.utils.WebDriverUtil;

public class PhantomjsTest {
	public static void main(String[] args) {
		WebDriver webDriver = null;
		try {
			webDriver = WebDriverUtil.createPhantomjsWebDriver("plugins\\phantomjs\\phantomjs.exe");
			webDriver.get("https://search.jd.com/Search?keyword=手机&enc=utf-8&page=1/");
			System.out.println(webDriver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(webDriver != null){
				webDriver.close();
			}
		}
	}
}
