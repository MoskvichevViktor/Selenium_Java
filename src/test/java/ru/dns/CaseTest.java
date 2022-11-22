package ru.dns;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CaseTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(CaseTest.class);

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    // Чтение передаваемого параметра pageLoadStrategy (-DpageLoadStrategy)
    String pls = System.getProperty("pageLoadStrategy", "normal");

    // Счетчик для нумерации скринов
    private int count = 1;

    @BeforeEach
    public void setUp() throws Exception {
        logger.info("env = " + env);
        logger.info("pls = " + pls);
        driver = WebDriverFactory.getDriver(env.toLowerCase(), pls.toLowerCase(Locale.ROOT));
        logger.info("Драйвер стартовал!");
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    @Test
    public void firstCase() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Открываем страницу DNS
        driver.get("https://www.dns-shop.ru/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        makeScreenshotFullPage();

        // Выводим в логи заголовок
        logger.info("Заголовок страницы - " + driver.getTitle());

        // Выводим в логи url
        logger.info("Текущий URL - " + driver.getCurrentUrl());

        // Выводим в логи размер окна браузера
        logger.info("Размеры окна браузера - " + driver.manage().window().getSize());

        // Убираем всплывающее окно
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=\"Всё верно\"]/parent::button")));
        WebElement buttonAssept = driver.findElement(By.xpath("//span[text()=\"Всё верно\"]/parent::button"));
        buttonAssept.click();

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Обновляем страницу
        driver.navigate().refresh();

        // Переходим по ссылке Бытовая техника
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Бытовая техника']")));
        WebElement linkTechnique = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        linkTechnique.click();

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Проверка на отображения текста Бытовая техника
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[@class='subcategory__page-title']"))));
        WebElement textBoxTechnique = driver.findElement(By.xpath("//h1[@class='subcategory__page-title']"));
        Assertions.assertEquals("Бытовая техника", textBoxTechnique.getText(), "Текст Бытовая техника не отображается.");
        logger.info("Проверка на отображения текста Бытовая техника - пройдена!");

        // Переходим по ссылке Техника для кухни (kitchenAppliances)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = 'Техника для кухни']")));
        WebElement linkKitchenAppliances = driver.findElement(By.xpath("//span[text() = 'Техника для кухни']"));
        linkKitchenAppliances.click();

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Проверка на отображения текста Техника для кухни
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[@class='subcategory__page-title']"))));
        WebElement textBoxKitchenAppliances = driver.findElement(By.xpath("//h1[@class='subcategory__page-title']"));
        Assertions.assertEquals("Техника для кухни", textBoxKitchenAppliances.getText(), "Текст Техника для кухни не отображается.");
        logger.info("Проверка на отображения текста Техника для кухни - пройдена!");

        // Проверка на отображения ссылки Собрать свою кухню
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[text() = 'Собрать свою кухню']"))));
        WebElement linkMakeKitchen = driver.findElement(By.xpath("//a[text() = 'Собрать свою кухню']"));
        Assertions.assertEquals("Собрать свою кухню", linkMakeKitchen.getText(), "Ссылка Собрать свою кухню не отображается.");
        logger.info("Проверка на отображения ссылки Собрать свою кухню - пройдена!");

        // Выводим в логи названия всех категорий раздела Техника для кухни
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//span[@class='subcategory__title']"))));
        List<WebElement> kitchenAppliancesCategories = driver.findElements(By.xpath("//span[@class='subcategory__title']"));
        for (WebElement element : kitchenAppliancesCategories) {
            logger.info("Техника для кухни - " + element.getText());
        }

        // Проверка, что количество категорий раздела Техника для кухни больше пяти
        Assertions.assertTrue(kitchenAppliancesCategories.size() > 5, "Количество категорий меньше или равно пяти");
        logger.info("Проверка на количество категорий раздела Техника для кухни  - пройдена!");
    }

    @Test
    public void secondCase() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Открываем страницу DNS
        driver.get("https://www.dns-shop.ru/");

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Убираем всплывающее окно
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=\"Всё верно\"]/parent::button")));
        WebElement buttonAssept = driver.findElement(By.xpath("//span[text()=\"Всё верно\"]/parent::button"));
        buttonAssept.click();

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Обновляем страницу
        driver.navigate().refresh();

        // Наводим курсор на ссылку Бытовая техника
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[text()='Бытовая техника']"))));
        WebElement linkTechnique = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(linkTechnique)
                .perform();

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Получаем список ссылок
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__first-level']"))));
        List<WebElement> listTechniqueCategories = driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__first-level']"));
        List<String> listTechniqueCategoriesNames = new ArrayList<>();
        for (WebElement element : listTechniqueCategories) {
            logger.info("Бытовая техника - "+element.getText());
            listTechniqueCategoriesNames.add(element.getText());
        }

        // Тестовый список
        List<String> listTest = Arrays.asList("Техника для кухни", "Техника для дома", "Красота и здоровье");

        // Производим проверку на соответствие
        Assertions.assertEquals(listTechniqueCategoriesNames,listTest, "Отображаемые ссылки не прошли проверку на заданное соответствие!");
        logger.info("Проверка на отображение ссылок в разделе Бытовая техника прошла!");

        // Наводим курсор на ссылку Приготовление пищи
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text() ='Приготовление пищи']"))));
        WebElement linkFoodPreparation  = driver.findElement(By.xpath("//*[text() ='Приготовление пищи']"));
        actions.moveToElement(linkFoodPreparation)
                .perform();

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Получаем список веб элементов из раздела Приготовление пищи
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__popup-link']"))));
        List<WebElement> listFoodPreparationCategories = driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__popup-link']"));

        // Список пишем в логи
        for (WebElement element : listFoodPreparationCategories) {
            logger.info("Приготовление пищи - "+element.getText());
        }

        // Проверяем что количество категорий из раздела Приготовление пищи больше пяти
        Assertions.assertTrue(listFoodPreparationCategories.size()>5,
                "Количество категорий из раздела Приготовление пищи больше или равно пяти.");
        logger.info("Проверка на количество категорий раздела Приготовление пищи  - пройдена!");

        // Наводим курсор на плиты (stove) и кликаем
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Плиты']")));
        WebElement linkStove = driver.findElement(By.xpath("//*[text()='Плиты']"));
        actions.moveToElement(linkStove)
                .click()
                .perform();

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Переходим по ссылке Плиты электрические
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Плиты электрические']")));
        WebElement linkElektrikStove = driver.findElement(By.xpath("//span[text()='Плиты электрические']"));
        linkElektrikStove.click();

        // Проверка загрузки страницы (проверка наличия элемента страницы в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Делаем скриншот
        makeScreenshotFullPage();

        // Находим веб элемент содержащий количество найденного товара
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@class='products-count']"))));
        WebElement textProductsCount = driver.findElement(By.xpath("//span[@class='products-count']"));

        //получаем строковое значение и из него извлекаем число
        String s = textProductsCount.getText();
        String[] words = s.split("\\s");
        int count = Integer.parseInt(words[0]);

        // Проводим проверку на соответствие на количества товара
        Assertions.assertTrue(count > 100, "Количество товара в разделе Плиты электрические меньше или равно 100.");
        logger.info("Проверка на количество товара в разделе Плиты электрические - пройдена!");
    }

    // Метод скриншотирования всей веб страницы.
    private void makeScreenshotFullPage() {
        try {

            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ScreenshotFromCaseHW01"+count+".png"));
            logger.info("Скриншот сохранен.");
            count++;
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.head.scrollHeight)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
