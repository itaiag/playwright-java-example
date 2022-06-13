package playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestOpenProjectFlat extends AbstractTest {


    @Test
    public void testCreateNewTask(){
        page.navigate("http://localhost:8080", new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.NETWORKIDLE));
        page.locator("span:has-text('Sign in')").click();
        page.fill("text=Username", "admin");
        page.fill("text=Password", "adminadmin");
        page.waitForResponse("**/login",()-> {
            page.locator("input:has-text('Sign in')").click();
        });
        page.locator("#projects-menu i").click();
        page.waitForNavigation(()-> {
            page.locator("#ui-id-5 >> text=Selenium project").click();
        });
        page.waitForNavigation(()->{
            page.locator("#main-menu-work-packages >> text=Work packages").click();
        });
        page.locator("text=Create Include projects >> [aria-label='Create new work package']").click();
        page.locator("div#types-context-menu a[aria-label='Task']").click();
        String taskName = "Task" + System.currentTimeMillis();
        page.locator("id=wp-new-inline-edit--field-subject").fill(taskName);

        page.waitForResponse("**/queries/**", ()->{
            page.locator("button:has-text('Save')").click();
        });
        page.locator("[aria-label='Activate Filter']").click();

        Response response = page.waitForResponse("**/queries/**", ()->{
            page.locator("id=filter-by-text-input").fill(taskName);
        });
        System.out.println("Response body:" + response.text());

        page.locator("a[title='Close form']").click();
        page.locator("button[title='Close details view'] i.icon-close.icon-no-color").click();
        int numOfRows = page.locator("tbody.work-package--results-tbody tr").count();
        Assert.assertEquals(numOfRows, 1);
        Locator locator = page.locator("i.icon-show-more-horizontal");
        locator.hover();
        locator.click();
        page.locator("[aria-label='Delete']").click();
        page.locator("button:has-text('Confirm')").click();
        page.locator("text=Successfully deleted work packages.").waitFor();
        page.locator("text=No work packages to display.").waitFor();

    }

}
