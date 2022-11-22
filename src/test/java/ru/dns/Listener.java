package ru.dns;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Listener implements WebDriverListener {

    private Logger logger = LogManager.getLogger(Listener.class);
    // Счетчик для нумерации скринов
    private int count = 1;

    // После нажатия на веб элемент
    @Override
    public void afterClick(WebElement element) {
        logger.info("Произведено нажатие.");
    }

    // После поиска веб элемента
    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        logger.info("Найден веб элемент.");

        // Сделать скриншот всей веб страницы
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ScreenFromListener"+count+".png"));
            logger.info("Скриншот сохранен.");
            count++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.head.scrollHeight)");

    }

    // После поиска веб элементов
    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        logger.info("Найдены веб элементы.");
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\ScreenshotFromListener"+count+".png"));
            logger.info("Скриншот сохранен.");
            count++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.head.scrollHeight)");

    }

    // После получения текста
    @Override
    public void afterGetText(WebElement element, String result) {
        logger.info("Получен текст.");
    }


}
