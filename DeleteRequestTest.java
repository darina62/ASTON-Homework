import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteRequestTest {
    
    @Test
    public void testDeleteRequest() {
        RestAssured.baseURI = "https://postman-echo.com";
        
        String expectedText = "This is expected to be sent back as part of response body.";
        
        given()
            .param("id", "123")  
        .when()
            .delete("/delete")
        .then()
            .statusCode(200)                                   
            .body("data", containsString(expectedText))        
            .body("url", containsString("/delete"));           
    }
    
    @Test
    public void testDeleteRequestFullResponse() {
        RestAssured.baseURI = "https://postman-echo.com";
        
        String expectedText = "This is expected to be sent back as part of response body.";
        
        Response response = given()
            .param("id", "123")
        .when()
            .delete("/delete")
        .then()
            .extract()
            .response();
        
        // Проверка статус кода
        assertEquals(200, response.statusCode());
        
        // Проверка, что текст возвращается в ответе
        String data = response.jsonPath().getString("data");
        assertTrue(data.contains(expectedText));
        
        // Проверка параметров
        assertEquals("123", response.jsonPath().getString("args.id"));
        
        // Проверка метода DELETE
        String url = response.jsonPath().getString("url");
        assertTrue(url.contains("/delete"));
    }
}
