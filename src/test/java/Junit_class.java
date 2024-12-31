import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Junit_class {
    WebDriver driver; //globally declare

    @BeforeAll

    public void setup() {
        //driver = new ChromeDriver();
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--headed");
        driver = new ChromeDriver(ops);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test

    @DisplayName("Check if website title showing properly")
    public void getTitle() {
        driver.get("https://demoqa.com/");
        String titleActual = driver.getTitle();
        String titleExpected = "DEMOQA";
        System.out.println(titleActual);
        Assertions.assertTrue(titleActual.contains(titleExpected));
    }

    @AfterAll
    public void closeDriver() {
        //driver.quit();
    }

    @Test
    public void submitForm() {
        driver.get("https://demoqa.com/text-box");
        //driver.findElement(By.id("userName")).sendKeys("Samia"); //Using id

        /*WebElement firstNameEle = driver.findElement(By.cssSelector("[type=text"));
        firstNameEle.sendKeys("Samia Jahan");*/

        /*List<WebElement> textBox = driver.findElements(By.tagName("input"));
        textBox.get(0).sendKeys("Samia Jahan");
        textBox.get(1).sendKeys("user@test.com");*/

        List<WebElement> textBox = driver.findElements(By.className("form-control"));// using ClassName
        textBox.get(0).sendKeys("Samia Jahan");
        textBox.get(1).sendKeys("user@test.com");
        textBox.get(2).sendKeys("Dhanmondi");
        textBox.get(3).sendKeys("Dhaka");

        Utils.scroll(driver, 500);

        driver.findElement(By.className("btn-primary")).click();

        List<WebElement> resultElem = driver.findElements(By.tagName("p"));
        String r1Actual = resultElem.get(0).getText();
        String r2Actual = resultElem.get(1).getText();
        String r3Actual = resultElem.get(2).getText();
        String r4Actual = resultElem.get(3).getText();

        String r1Expected = "Samia Jahan";
        String r2Expected = "user@test.com";
        String r3Expected = "Dhanmondi";
        String r4Expected = "Dhaka";

        Assertions.assertTrue(r1Actual.contains(r1Expected));
        Assertions.assertTrue(r2Actual.contains(r2Expected));
        Assertions.assertTrue(r3Actual.contains(r3Expected));
        Assertions.assertTrue(r4Actual.contains(r4Expected));
    }

    @Test
    public void clickButtons() {
        driver.get("https://demoqa.com/buttons");
        List<WebElement> btnElement = driver.findElements(By.className("btn-primary"));
        Actions actions = new Actions(driver);
        Utils.scroll(driver, 300);
        actions.doubleClick(btnElement.get(0)).perform();
        actions.contextClick(btnElement.get(1)).perform();
        actions.click(btnElement.get(2)).perform();
    }

    @Test
    public void handleAlerts() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        Thread.sleep(5000);
        driver.switchTo().alert().accept();

        driver.findElement(By.id("timerAlertButton")).click();
        Thread.sleep(5000);
        driver.switchTo().alert().accept();

        driver.findElement(By.id("confirmButton")).click();
        Thread.sleep(5000);
        driver.switchTo().alert().dismiss();

        driver.findElement(By.id("promtButton")).click();
        driver.switchTo().alert().sendKeys("samia");
        Thread.sleep(5000);
        driver.switchTo().alert().accept();
    }

    @Test
    public void selectDate() throws InterruptedException {
        driver.get("https://demoqa.com/date-picker");
        WebElement txtCalendarElement = driver.findElement(By.id("datePickerMonthYearInput"));
        txtCalendarElement.click();
        txtCalendarElement.sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        Thread.sleep(5000);
        txtCalendarElement.sendKeys("10/05/2024", Keys.ENTER);
    }

    @Test
    public void selectDropDown() {
        driver.get("https://demoqa.com/select-menu");
        Select options = new Select(driver.findElement(By.id("oldSelectMenu")));
        options.selectByVisibleText("Green");
        options.selectByValue("1");

        Select cars = new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
        }
    }
@Test
    public void selectDynamicDropdown() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");
        List<WebElement> dropDownElem = driver.findElements(By.className("css-1hwfws3"));
        dropDownElem.get(1).click();
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        //actions.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(5000);
        actions.sendKeys(Keys.ENTER).perform();
    }
@Test
    public void MouseHover(){
        driver.get("https://www.aiub.edu/");
        List<WebElement> menuElement = driver.findElements(By.xpath(" //a[contains(text(),\"About\")]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuElement.get(1)).perform();
    }
@Test
    public void TabHandle() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(5000);
        //System.out.println(driver.getWindowHandles());
        ArrayList<String> arrayList = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(arrayList.get(1)); //switching
        String text = driver.findElement(By.id("sampleHeading")).getText();
        System.out.println(text);
        driver.close();
        Assertions.assertEquals(text,"This is a sample page");
        driver.switchTo().window(arrayList.get(0));
    }
@Test
    public void HandleMultipleWindows(){
        driver .get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("windowButton")).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();

        Iterator<String> Iterator = allWindowHandles.iterator();
        while (Iterator.hasNext()){
            String childWindow = Iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                String text = driver.findElement(By.id("sampleHeading")).getText();
                System.out.println(text);
            }
        }
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }
@Test
    public void UploadImage(){
        driver.get("https://demoqa.com/upload-download");
        System.out.println(System.getProperty("user.dir")+"/src/test/resources/Sqa.jpg");
        driver.findElement(By.id("uploadFile")).sendKeys(System.getProperty("user.dir")+"/src/test/resources/Sqa.jpg");
    }
@Test
    public void scrapWebTableData() {
    driver.get("https://demoqa.com/webtables");
    WebElement table = driver.findElement(By.className("rt-tbody"));
    List<WebElement> allRows = table.findElements(By.cssSelector("[role=rowgroup]"));

    for (WebElement row : allRows) {
        List<WebElement> allCells = table.findElements(By.cssSelector("[role=gridcell]"));
        for (WebElement cell : allCells) {
            System.out.println(cell.getText());
        }
    }
}
@Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame1");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();
    }

}
