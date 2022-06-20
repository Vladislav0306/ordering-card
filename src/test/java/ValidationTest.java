import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ValidationTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFailIfNameIsNotRussian() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Ezhkov Vladislav");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79183477293");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'name')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfNameMadeOfNumbers() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("12345");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79183477293");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'name')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfNameFromSpecialCharacters() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("@#$%*");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79183477293");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'name')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfNameInputIsEmpty() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79183477293");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'name')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfWrongNumber10Characters() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Ежков Владислав");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+7918347729");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'phone')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfWrongNumber12Characters() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Ежков Владислав");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+791834772930");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'phone')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfNumberOfLetters() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Ежков Владислав");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("абвгд");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'phone')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfNumberOfSpecialCharacters() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Ежков Владислав");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("@#$%*");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'phone')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfCheckboxIsEmpty() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Ежков Владислав");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79183477293");
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//label[contains(@class, 'input_invalid')]//span[contains(@class, 'checkbox__text')]")).getText();
        System.out.println(actualMessage);
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
