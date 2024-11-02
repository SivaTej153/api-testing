package training;

import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @Test
    public void getCategories(){
        String endpoint = "http://localhost:80/api_testing/category/read.php";
        var response = given().
                        when().
                            get(endpoint).
                        then().
                            assertThat().
                                statusCode(200);
//        response.log().body();
//        response.log().all();
    }

    @Test
    public void getProduct(){
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response =
                given().
                        queryParam("id", 2).
                when().
                        get(endpoint).
                then();
        response.log().body();
    }

    @Test
    public void createProduct(){
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        String body = """
                {
                "name" : "Reebok Shorts",
                "description" : "Comfy Shorts",
                "price" : 15.99,
                "category_id" : 2
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
                "id" : 1000,
                "name" : "Reebok Shorts",
                "description" : "Comfy Shorts",
                "price" : 15.99,
                "category_id" : 1
                }
                """;
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void deleteProduct(){
        String endpoint = "http://localhost:80/api_testing/product/delete.php";
        String body = """
                {
                "id" : 1000
                }
                """;

        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void createProductSerialized(){
        String endpoint = "http://localhost:80/api_testing/product/create.php";
        Product product = new Product(
                "Reebok Shorts",
                "comfy shorts",
                12.99,
                2
        );
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void getAllProducts(){
        String endpoint = "http://localhost:80/api_testing/product/read.php";
/*      //Checks the body
        var response = given().
                        when().
                            get(endpoint).
                        then().
                            assertThat().
                                body("records.size()", equalTo (23)).
                                body("records.size()", greaterThan(0)).
                                body("records.id", everyItem(notNullValue())).
                                body("records.name", everyItem(notNullValue())).
                                body("records.category_id", everyItem(notNullValue())).
                                body("records.id[0]", equalTo("1008"));
*/

        //Checks the header and body
        var response = given().
                when().
                get(endpoint).
                then().
                log().headers().
                assertThat().
                statusCode(200).
                headers("Content-Type", equalTo("application/json; charset=UTF-8")).
                body("records.size()", equalTo (23)).
                body("records.size()", greaterThan(0)).
                body("records.id", everyItem(notNullValue())).
                body("records.name", everyItem(notNullValue())).
                body("records.category_id", everyItem(notNullValue())).
                body("records.id[0]", equalTo("1008"));

    }

    @Test
    public void getProductDeSerialized(){
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299,
                2,
                "Active Wear - Women"
        );

        Product actualProduct = given().queryParam("id", 2).
                when().get(endpoint).as(Product.class);

        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }

    @Test
    public void checkMultivitaminProduct(){
        String endpoint = "http://localhost:80/api_testing/product/read_one.php";
        var response = given().
                            queryParam("id", 18).
                        when().
                            get(endpoint).
                        then().
                            assertThat().
                            statusCode(200).
                            headers("Content-Type", equalTo("application/json")).
                            body("id", equalTo("18")).
                            body("name", equalTo("Multi-Vitamin (90 capsules)")).
                            body("description", equalTo("A daily dose of our Multi-Vitamins fulfills a day’s nutritional needs for over 12 vitamins and minerals.")).
                            body("category_id", equalTo("4")).
                            body("category_name", equalTo("Supplements")).
                            body("price", equalTo("10.00"));

        response.log().all();

    }
}