package lesson5.utils;

import lombok.experimental.UtilityClass;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;


@UtilityClass //все методы класса статические
public class RetrofitUtils {

    Properties prop = new Properties(); //создается объект property
    private static InputStream configFile; //читается из файла

    static {
        try {
            configFile = new FileInputStream("src/main/resources/my.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows //генерирует exception и скрывает их
    public String getBaseUrl() {
        prop.load(configFile);//метод load в классе property генерирует in out exception
        return prop.getProperty("url");
    }


    //определяяем перехватчики
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(); //стандарный от библиотеки okhttp3
    LoggingInterceptor logging2 = new LoggingInterceptor();//самописный перехватчик
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();//доступ к клиенту okhttp3 для настройки
    //в ретрофит Utils может быть несколько retrofit клиентов с разным содержимым и билдерами


    /*    public Retrofit getRetrofit(){ //статический метод(т.к. есть Util) ,настройка библиотеки Retrofit
        return new Retrofit.Builder() //настраивается через builder
                .baseUrl(getBaseUrl())//в нашем случае настраивается только url
                .addConverterFactory(JacksonConverterFactory.create()) //фабрика, билдер возвращает некую реализацию класса
                //добавляем конвектор и указываем что для сериализации и наоборот мы используем Jackson
                .build();
    }*/

    public Retrofit getRetrofit(){
        logging.setLevel(BODY);//определяем уровень логирования
        httpClient.addInterceptor(logging2);//создаем и настраиваем http клиент на использование перехватчика
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl()) //урл
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

}
