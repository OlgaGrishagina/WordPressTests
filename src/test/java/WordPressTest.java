/**
 * Created by admin on 12.05.2019.
 */

import org.openqa.selenium.*;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.openqa.selenium.opera.OperaOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

public class WordPressTest {
    public WebDriver driver;
    String url = "http://127.0.0.1:10080/wordpress/wp-login.php";

    @BeforeMethod
    public void setUpTest() {
        System.out.println("Test is set up");
        System.setProperty("webdriver.opera.driver", "C:\\Users\\admin\\IdeaProjects\\WordPressTest\\src\\test\\tools\\operadriver.exe");
        OperaOptions options = new OperaOptions();
        options.setBinary("C:\\Users\\admin\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        driver = new OperaDriver(options);
        driver.navigate().to(url);
    }

    @Test
    public void wordPressTest() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "InstantWP ‹ Log In");
        userNameTest();
        passwordTest();
        logInTest();
        writePostTest();
    }

    public void userNameTest(){
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement userName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"user_login\"]")));
        Assert.assertNotNull(userName);
        String name = "admin";
        userName.sendKeys(name);
        Assert.assertEquals(name, "admin");
    }

    public void passwordTest() {
        WebDriverWait wait = new WebDriverWait(driver, 0);
        WebElement userPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"user_pass\"]")));
        Assert.assertNotNull(userPassword);
        String password = "password";
        userPassword.sendKeys(password);
        Assert.assertEquals(password, "password");
    }

    public void logInTest() {
        WebDriverWait wait = new WebDriverWait(driver, 0);
        WebElement logIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"wp-submit\"]")));
        logIn.click();
        Assert.assertNotNull(logIn);
    }
    public void writePostTest(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement writePost = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"welcome-icon welcome-write-blog\"]")));
        writePost.click();
        Assert.assertNotNull(writePost);
        addTitle();
        addText();
        publishPost();
    }
    public void addTitle(){
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement postTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@name=\"post_title\"]")));
        Assert.assertNotNull(postTitle);
        String title = "My first post";
        postTitle.sendKeys(title);
        Assert.assertEquals(title, "My first post");
    }
    public void addText(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        driver.switchTo().frame("content_ifr");
        WebElement postText = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"tinymce\"]")));
        Assert.assertNotNull(postText);
        String text = "Here is my first blog! Enjoy!";
        postText.sendKeys(text);
        Assert.assertEquals(text, "Here is my first blog! Enjoy!");
        driver.switchTo().parentFrame();
    }
    public void publishPost(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement publishButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@name=\"publish\"]")));
        publishButton.click();
        Assert.assertNotNull(publishButton);
    }

    @AfterMethod
        public void tearDownTest(){
            if(driver!=null){
                driver.quit();
            }

        }

    }
