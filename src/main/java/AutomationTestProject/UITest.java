package AutomationTestProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class UITest {
    Logger logger = LoggerFactory.getLogger(UITest.class);

    public static final String APPLICATION_URL = "https://www.amazon.com/";

    URL baseUrl() throws MalformedURLException {
        return new URL(APPLICATION_URL);
    }

    private WebDriver driver;

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;

    @FindBy(css = "div[data-component-type=s-search-result]")
    private List<WebElement> searchResults;

    @BeforeClass
    void setup(){
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        pageFactory(driver);
    }

    private void pageFactory(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Test
    void amazonSearch() throws MalformedURLException {
        driver.get(baseUrl().toString());
        logger.info(driver.getTitle());
        searchBox.sendKeys("iPhone");
        searchButton.click();
        logger.info(String.valueOf(searchResults.get(0).getText().contains("Apple iPhone")));
        Assert.assertTrue(searchResults.get(0).getText().contains("Apple iPhone"));
    }

    @AfterClass
    void closeSession(){
        driver.close();
        driver.quit();
    }
}
