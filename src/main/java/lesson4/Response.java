package lesson4;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnore;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Библиотека lombok (предназначена для сокращения кода в
классах) с помощью аннотаций
 */

@JsonInclude(JsonInclude.Include.NON_NULL)// non null - если какое то значение будет null мы не передаем его в json
@JsonPropertyOrder({// определяет порядок  последовательности частей  в json
        "cuisine",
        "cuisines",
        "confidence"
})
@Data

public class Response {
    @JsonProperty("cuisine")
    private String cuisine;
    @JsonProperty("cuisines")
    private List<String> cuisines = null;
    @JsonProperty("confidence")
    private Double confidence;
    @JsonIgnore // атрибуты которые не участвуют в процессе сериализации и дессиарилизации
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
