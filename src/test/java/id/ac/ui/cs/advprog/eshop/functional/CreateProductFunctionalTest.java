package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context
     */
    @LocalServerPort
    private int serverPort;

    /**
     * the base URL for testing. Default to {@code http://localhost}
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        //Exercise
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();

        //Verify
        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void header_isCorrect(ChromeDriver driver) throws Exception {
        //Exercise
        driver.get(baseUrl);
        String header = driver.findElement(By.tagName("h3")).getText();

        //Verify
        assertEquals("Create New Product", header);
    }

    @Test
    void rerouting_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);

        //clicks the submit button
        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        //checks if the submit button reroutes to the product list
        String pageTitle = driver.getTitle();
        assertEquals("Product List", pageTitle);
    }
    @Test
    void creatingProductTest(ChromeDriver driver) throws  Exception {
        driver.get(baseUrl);

        //fills in the name input
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        String name = "Sampo Cap Bambang";
        nameInput.sendKeys(name);

        //fills in the quantity input
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        String quantity = "100";
        quantityInput.sendKeys(quantity);

        //clicks the submit button
        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        //checks if the submit button reroutes to the product list
        String pageTitle = driver.getTitle();
        assertEquals("Product List", pageTitle);

        //checks table for created product
        List<WebElement> cells = driver.findElements(By.tagName("td"));
        assertEquals("Sampo Cap Bambang", cells.get(0).getText());
        assertEquals("100", cells.get(1).getText());
    }
}

