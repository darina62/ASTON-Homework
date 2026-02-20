import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetRequestTest {
    
    @Test
    public void testGetRequest() {
        RestAssured.baseURI = "https://postman-echo.com";
        
        given()
            .param("foo1", "bar1")
            .param("foo2", "bar2")
        .when()
            .get("/get")
        .then()
            .statusCode(200)
            .body("args.foo1", equalTo("bar1"))
            .body("args.foo2", equalTo("bar2"));
    }
    
    @Test
    public void testGetRequestFullResponse() {
        RestAssured.baseURI = "https://postman-echo.com";
        
        Response response = given()
            .param("foo1", "bar1")
            .param("foo2", "bar2")
        .when()
            .get("/get")
        .then()
            .extract()
            .response();
        
        // Проверка статус кода
        assertEquals(200, response.statusCode());
        
        // Проверка тела ответа
        assertEquals("bar1", response.jsonPath().getString("args.foo1"));
        assertEquals("bar2", response.jsonPath().getString("args.foo2"));
        
        // Дополнительные проверки
        assertEquals("https://postman-echo.com/get?foo1=bar1&foo2=bar2", 
                     response.jsonPath().getString("url"));
        assertNotNull(response.jsonPath().getString("headers.host"));
    }
}
