package MyCode;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


import static io.restassured.RestAssured.*;



public class Base64ToImage {
    static String gtin = null;
    static String text = null;

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> km = new ArrayList<>();

        ArrayList<String> FileName = new ArrayList<>();
        String folderPath = "C:\\Users\\User11\\Desktop\\Test\\12"; // Укажите путь к папке
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        // Обработка каждого файла
                        String fileName = file.getName();
                        FileName.add(fileName);
                    }
                }
            }
        }

        baseURI = "https://elk.prod.markirovka.ismet.kz/api/v3/facade/";
        basePath = "auth/login";
        Response response1 = (Response) given().log().all()
                .body("{\"login\":\"a.nurmagambetov\",\"password\":\"dNOdxC9g\"}")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .contentType("application/json")
                .when().post()
                .then()
                .statusCode(200)
                .extract().response();
        String place = response1.asPrettyString();
        JsonObject jsonObject5 = new JsonParser().parse(place).getAsJsonObject();
        String token = jsonObject5.get("token").getAsString();
        //String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2dyb3VwX2luZm8iOlt7Im5hbWUiOiJzaG9lcyIsInN0YXR1cyI6IjUiLCJ0eXBlcyI6WyJJU19NUF9PUEVSQVRPUiJdfSx7Im5hbWUiOiJwaGFybWEiLCJzdGF0dXMiOiI1IiwidHlwZXMiOlsiSVNfTVBfT1BFUkFUT1IiXX0seyJuYW1lIjoidG9iYWNjbyIsInN0YXR1cyI6IjUiLCJ0eXBlcyI6WyJJU19NUF9PUEVSQVRPUiJdfV0sInVzZXJfc3RhdHVzIjoiQUNUSVZFIiwidXNlcl9uYW1lIjoiYS5udXJtYWdhbWJldG92IiwiaW5uIjoiOTQxMjQwMDAwMTkzIiwicGlkIjoxLCJhdXRob3JpdGllcyI6WyJDUlBULUZBQ0FERS5QUk9GSUxFLUNPTlRST0xMRVIuQ09NUEFOWS5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuQUdHUkVHQVRJT04uQ1JFQVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuTU9ESUZZSU5HLldSSVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuUkVBRElORy5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5TSElQTUVOVC5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5DT01NSVNTSU9OSU5HLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRUFHR1JFR0FUSU9OLkNSRUFURSIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkRJU0FHR1JFR0FUSU9OLlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5DUkVBVElORy1EUkFGVC5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRU1BUktJTkcuQ1JFQVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuUkVBRElORy1CWS1PUEVSQVRPUi5DUkVBVEUiLCJJTk5fOTQxMjQwMDAwMTkzIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuUkVBRElORy1CWS1TVVouQ1JFQVRFIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuQ09OVFJBQ1QtQ09NTUlTU0lPTklORy5DUkVBVEUiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkctRFJBRlQuQ1JFQVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuQ1JFQVRJTkctRFJBRlQuUkVBRCIsIkNSUFQtRkFDQURFLk1BUktFRC1QUk9EVUNUUy1DT05UUk9MTEVSLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5BR0dSRUdBVElPTi5SRUFEIiwiQ1JQVC1GQUNBREUuQVBQLVVTRVItQ09OVFJPTExFUi5MSVNULVJFTU9WRUQuUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkFDQ0VQVEFOQ0UuUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLktNLUFQUExJRUQtQ0FOQ0VMLkNSRUFURSIsIkNSUFQtRkFDQURFLkNJUy1DT05UUk9MTEVSLlJFUE9SVC5ET1dOTE9BRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkNPTU1JU1NJT05JTkcuQ1JFQVRFIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuRElTQUdHUkVHQVRJT04uQ1JFQVRFIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuTE9BTi5DUkVBVEUiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkcuQ1JFQVRFIiwiUk9MRV9PUkdfSVNfTVBfT1BFUkFUT1IiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkctRFJBRlQuUkVBRCIsIkNSUFQtRkFDQURFLkFQUC1VU0VSLUNPTlRST0xMRVIuTElTVC1BQ1RJVkUuUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLlNISVBNRU5ULlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkcuQURNSU5JU1RSQVRJT04iLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5SRUFESU5HLUJZLU9QRVJBVE9SLlJFQUQiLCJST0xFX09SR1_QntC_0LXRgNCw0YLQvtGAIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuUkVDRUlQVC5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5JTVBPUlQtQ09NTUlTU0lPTklORy5DUkVBVEUiLCJFTEstUkVHSVNUUkFUSU9OLldSSVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuTU9ESUZZSU5HLkRPV05MT0FEIiwiQ1JQVC1GQUNBREUuQ0lTLUNPTlRST0xMRVIuU0VBUkNILlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRUFHR1JFR0FUSU9OLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5MT0FOLlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5SRUFESU5HLlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5SRUFESU5HLUJZLVNVWi5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuUkVNQVJLSU5HLlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkcuREVMRVRFIiwiRUxLLVJFR0lTVFJBVElPTi5DUkVBVEUiLCJFTEstUkVHSVNUUkFUSU9OLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5BQ0NFUFRBTkNFLkNSRUFURSIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLk1PRElGWUlORy5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuSU5ESS1DT01NSVNTSU9OSU5HLkNSRUFURSIsIlJPTEVfQURNSU4iLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5LTS1DQU5DRUwuQ1JFQVRFIl0sImNsaWVudF9pZCI6ImNycHQtc2VydmljZSIsImZ1bGxfbmFtZSI6ItCd0YPRgNC80LDQs9Cw0LzQsdC10YLQvtCyINCa0LDQudGA0LDRgiAiLCJzY29wZSI6WyJ0cnVzdGVkIl0sImlkIjo2MDAwNzk2NzAsImV4cCI6MTY5OTQ1NjgxNSwib3JnYW5pc2F0aW9uX3N0YXR1cyI6IlJFR0lTVEVSRUQiLCJqdGkiOiI2YTU4Mjc4Mi0zZmJhLTQwMDUtYTU0My05MDdjZGM1NjAyZTgifQ.OOIaV9pTtp4ZlQxuWrqIjBQIsk-3VNDGSMuJZyot95Y";

        int ji = 0;
        for (int y = 0; y < FileName.size(); y++) {
            File file = new File("C:\\Users\\User11\\Desktop\\Test\\12\\" + FileName.get(y));
            //System.out.println(FileName.get(y));
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] ss = line.split(" ");
                km.add(ss[0]);
                StringBuilder stringBuilder = new StringBuilder();
                for (String a : ss) {
                    stringBuilder.append(a);
                    stringBuilder.append(" ");
                    text = stringBuilder.toString();

                    // Задаем текст для кодирования
                    //gtin = text.substring(2, 16);
                }

                try {
                    baseURI = "https://goods.prod.markirovka.ismet.kz/api/v3/facade/";
                    basePath = "product/search";
                    Response response =
                            given().log().all()
                                    .queryParam("gtin", gtin)
                                    .header("Authorization", "Bearer " + token)
                                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                                    .when().get()
                                    .then()
                                    .extract().response();
                    String place2 = response.asPrettyString();
                    System.out.println(place2);

                    JsonObject jsonObject = new JsonParser().parse(place2).getAsJsonObject();
                    JsonArray results = jsonObject.get("results").getAsJsonArray();
                    String name = results.get(0).getAsJsonObject().get("name").getAsString();

                    byte[] decodedBytes = java.util.Base64.getDecoder().decode(Base64.getBase64());
                    // Создание PDF
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage(new PDRectangle(85.360737f, 56.907158f)); //30x20
                    document.addPage(page);

                    // Добавление текста в PDF
                    //PDType0Font font = PDType0Font.load(document, new File("DejaVuSans.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("calibri.ttf"));
                    PDPageContentStream contentStream = new PDPageContentStream(document, page);

                    PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, decodedBytes, "image");
                    contentStream.drawImage(pdImage, 10, 10, 20, 20);
                    //name
                    drawText(contentStream, font, 6, 10, 40,  name);

                    contentStream.stroke();
                    contentStream.close();

                    //File directory = new File("C:\\Users\\User11\\Desktop\\Test\\" + FileName.get(y)); //в каждом файле
                    File directory = new File("C:\\Users\\User11\\Desktop\\Test\\");
                    directory.mkdirs();
                    document.save(directory + "\\" + ji + "-код.pdf");
                    ji++;
                    document.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    private static void drawText(PDPageContentStream contentStream, PDFont font, int fontSize, float x, float y, String text) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }
}

