package Dubrova;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;


public class LoginTest {
    WebDriver webDriver;


    @Before
    public void setUp() {

        File fileFF = new File("./drivers/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", fileFF.getAbsolutePath());
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://account.templatemonster.com/auth/#/");
    }

    @Test
    public void emptyEmailErrorMessage() {

        webDriver.findElement(By.xpath(".//span[@id='id-index-continue-button']/button")).click();
        WebDriverWait webDriverWait15 = new WebDriverWait(webDriver, 15);
        webDriverWait15.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("div.notification.notification--error span.notification__text"), "Please enter Your email"));
        String textFromElement = webDriver.findElement(By.cssSelector("div.notification.notification--error span.notification__text")).getText();
        Assert.assertThat("Text in element does not match", textFromElement, is("Please enter Your email"));
        System.out.println("Correct error message for empty email field");
    }

    @Test
    public void correctFormForNewUser() {

        webDriver.findElement(By.xpath(".//input[@type='email']")).sendKeys("rrr@rr.rr");
        webDriver.findElement(By.xpath(".//span[@id='id-index-continue-button']/button")).click();
        String textFromElement = webDriver.findElement(By.cssSelector("div.id-general-password-field label")).getText();
        Assert.assertTrue("Wrong registration form for a new user",
                webDriver.findElement(By.xpath(".//span[@id='id-create-new-account']//button[@type='button']"))
                        .isDisplayed());
        Assert.assertThat("Wrong placeholder in the password field",
                textFromElement, is("Create your password"));
        System.out.println("Correct registration form");
    }

    @Test
    public void successfulLoginRegisteredUser() {

        webDriver.findElement(By.xpath(".//input[@type='email']")).sendKeys("just.name197@gmail.com");
        webDriver.findElement(By.xpath(".//span[@id='id-index-continue-button']/button")).click();
        webDriver.findElement(By.xpath(".//input[@type='password']")).sendKeys("258258");
        webDriver.findElement(By.xpath(".//span[@id='id-password-login-button']/button")).click();
        Assert.assertTrue("Login failed",
                webDriver.findElement(By.xpath(".//*[text()='just.name197@gmail.com']"))
                        .isDisplayed());

        System.out.println("Successful login");
    }

    @After
    public void tearDown() {

        webDriver.quit();
    }
}