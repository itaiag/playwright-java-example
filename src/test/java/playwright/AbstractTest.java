package playwright;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.nio.file.Paths;

public abstract class AbstractTest {

    protected Playwright playwright;
    protected BrowserContext context;
    protected Page page;

    @BeforeSuite
    public void beforeSuite(){
        playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("test-results"+File.separator+"videos/"))
                .setRecordVideoSize(640, 480));
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

    }

    @BeforeMethod
    public void beforeMethod(){
        page = context.newPage();
    }

    @AfterMethod
    public void afterMethod(){
        page.close();
    }

    @AfterSuite
    public void teardown(){
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("test-results"+ File.separator+"trace.zip")));
        playwright.close();
    }

}
