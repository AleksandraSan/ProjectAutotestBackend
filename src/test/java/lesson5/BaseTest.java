package lesson5;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {
     Properties properties = new Properties();
     private static InputStream parameters;

    static {
        try {
            parameters = new FileInputStream("src/main/resources/test.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void setSavedId(String newId) {
        properties.load(parameters);
        properties.setProperty("NewID", newId);
        FileOutputStream flow = new FileOutputStream("src/main/resources/test.properties");
        properties.store(flow, "This is my test prop file");
    }
    @SneakyThrows
    public Integer getSavedId() {
        properties.load(parameters);
        return Integer.parseInt(properties.getProperty("NewID"));
    }
}
