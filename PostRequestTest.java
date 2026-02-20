import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class PostRequestTest {
    
    @Test
    public void testPostRequest() {
        RestAssured.baseURI = "https://postman-echo.com";
        
        // Создаем тело запроса как на скриншоте
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("test", "value");
        
        // Текст из скриншота: "This is expected to be sent back as part of response body."
        String expectedText = "This is expected to be sent back as part of response body.";
        
        given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/post")
        .then()
            .statusCode(200)                                   // pm.test("response is ok")
            .body("data", containsString(expectedText))        // pm.test с проверкой data
            .body("json.test", equalTo("value"))               // проверка отправленных данных
            .body("headers.content-type", containsString("application/json"));
    }
    
    @Test
    public void testPostRequestFullResponse() {
        RestAssured.baseURI = "https://postman-echo.com";
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("test", "value");
        
        String expectedText = "This is expected to be sent back as part of response body.";
        
        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/post")
        .then()
            .extract()
            .response();
        
        // Проверка статус кода
        assertEquals(200, response.statusCode());
        
        // Проверка, что текст возвращается в ответе
        String data = response.jsonPath().getString("data");
        assertTrue(data.contains(expectedText));
        
        // Проверка отправленных данных
        assertEquals("value", response.jsonPath().getString("json.test"));
        
        // Проверка метода (как в pm.test("testing method"))
        String url = response.jsonPath().getString("url");
        assertTrue(url.contains("/post"));
    }
}
