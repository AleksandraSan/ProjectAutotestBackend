package lesson5;

import com.github.javafaker.Faker;
import lesson5.api.ProductService;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateProductTest extends BaseTest{

    static ProductService productService;
    Product product = null;
    Product product1 = null;
    //библиотека Faker - рандомизация стринговых литералов
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }


    //Получаем весь список существующих товаров
    @Test
    void getProductsTest() throws IOException {
        Response<ResponseBody> response = productService.getProducts()
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }


    //Создаём случайный товар запросом POST, сохраняем ID в пропертю
    @Test
    @DisplayName("Создать продукт категории еды")
      void createProductInFoodCategoryTest() throws IOException {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
        Response<Product> response = productService.createProduct(product)
                .execute();
        assert response.body() != null;
        setSavedId(response.body().getId().toString());
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    //Тест, провяряющий, что при запросе созданного товара по id , возвращется наш товар
    @Test
    void getProductByIdTest() throws IOException {
        Response<Product> response = productService.getProductById(getSavedId()).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(getSavedId()));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));
    }

    //В третьем тесте, исправим цену у нашего товара запросом PUT
    @Test
    @DisplayName("Изменение информации о продукте")
    void modificationProductTest() throws IOException {
        product1 = new Product()
                .withId(getSavedId())
                .withCategoryTitle("Food")
                .withPrice(1000);
        Response<Product> response = productService.modifyProduct(product1)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        Assertions.assertEquals(1000, response.body().getPrice());
    }

    //проверка удаления
    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(getSavedId()).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}
