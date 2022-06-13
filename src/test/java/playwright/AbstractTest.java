package playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class AbstractTest {

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeSuite
    public void beforeSuite(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

    }

    @BeforeMethod
    public void beforeMethod(){
        page = browser.newPage();
    }

    @AfterMethod
    public void afterMethod(){
        page.close();
    }
    @AfterSuite
    public void teardown(){
        playwright.close();
    }

}
