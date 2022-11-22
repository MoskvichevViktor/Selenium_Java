package ru.dns;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Locale;

public class CaseHW02Test {

    private String brendTestValue = "ASUS";
    private String RAMTestValue = "32 ГБ";
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(CaseHW02Test.class);

    // Счетчик для нумерации скринов
    private int count = 1;

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
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

    @Test
    public void thirdCase() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Регистрация слушателя событий
        Listener listener = new Listener();
        WebDriver eventFiringWebDriver = new EventFiringDecorator<>(listener).decorate(driver);

        Actions actions = new Actions(eventFiringWebDriver);

        // Открываем страницу DNS
        eventFiringWebDriver.get("https://www.dns-shop.ru/");

        // Проверка загрузки страницы (Проверка на наличие элемента в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Скриншот всей страницы (с прокруткой) после загрузки страницы
        makeScreenshotFullPage();

        // Убираем всплывающее окно
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=\"Всё верно\"]/parent::button")));
        WebElement buttonAssept = eventFiringWebDriver.findElement(By.xpath("//span[text()=\"Всё верно\"]/parent::button"));
        buttonAssept.click();

        // Обновляем страницу
        eventFiringWebDriver.navigate().refresh();

        // Наводим курсор на ссылку ПК, ноутбуки, периферия
        wait.until(ExpectedConditions.visibilityOf(eventFiringWebDriver.findElement(By.xpath("//a[text()='ПК, ноутбуки, периферия']"))));
        WebElement linkComputerCategory = eventFiringWebDriver.findElement(By.xpath("//a[text()='ПК, ноутбуки, периферия']"));
        actions.moveToElement(linkComputerCategory)
            .perform();

        // Проверка загрузки страницы (Проверка на наличие элемента в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Скриншот всей страницы (с прокруткой) после загрузки страницы
        makeScreenshotFullPage();

        // Переходим по ссылке Ноутбуки
        WebElement linkLaptopCategory = eventFiringWebDriver.findElement(By.xpath("//*[text() ='Ноутбуки']"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text() ='Ноутбуки']")));
        linkLaptopCategory.click();

        // Проверка загрузки страницы (Проверка на наличие элемента в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Скриншот всей страницы (с прокруткой) после загрузки страницы
        makeScreenshotFullPage();

        // Скрываем блок страницы
        WebElement blockHeader = eventFiringWebDriver.findElement(By.xpath("//header"));
        JavascriptExecutor js = (JavascriptExecutor)eventFiringWebDriver;
        js.executeScript("arguments[0].style.display='none';", blockHeader);

        // Скриншот всей страницы (с прокруткой) после скрытия блока
        wait.until(ExpectedConditions.invisibilityOf(eventFiringWebDriver.findElement(By.xpath("//header"))));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        makeScreenshotFullPage();

        // Выбираем производителя ASUS
        wait.until(ExpectedConditions.visibilityOf(eventFiringWebDriver.findElement(By.xpath("//input[@placeholder='Поиск']"))));
        WebElement inputBrand = eventFiringWebDriver.findElement(By.xpath("//input[@placeholder='Поиск']"));
        actions.scrollToElement(inputBrand)
                .perform();
        inputBrand.click();
        inputBrand.sendKeys("ASUS");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='ASUS  ']")));
        WebElement checkBoxBrand = eventFiringWebDriver.findElement(By.xpath("//span[text()='ASUS  ']"));
        checkBoxBrand.click();

        // Выбираем раздел Объем оперативной памяти (ГБ)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Объем оперативной памяти (ГБ)']")));
        WebElement accordeonRAM = eventFiringWebDriver.findElement(By.xpath("//span[text()='Объем оперативной памяти (ГБ)']"));
        accordeonRAM.click();

        // Выбираем значение 32 ГБ
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='32 ГБ  ']")));
        WebElement checkBoxMemoryType = eventFiringWebDriver.findElement(By.xpath("//span[text()='32 ГБ  ']"));
        checkBoxMemoryType.click();

        // Нажимаем кнопку Применить
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Применить']")));
        WebElement buttonApply = eventFiringWebDriver.findElement(By.xpath("//button[text()='Применить']"));
        actions.scrollToElement(buttonApply)
                .click()
                .perform();

        // Проверка загрузки страницы (Проверка на наличие элемента в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Скриншот всей страницы (с прокруткой) после загрузки страницы
        makeScreenshotFullPage();

        // Переходим в меню Сортировка
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Сортировка:']")));
        WebElement accordeonSort= eventFiringWebDriver.findElement(By.xpath("//span[text()='Сортировка:']"));
        accordeonSort.click();

        // Выбираем раздел Сначала дорогие
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Сначала дорогие']")));
        WebElement radiobuttonExpensive = eventFiringWebDriver.findElement(By.xpath("//*[text()='Сначала дорогие']"));
        radiobuttonExpensive.click();

        // Обновляем страницу для построения списка согласно требованию - Сначала дорогие
        eventFiringWebDriver.navigate().refresh();

        // Скриншот всей страницы (с прокруткой) после применения сортировки
        // Проверка загрузки страницы (Проверка на наличие элемента в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Скриншот всей страницы (с прокруткой) после загрузки страницы
        makeScreenshotFullPage();

        //Получаем название ноутбука для дальнейшей проверки на соответствие ожидаемому заголовку страницы
        wait.until(ExpectedConditions.visibilityOf(eventFiringWebDriver.findElement(By.xpath("//div[@data-id='product']/a"))));
        WebElement linkFirstElement = eventFiringWebDriver.findElement(By.xpath("//div[@data-id='product']/a"));
        String expectedTitle = linkFirstElement.getText();
        expectedTitle = expectedTitle.substring(0, expectedTitle.indexOf("["));

        // Получаем ссылку на страницу ноутбука первого в списке
        String urlFirstElement = linkFirstElement.getAttribute("href");

        // Перейти на страницу первого продукта в списке
        // Страница открывается в новом окне
        // Страница открывается в максимизированном режиме (не fullscreen)
        eventFiringWebDriver.switchTo().newWindow(WindowType.WINDOW);
        eventFiringWebDriver.manage().window().maximize();
        eventFiringWebDriver.get(urlFirstElement);

        // Проверка загрузки страницы (Проверка на наличие элемента в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='footer__main']")));
        // Скриншот всей страницы (с прокруткой) после загрузки страницы
        makeScreenshotFullPage();

        // Проверить, что заголовок страницы соответствует ожидаемому
        // Название соответствует названию в списке на предыдущей странице
        Assertions.assertTrue(eventFiringWebDriver.getTitle().contains(expectedTitle), "Заголовок страницы НЕ соответствует ожидаемому.");
        logger.info("Заголовок страницы соответствует ожидаемому.");

        // Проверить, что в блоке Характеристики заголовок содержит ASUS
        wait.until(ExpectedConditions.visibilityOf(eventFiringWebDriver.findElement(By.xpath("//div[@class='product-card-description__title']"))));
        WebElement blockCharacteristics = eventFiringWebDriver.findElement(By.xpath("//div[@class='product-card-description__title']"));
        Assertions.assertTrue(blockCharacteristics.getText().contains(brendTestValue),
                "В блоке Характеристики заголовок НЕ содержит ASUS.");
        logger.info("В блоке Характеристики заголовок содержит ASUS.");

        // Разворачиваем список характеристик
        wait.until(ExpectedConditions.elementToBeClickable(eventFiringWebDriver.findElement(By.xpath("//button[text() = 'Развернуть все']"))));
        WebElement buttonAllCharacteristics = eventFiringWebDriver.findElement(By.xpath("//button[text() = 'Развернуть все']"));
        buttonAllCharacteristics.click();

        // Проверка, что в блоке Характеристики значение Объем оперативной памяти равно 32 ГБ
        wait.until(ExpectedConditions.visibilityOf(eventFiringWebDriver.findElement(By.xpath("//div[text() = ' Объем оперативной памяти ']/following-sibling::div"))));
        WebElement textBoxRAM = eventFiringWebDriver.findElement(By.xpath("//div[text() = ' Объем оперативной памяти ']/following-sibling::div"));
        actions.scrollToElement(textBoxRAM)
                .perform();
        Assertions.assertTrue(textBoxRAM.getText().contains(RAMTestValue),
                "В блоке Характеристики значение Объем оперативной памяти НЕ равно 32 ГБ.");
        logger.info("В блоке Характеристики значение Объем оперативной памяти равно 32 ГБ.");

    }
    // Метод скриншотирования всей веб страницы.
    private void makeScreenshotFullPage() {
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ScreenshotFromCaseHW02"+count+".png"));
            logger.info("Скриншот сохранен.");
            count++;
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.head.scrollHeight)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
