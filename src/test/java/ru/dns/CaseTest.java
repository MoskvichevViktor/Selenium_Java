package ru.dns;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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

    @BeforeEach
    public void setUp() throws Exception {
        logger.info("env = " + env);
        logger.info("pls = " + pls);
        driver = WebDriverFactory.getDriver(env.toLowerCase(), pls.toLowerCase(Locale.ROOT));
        logger.info("Драйвер стартовал!");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    @Test
    public void firsCase() {
        // Открываем страницу DNS
        driver.get("https://www.dns-shop.ru/");

        // Выводим в логи заголовок
        logger.info("Заголовок страницы - " + driver.getTitle());

        // Выводим в логи url
        logger.info("Текущий URL - " + driver.getCurrentUrl());

        // Выводим в логи размер окна браузера
        logger.info("Размеры окна браузера - " + driver.manage().window().getSize());

        // Убираем всплывающее окно
        driver.findElement(By.xpath("//span[text()=\"Всё верно\"]/parent::button")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Переходим по ссылке Бытовая техника
        WebElement technique = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        technique.click();

        // Проверка на отображения текста Бытовая техника
        WebElement textTechnique = driver.findElement(By.xpath("//h1[@class='subcategory__page-title']"));
        Assertions.assertEquals("Бытовая техника", textTechnique.getText(), "Текст Бытовая техника не отображается.");
        logger.info("Проверка на отображения текста Бытовая техника - пройдена!");

        // Переходим по ссылке Техника для кухни (kitchenAppliances)
        WebElement kitchenAppliances = driver.findElement(By.xpath("//span[text() = 'Техника для кухни']"));
        kitchenAppliances.click();

        // Проверка на отображения текста Техника для кухни
        WebElement textKitchenAppliances = driver.findElement(By.xpath("//h1[@class='subcategory__page-title']"));
        Assertions.assertEquals("Техника для кухни", textKitchenAppliances.getText(), "Текст Техника для кухни не отображается.");
        logger.info("Проверка на отображения текста Техника для кухни - пройдена!");

        // Проверка на отображения ссылки Собрать свою кухню
        WebElement makeKitchen = driver.findElement(By.xpath("//a[text() = 'Собрать свою кухню']"));
        Assertions.assertEquals("Собрать свою кухню", makeKitchen.getText(), "Ссылка Собрать свою кухню не отображается.");
        logger.info("Проверка на отображения ссылки Собрать свою кухню - пройдена!");

        // Выводим в логи названия всех категорий раздела Техника для кухни
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

        // Открываем страницу DNS
        driver.get("https://www.dns-shop.ru/");

        // Убираем всплывающее окно
        driver.findElement(By.xpath("//span[text()=\"Всё верно\"]/parent::button")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Наводим курсор на ссылку Бытовая техника
        WebElement technique = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(technique);
        actions.perform();

        // Получаем список ссылок
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
        WebElement elementFoodPreparation  = driver.findElement(By.xpath("//*[text() ='Приготовление пищи']"));
        Actions actionsFoodPreparation = new Actions(driver);
        actionsFoodPreparation.moveToElement(elementFoodPreparation);
        actionsFoodPreparation.perform();

        // Получаем список веб элементов из раздела Приготовление пищи
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
        WebElement stove = driver.findElement(By.xpath("//*[text()='Плиты']"));
        Actions actionsStove = new Actions(driver);
        actionsStove
                .moveToElement(stove)
                .click()
                .perform();

        // Переходим по ссылке Плиты электрические
        WebElement elektrikStove = driver.findElement(By.xpath("//span[text()='Плиты электрические']"));
        elektrikStove.click();

        // Находим веб элемент содержащий количество найденного товара
        WebElement productsCount = driver.findElement(By.xpath("//span[@class='products-count']"));

        //получаем строковое значение и из него извлекаем число
        String s = productsCount.getText();
        String[] words = s.split("\\s");
        int count = Integer.parseInt(words[0]);

        // Проводим проверку на соответствие на количества товара
        Assertions.assertTrue(count > 100, "Количество товара в разделе Плиты электрические меньше или равно 100.");
        logger.info("Проверка на количество товара в разделе Плиты электрические - пройдена!");
    }
}
