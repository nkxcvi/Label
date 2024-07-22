package MyCode;

import io.restassured.response.Response;

import javax.imageio.spi.IIORegistry;

import static io.restassured.RestAssured.*;

public class Base64 {
    public static String getBase64 () {

        baseURI = "https://barcode.tec-it.com/";
        basePath = "barcode.ashx";
        Response response =
                given().log().all()
                        .queryParam("data", Base64ToImage.text)
                        .queryParam("code", "DataMatrix")
                        .queryParam("base64", true)
                        .when().post()
                        .then()
                        .extract().response();
        String base64PNG = response.asPrettyString();
        System.out.println(base64PNG);

        return base64PNG;
    }

}
