package lesson5.api;

import lesson5.dto.GetCategoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("categories/{id}")//c помощью аннотации вызываем необходимый метод , value-это суффикс (динамическая часть адреса)
    Call<GetCategoryResponse> getCategory(@Path("id") int id);
    //Call-вызов (внутри определяем возвращаемый объект
    //GetCategoryResponse-json (пакет dto)
    //Прописываем то,что вставляем в запрос @path-pathparam , query = queryparams
}
