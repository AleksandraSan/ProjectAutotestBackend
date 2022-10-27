package lesson5;

import lesson5.utils.RetrofitUtils;
import lesson5.api.CategoryService;
import lesson5.dto.GetCategoryResponse;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetCategoryTest {
    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll(){
    /*получаем экземпляр реализации categoryService- обращаемся к классу RetrofitUtils, вызываем его метод RetrofitUtils
     и с учетом его настроек получаем реализацию CategoryService,чем получаем доступ к его сервисам
     */
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        // обращаемся к классу  categoryService и вызываем у него публичный апи ( методы в классе) и делаем execute
        //в итоге получаем объект responce внутри которого body - GetCategoryResponse
        // итак мы имеем доступ и к ответу и к json и пишем проверки
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Food")));
    }
    //перехватчик - получает доступ к запросу или ответу
}
