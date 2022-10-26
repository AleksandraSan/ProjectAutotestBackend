package lesson3.homework3.balldontlie;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AbstractClass {
    //в абстрактный класс выносится вся основная логика
    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;

    @BeforeAll
    static void initTest() throws IOException {
        // получаем объект типа InputStream из файла (путь прописан)
        configFile = new FileInputStream("src/main/resources/balldontlie.properties");
        //загружаем файл в обьект типа Properties
        prop.load(configFile);
        //определяем статические переменные
        baseUrl= prop.getProperty("baseUrl");
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
