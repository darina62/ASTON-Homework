import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatchRequestTest {
    
    @Test
    public void testPatchRequest() {
        RestAssured.baseURI = "https://postman-echo.com";
        
        // Создаем тело запроса
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("test", "value");
        
        String expectedText = "This is expected to be sent back as part of response body.";
        
        given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .patch("/patch")
        .then()
            .statusCode(200)                                   
            .body("data", containsString(expectedText))        
            .body("json.test", equalTo("value"))               
            .body("url", containsString("/patch"));            
    }
    
    @Test
    public void testPatchRequestFullResponse() {
        RestAssured.baseURI = "https://postman-echo.com";
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("test", "value");
        
        String expectedText = "This is expected to be sent back as part of response body.";
        
        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .patch("/patch")
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
        
        // Проверка метода PATCH
        String url = response.jsonPath().getString("url");
        assertTrue(url.contains("/patch"));
    }
}
