package lesson3.homework3.mealplanner;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lesson3.homework3.mealplanner.AbstractTest.*;
import static org.hamcrest.Matchers.lessThan;

public class RecipesTest {

    @Test
    @DisplayName("Поиск рецепта по слову")
    void SearchRecipesWithQueryTest() {
        //поиск рецепта с параметром query
        given().queryParam("apiKey", getApiKey())
                .queryParam("query", getQuery())
                .when()
                .get(getBaseUrl() + "/recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .header("Connection", "keep-alive")
                .time(lessThan(4000L));
    }


    @Test
    @DisplayName("поиск рецепта с параметрами diet , includeIngredients")
    void SearchWithDietAndIngredientsTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("diet", getDiet())
                .queryParam("includeIngredients", getIncludeIngredients())
                .when().get(getBaseUrl() + "/recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .time(lessThan(4000L));
        //.contentType(ContentType.JSON);
    }


    @Test
    @DisplayName("поиск рецепта с параметрами excludeIngredients, addRecipeInformation")
    void SearchWithExcludeIngredientsTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("excludeIngredients", getExcludeIngredients())
                .queryParam("addRecipeInformation", true)
                .when()
                .get(getBaseUrl() + "/recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                //.contentType(ContentType.JSON)
                .time(lessThan(4000L));
    }

    @Test
    @DisplayName("Поиск рецепта с параметрами")
    void SearchWithMinMaxCaloriesTest(){
        given()
                .queryParam("minCalories", 60)
                .queryParam("maxCalories", 700)
                .queryParam("apiKey", getApiKey())
                .when()
                .get(getBaseUrl() + "/recipes/complexSearch" )
                .then()
                .assertThat()
                //.contentType(ContentType.JSON)
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(4000L));
    }

    @Test
    @DisplayName("Поиск рецепта по тексту, который должен быть найден в названии рецептов")
    void SearchWithTitleMatchTest(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", getTitleMatch())
                .when()
                .get(getBaseUrl() + "/recipes/complexSearch")
                .then()
                .assertThat()
                //.contentType(ContentType.JSON)
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(4000L));
    }


    @Test
    @DisplayName("Классификация кухни по названию рецепта")
    void CuisineRecepiesTest(){
        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .queryParam("title", getTitle())
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(4000L));
        //.contentType(ContentType.JSON);

    }

    @Test
    @DisplayName("Классификация рецептов по названию реценпта и ингредиентам")
    void CuisineByNameAndIngredientsTest(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", getTitle())
                .queryParam("ingredientList", getIngredientList())
                .header("ContentType","application/x-www-form-urlencoded")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(4000L))
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Классификация рецептов по названию рецепта и ингредиентам с выбором языка")
    void CuisineByNameIngredientsLangTest(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", getTitle())
                .queryParam("ingredientList", getIngredientList())
                .queryParam("language", "en")
                .header("ContentType","application/x-www-form-urlencoded")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(4000L))
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Классификация кухни по рецепту - ингридиенты и язык")
    void CuisineByIngredientsAndLangTest(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("ingredientList",getIngredientList())
                .queryParam("language","en")
                .header("ContentType","application/x-www-form-urlencoded")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .contentType(ContentType.JSON)
                .time(lessThan(4000L));
    }

    @Test
    @DisplayName("Классификация кухни по рецепту - заголовок и язык")
    void СuisineByNameAndLang(){
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title",getTitle())
                .queryParam("language","en")
                .header("ContentType","application/x-www-form-urlencoded")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .contentType(ContentType.JSON)
                .time(lessThan(4000L));
    }
}
