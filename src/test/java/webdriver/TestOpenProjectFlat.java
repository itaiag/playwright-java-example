package webdriver;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestOpenProjectFlat extends AbstractTest{


    @Test
    public void testCreateNewTask() throws InterruptedException {
        driver.get("http://localhost:8080");
        driver.findElement(By.xpath("//span[text()='Sign in']")).click();

        WebElement userName = driver.findElement(By.id("username-pulldown"));
        wait.until(ExpectedConditions.elementToBeClickable(userName));
        userName.sendKeys("admin");
        driver.findElement(By.id("password-pulldown")).sendKeys("adminadmin");
        driver.findElement(By.id("login-pulldown")).click();

        driver.findElement(By.cssSelector("#projects-menu i")).click();
        driver.findElement(By.linkText("Selenium project")).click();
        driver.findElement(By.cssSelector("a#main-menu-work-packages")).click();
        driver.findElement(By.cssSelector("button.add-work-package")).click();
        driver.findElement(By.cssSelector("a[aria-label='Task']")).click();

        String taskName = "Task" + System.currentTimeMillis();
        driver.findElement(By.id("wp-new-inline-edit--field-subject")).sendKeys(taskName);
        driver.findElement(By.id("work-packages--edit-actions-save")).click();
        driver.findElement(By.cssSelector("button[title='Close details view'].work-packages--details-close-icon")).click();

        driver.findElement(By.id("work-packages-filter-toggle-button")).click();
        driver.findElement(By.id("filter-by-text-input")).sendKeys(taskName);

        Thread.sleep(2000); // Can't tell when the table is refreshed
        int numOfLines = driver.findElements(By.cssSelector("tbody.work-package--results-tbody tr")).size();
        Assert.assertEquals(numOfLines, 1);
        WebElement element = driver.findElement(By.cssSelector("i.icon-show-more-horizontal"));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("i.icon-show-more-horizontal")).click(); // Need to find again due to stale element
        element = driver.findElement(By.cssSelector("button[aria-label='Delete'].menu-item"));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        driver.findElement(By.xpath("//button[text()='Confirm']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully deleted work packages.']")));
    }
}
