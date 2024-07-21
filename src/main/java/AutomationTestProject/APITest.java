package AutomationTestProject;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class APITest {



    @Test
    void APITestGet() throws MalformedURLException {
        URL url = new URL("https://reqres.in/api/users/2");
        Response response = given()
                .header(new Header("Content-Type", "application/json"))
                .when().log().all()
                .get(url)
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Map<String, Object> message = response.jsonPath().getMap("data");
        Assert.assertTrue(message.get("id").equals(2));
        Assert.assertTrue(message.get("email").equals("janet.weaver@reqres.in"));
    }

    @Test
    void APITestPost() throws MalformedURLException {
        URL url = new URL("https://reqres.in/api/users");
        String postMessage = "{\n" +
                "    \"name\": \"sathish\",\n" +
                "    \"job\": \"tester\"\n" +
                "}";
        Response response = given()
                .body(postMessage)
                .header(new Header("Content-Type", "application/json"))
                .when().log().all()
                .post(url)
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.jsonPath().get("name").equals("sathish"));
        Assert.assertTrue(response.jsonPath().get("job").equals("tester"));
    }

    @Test
    void APITestPut() throws MalformedURLException {
        URL url = new URL("https://reqres.in/api/users/2");
        String timestamp = Instant.now().toString();
        String postMessage = "{\"name\": \"sathish\",\"job\": \""+timestamp+"\"}";
        Response response = given()
                .body(postMessage)
                .header(new Header("Content-Type", "application/json"))
                .when().log().all()
                .put(url)
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().get("name").equals("sathish"));
        Assert.assertTrue(response.jsonPath().get("job").equals(timestamp));
    }

    @Test
    void APITestDelete() throws MalformedURLException {
        URL url = new URL("https://reqres.in/api/users/2");
        Response response = given()
                .header(new Header("Content-Type", "application/json"))
                .when().log().all()
                .delete(url)
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(), 204);
    }
}
