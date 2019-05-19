/**
 * Created by admin on 12.05.2019.
 */

import org.openqa.selenium.*;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import java.util.concurrent.TimeUnit;

public class WordPressTest {
    private WebDriver driver;

    @Parameters ({"url","driverName","driverLocation"})
    @BeforeTest
    public void setUpTest(String url, String driverName, String driverLocation) {
        System.out.println("Test is set up");
        System.setProperty(driverName, driverLocation);
        driver = new OperaDriver();
        driver.navigate().to(url);
        String title = driver.getTitle();
        Assert.assertEquals(title, "InstantWP ‹ Log In");
    }
    @Parameters({"userName", "password"})
    @Test
    public void logInTest(String userName, String password) {
        userName(userName);
        password(password);
        logIn();
    }

    public void userName(String name){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement userName = driver.findElement(By.xpath("//*[@id=\"user_login\"]"));
        Assert.assertNotNull(name);
        userName.sendKeys(name);
        Assert.assertEquals(name, "admin");
    }

    public void password(String password) {
        WebElement userPassword = driver.findElement(By.xpath("//*[@id=\"user_pass\"]"));
        Assert.assertNotNull(userPassword);
        userPassword.sendKeys(password);
        Assert.assertEquals(password, "password");
    }

    public void logIn() {
        WebElement logIn = driver.findElement(By.xpath("//*[@id=\"wp-submit\"]"));
        logIn.click();
        Assert.assertNotNull(logIn);
    }
    @Parameters({"title", "text"})
    @Test(dependsOnMethods = {"logInTest"})
    public void writePostTest(String title, String text){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement writePost = driver.findElement(By.xpath("//*[@class=\"welcome-icon welcome-write-blog\"]"));
        writePost.click();
        Assert.assertNotNull(writePost);
        addTitle(title);
        addText(text);
        publishPost();
    }
    public void addTitle(String title){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement postTitle = driver.findElement(By.xpath("//*[@name=\"post_title\"]"));
        Assert.assertNotNull(postTitle);
        postTitle.sendKeys(title);
        Assert.assertEquals(title, "My first post");
    }
    public void addText(String text){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.switchTo().frame("content_ifr");
        WebElement postText = driver.findElement(By.xpath("//*[@id=\"tinymce\"]"));
        Assert.assertNotNull(postText);
        postText.sendKeys(text);
        Assert.assertEquals(text, "Here is my first post! Enjoy!");
        driver.switchTo().parentFrame();
    }
    public void publishPost(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement publishButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@name=\"publish\"]")));
        publishButton.click();
        Assert.assertNotNull(publishButton);
    }

    @AfterTest
        public void tearDownTest(){
            if(driver!=null){
                driver.quit();
            }

        }

    }
