//automation for logging an e-commerce website then adding an item and giving my
// personal information and after confirming my order taking a screenshot.



import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class Main {

  public static void main(String[] args) throws IOException {

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--start-maximized");

    WebDriver driver = new ChromeDriver(options);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Step 1: Open website
    driver.manage().deleteAllCookies();
    driver.get("https://www.saucedemo.com/");

    // Step 2: Login
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();

    // Wait until inventory page loads
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

    // Step 3: Add first product to cart
    driver.findElement(By.cssSelector(".inventory_item button")).click();

    // Step 4: Go to cart
    driver.findElement(By.className("shopping_cart_link")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

    // Step 5: Click Checkout
    driver.findElement(By.id("checkout")).click();

    // Step 6: Fill customer information
    driver.findElement(By.id("first-name")).sendKeys("Ayush");
    driver.findElement(By.id("last-name")).sendKeys("Kumar");
    driver.findElement(By.id("postal-code")).sendKeys("751001");

    driver.findElement(By.id("continue")).click();

    // Step 7: Finish order
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("summary_info")));
    driver.findElement(By.id("finish")).click();

    // Step 8: Validate order success
    WebElement confirmation = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.className("complete-header"))
    );

    System.out.println("Order Status: " + confirmation.getText());

    // Step 9: Take Screenshot
    TakesScreenshot ts = (TakesScreenshot) driver;
    File source = ts.getScreenshotAs(OutputType.FILE);
    File destination = new File("order_confirmation.png");

    Files.copy(source.toPath(), destination.toPath(),
            StandardCopyOption.REPLACE_EXISTING);

    System.out.println("Screenshot saved successfully!");

    driver.quit();
  }
}