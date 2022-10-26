package lesson4.homework;

import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lesson4.homework.AbstractTest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class MealPlanningTest {

    @Test
    @Story("Working with Shopping List")
    @DisplayName("Add to Shopping List")
    public void addShoppingList() {
        //Connect User , получение хэша пользователя
        String hash = given()
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"username\":" + getUsername() + ",\n"
                        + " \"firstname\":" + getFirstname() + ",\n"
                        + " \"lastname\": " + getLastname() + ",\n"
                        + " \"email\":" + getEmail() + ",\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/users/connect").prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("hash")
                .toString();

                 lesson4.homework4.Response response = (lesson4.homework4.Response) given().spec(getRequestSpecification())
                .queryParam("hash", hash )
                .pathParam("username", getUsername())
                .body("{\n"
                        + " \"item\": 1 package baking powder,\n"
                        + " \"aisle\":Baking,\n"
                        + " \"parse\": true,\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/{username}/shopping-list/items").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(Response.class); //сохраняем body в класс

        assertThat(response.getAisle(), containsString("Baking"));// проверяем что ответ содержит
    }


    @Test
    @DisplayName("Get Shopping List")
    void getShoppingListTest(){
        given().spec(getRequestSpecification())
                .header("Content-Type" ,"application/json")
                .when()
                .get(getBaseUrl() + "/mealplanner/alex-cool-user/shopping-list")
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    @DisplayName("Delete from Shopping List")
    void deleteShoppingList(){
        given().spec(getRequestSpecification())
                .queryParam("id", "1307359")
                .when()
                .delete(getBaseUrl() + "/mealplanner/alex-cool-user/shopping-list/items")
                .then()
                .spec(getResponseSpecification());
    }
}
