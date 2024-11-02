package training;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SweatBandCRUD {

    @Test
    public void createProduct(){
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        String body = """
                {
                "name" : "Sweatband",
                "description" : "absorbs sweat",
                "price" : 5,
                "category_id" : 3
                }
                """;
        var response = given().body(body).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void updateProduct(){
        String endpoint = "http://localhost:80/api_testing/product/update.php";
        String body = """
                {
                "id" : 1008,
                "price" : 9.99
                }
                """;
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void getProduct(){
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        given().
                queryParam("id", 1008).
        when().
                get(endpoint).
        then().
                assertThat().
                body("name", equalTo("Sweatband")).log().body();
    }

    @Test
    public void deleteProduct(){
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = """
                {
                "name" : "Sweatband"
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }
}
