package ru.dns;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    // Выбор драйвера для конкретного браузера по его названию
    public static WebDriver getDriver(String browserName, String pageLoadStrategy) throws Exception {
        switch (browserName){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                logger.info("Драйвер для браузера Google Chrome");
                // Добавление свойств браузера Google Chrome
                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.valueOf(pageLoadStrategy.toUpperCase()));
                logger.info("PageLoadStrategy - " + pageLoadStrategy);
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");
                return new ChromeDriver(options);
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                logger.info("Драйвер для браузера Mozilla Firefox");
                // Добавление свойств браузера Firefox
                FirefoxOptions optionsF = new FirefoxOptions();
                optionsF.setPageLoadStrategy(PageLoadStrategy.valueOf(pageLoadStrategy.toUpperCase()));
                logger.info("PageLoadStrategy - " + pageLoadStrategy);
                optionsF.addArguments("--kiosk");
                optionsF.addArguments("-private");
                return new FirefoxDriver(optionsF);
            default:
                throw new RuntimeException("Введено некорректное название браузера");
        }
    }
}