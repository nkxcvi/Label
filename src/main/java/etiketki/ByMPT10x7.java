package etiketki;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
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

public class ByMPT10x7 {

    public static void main(String[] args) throws IOException {
        String gtin = null;
        String text = null;
        String ki = null;

        ArrayList<String> km = new ArrayList<>();
        ArrayList<String> FileName = new ArrayList<>();

        File folder = new File("C:\\Users\\user1\\Desktop\\Кайрат\\delta\\24.05.2024\\315 KM");
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
                .body("{\"login\":\"a.berdeshov\",\"password\":\"64@J6WGvNvyp\"}")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .contentType("application/json")
                .when().post()
                .then()
                .statusCode(200)
                .extract().response();
        String place = response1.asPrettyString();
        JsonObject jsonObject5 = new JsonParser().parse(place).getAsJsonObject();
        String token = jsonObject5.get("token").getAsString();
        //String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2dyb3VwX2luZm8iOlt7Im5hbWUiOiJwaGFybWEiLCJzdGF0dXMiOiI1IiwidHlwZXMiOlsiUFJPRFVDRVIiLCJJTVBPUlRFUiIsIlRSQURFX1BBUlRJQ0lQQU5UIl19LHsibmFtZSI6InRvYmFjY28iLCJzdGF0dXMiOiI1IiwidHlwZXMiOlsiUFJPRFVDRVIiLCJSRVRBSUwiLCJJTVBPUlRFUiIsIlRSQURFX1BBUlRJQ0lQQU5UIiwiV0hPTEVTQUxFUiJdfSx7Im5hbWUiOiJzaG9lcyIsInN0YXR1cyI6IjUiLCJ0eXBlcyI6WyJSRVRBSUwiLCJQUk9EVUNFUiIsIklNUE9SVEVSIiwiVFJBREVfUEFSVElDSVBBTlQiLCJXSE9MRVNBTEVSIl19XSwidXNlcl9zdGF0dXMiOiJBQ1RJVkUiLCJ1c2VyX25hbWUiOm51bGwsImlubiI6IjE4MTI0MDAxMTUzNSIsInBpZCI6NjAwMDA4MzM2LCJhdXRob3JpdGllcyI6WyJDUlBULUZBQ0FERS5QUk9GSUxFLUNPTlRST0xMRVIuQ09NUEFOWS5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuQUdHUkVHQVRJT04uQ1JFQVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuTU9ESUZZSU5HLldSSVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuUkVBRElORy5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5TSElQTUVOVC5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5DT01NSVNTSU9OSU5HLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRUFHR1JFR0FUSU9OLkNSRUFURSIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkRJU0FHR1JFR0FUSU9OLlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5DUkVBVElORy1EUkFGVC5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRU1BUktJTkcuQ1JFQVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuUkVBRElORy1CWS1PUEVSQVRPUi5DUkVBVEUiLCJST0xFX09SR19JTVBPUlRFUiIsIlJPTEVfT1JHX1RSQURFX1BBUlRJQ0lQQU5UIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuUkVBRElORy1CWS1TVVouQ1JFQVRFIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuQ09OVFJBQ1QtQ09NTUlTU0lPTklORy5DUkVBVEUiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkctRFJBRlQuQ1JFQVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuQ1JFQVRJTkctRFJBRlQuUkVBRCIsIkNSUFQtRkFDQURFLk1BUktFRC1QUk9EVUNUUy1DT05UUk9MTEVSLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5BR0dSRUdBVElPTi5SRUFEIiwiUk9MRV9PUkdf0KDQvtC30L3QuNGH0L3QsNGPINGC0L7RgNCz0L7QstC70Y8iLCJDUlBULUZBQ0FERS5BUFAtVVNFUi1DT05UUk9MTEVSLkxJU1QtUkVNT1ZFRC5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuQUNDRVBUQU5DRS5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuS00tQVBQTElFRC1DQU5DRUwuQ1JFQVRFIiwiUk9MRV9PUkdf0KPRh9Cw0YHRgtC90LjQuiDQvtCx0L7RgNC-0YLQsCIsIlJPTEVfT1JHX1dIT0xFU0FMRVIiLCJDUlBULUZBQ0FERS5DSVMtQ09OVFJPTExFUi5SRVBPUlQuRE9XTkxPQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5DT01NSVNTSU9OSU5HLkNSRUFURSIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkRJU0FHR1JFR0FUSU9OLkNSRUFURSIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkxPQU4uQ1JFQVRFIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuTU9ESUZZSU5HLkNSRUFURSIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLk1PRElGWUlORy1EUkFGVC5SRUFEIiwiQ1JQVC1GQUNBREUuQVBQLVVTRVItQ09OVFJPTExFUi5MSVNULUFDVElWRS5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuU0hJUE1FTlQuUkVBRCIsIlJPTEVfT1JHX9Cf0YDQvtC40LfQstC-0LTQuNGC0LXQu9GMIiwiUk9MRV9PUkdfUFJPRFVDRVIiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkcuQURNSU5JU1RSQVRJT04iLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5SRUFESU5HLUJZLU9QRVJBVE9SLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRUNFSVBULkNSRUFURSIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLklNUE9SVC1DT01NSVNTSU9OSU5HLkNSRUFURSIsIlJPTEVfT1JHX9CY0LzQv9C-0YDRgtC10YAg0YLQvtCy0LDRgNCwIiwiRUxLLVJFR0lTVFJBVElPTi5XUklURSIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLk1PRElGWUlORy5ET1dOTE9BRCIsIkNSUFQtRkFDQURFLkNJUy1DT05UUk9MTEVSLlNFQVJDSC5SRUFEIiwiUk9MRV9PUkdf0J7Qv9GC0L7QstCw0Y8g0YLQvtGA0LPQvtCy0LvRjyIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLlJFQUdHUkVHQVRJT04uUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkxPQU4uUkVBRCIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLlJFQURJTkcuUkVBRCIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLlJFQURJTkctQlktU1VaLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRU1BUktJTkcuUkVBRCIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLk1PRElGWUlORy5ERUxFVEUiLCJFTEstUkVHSVNUUkFUSU9OLkNSRUFURSIsIkVMSy1SRUdJU1RSQVRJT04uUkVBRCIsIklOTl8xODEyNDAwMTE1MzUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5BQ0NFUFRBTkNFLkNSRUFURSIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLk1PRElGWUlORy5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuSU5ESS1DT01NSVNTSU9OSU5HLkNSRUFURSIsIlJPTEVfQURNSU4iLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5LTS1DQU5DRUwuQ1JFQVRFIiwiUk9MRV9PUkdfUkVUQUlMIl0sImNsaWVudF9pZCI6ImNycHQtc2VydmljZSIsImZ1bGxfbmFtZSI6ItCh0KPQm9CV0JnQnNCV0J3QntCSINCd0KPQoNCg0JDQpdCY0Jwg0K3QoNCY0JrQntCS0JjQpyIsInNjb3BlIjpbInRydXN0ZWQiXSwiaWQiOjYwMDExNzAwMiwiZXhwIjoxNzA0OTgyMTQ5LCJvcmdhbmlzYXRpb25fc3RhdHVzIjoiUkVHSVNURVJFRCIsImp0aSI6IjFmZmM5NDAxLTRmNmEtNDlkMy04YTY2LWJlYzJkMGFhZmNhZiJ9.FjcREjgDmd3LaNHAh9fRnz-mducggxR3aTpJJ340zpU";

        int ji = 0;
        for (int y = 0; y < FileName.size(); y++) {
            Scanner scanner = new Scanner(new File("C:\\Users\\user1\\Desktop\\Кайрат\\delta\\24.05.2024\\315 KM\\" + FileName.get(y)));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                km.add(line);
                for (String a : km) {
                    text = a;
                    gtin = a.substring(2, 16);
                    ki = a.substring(0, 31);
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
                    String bottom = results.get(0).getAsJsonObject().get("materialDown").getAsString();
                    String inside = results.get(0).getAsJsonObject().get("materialLining").getAsString();
                    String top = results.get(0).getAsJsonObject().get("materialUpper").getAsString();
                    String color = results.get(0).getAsJsonObject().get("color").getAsString();
                    String size = results.get(0).getAsJsonObject().get("productSize").getAsString();
                    String vid = results.get(0).getAsJsonObject().get("productTypeDesc").getAsString();
//                  String model = results.get(0).getAsJsonObject().get("model").getAsString();
                    JsonElement model1 = results.get(0).getAsJsonObject().get("model");
                    String model = model1 != null ? model1.getAsString() : "---";
                    String TZ = results.get(0).getAsJsonObject().get("brand").getAsString();
                    String country = results.get(0).getAsJsonObject().get("producerCountry").getAsString();
                    String NO = results.get(0).getAsJsonObject().get("productTypeDesc").getAsString();
                    String company = results.get(0).getAsJsonObject().get("productTypeDesc").getAsString();


                    //Техническая карточка - 1
//                    String name = results.get(0).getAsJsonObject().get("name").getAsString();
//                    String gtin1 = results.get(0).getAsJsonObject().get("gtin").getAsString();
//                    String color = results.get(0).getAsJsonObject().get("color").getAsString();
//                    String tnVedCode10 = results.get(0).getAsJsonObject().get("tnVedCode10").getAsString();
//                    String productSize = results.get(0).getAsJsonObject().get("productSize").getAsString();


                    // Создание DataMatrix
                    BitMatrix bitMatrix = new DataMatrixWriter().encode(text, BarcodeFormat.DATA_MATRIX, 90, 90);
                    // Создание PDF
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage(new PDRectangle(283.46f, 198.43f)); //100x70
                    document.addPage(page);

                    // Добавление текста в PDF
                    PDType0Font font = PDType0Font.load(document, new File("calibri.ttf"));
                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    // Создание изображения DataMatrix и добавление его в PDF
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
                    PDImageXObject ximage = PDImageXObject.createFromByteArray(document, out.toByteArray(), "datamatrix");
                    contentStream.drawImage(ximage, 5, 100);
                    //SN
                    drawText(contentStream, font, 6, 100, 150, "SN: " + ki);
                    //ТОВАРНЫЙ ЗНАК
                    drawText(contentStream, font, 6, 100, 140, "ТОВАРНЫЙ ЗНАК: " + TZ);
                    // ТН ВЭД
                    // drawText(contentStream, font, 6, 100, 170, "ТН ВЭД: " + tnvd.substring(0, 10));
                    // "Страна производства"
                    drawText(contentStream, font, 6, 10, 85, "СТРАНА ПРОИЗВОДСТВА: " + country);
                    // "Назначение обуви"
                    //drawText(contentStream, font, 6, 10, 75, "НАЗНАЧЕНИЕ ОБУВИ: " + NO);
                    // Модель/Артикул
                    drawText(contentStream, font, 6, 10, 65, "МОДЕЛЬ/АРТИКУЛ: " + model);
                    // Вид обуви
                    drawText(contentStream, font, 6, 10, 55, "ВИД ОБУВИ: " + vid);
                    // Размер
                    drawText(contentStream, font, 6, 10, 45, "РАЗМЕР: " + size);
                    // Цвет
                    drawText(contentStream, font, 6, 10, 35, "ЦВЕТ: " + color);
                    // Материал верха
                    drawText(contentStream, font, 6, 10, 25, "МАТЕРИАЛ ВЕРХА: " + top);
                    // Материал подкладки
                    drawText(contentStream, font, 6, 10, 15, "МАТЕРИАЛ ПОДКЛАДКИ: " + inside);
                    // Материал низа / подошвы
                    drawText(contentStream, font, 6, 10, 5, "МАТЕРИАЛ НИЗА/ПОДОШВЫ: " + bottom);
                    //ТОО
                    //drawText(contentStream, font, 4, 100, 120, "ТОВАРИЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ДЖОМА-НС\"");
                    //drawText(contentStream, font, 4, 100, 120, "ЮРИДИЧЕСКИЙ АДРЕС");
                    //БИН
                    //drawText(contentStream, font, 4, 100, 115, "БИН: 200140029815");
                    // Адрес
                    //String ads = "г. Астана, ул. Жанибека Тархана 9, н.п. 58";
                    //drawTextWithLineBreak(contentStream, font, 4, 100, 110, 100, ads);


                    //Техническая карточка - 2
//                    drawText(contentStream, font, 4, 60, 60, "SN: " + ki);
//                    drawText(contentStream, font, 4, 60, 55, "КОД ТОВАРА: " + gtin1);
//                    drawText(contentStream, font, 4, 60, 50, "НАИМЕНОВАНИЕ: " + name);
//                    drawText(contentStream, font, 4, 60, 45, "ЦВЕТ: " + color);
//                    drawText(contentStream, font, 4, 60, 40, "ТН ВЭД: " + tnVedCode10);
//                    drawText(contentStream, font, 4, 60, 35, "РАЗМЕР: " + productSize);


                    //Line
                    contentStream.moveTo(300, 95);
                    contentStream.lineTo(0, 95);
                    contentStream.stroke();
                    contentStream.close();


                    //File directory = new File("C:\\Users\\User11\\Desktop\\Test\\" + FileName.get(y)); //в каждом файле
                    File directory = new File("C:\\Users\\user1\\Desktop\\Кайрат\\delta\\24.05.2024\\315 PDF\\");
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

    private static void drawTextWithLineBreak(PDPageContentStream contentStream, PDFont font, int fontSize, float x, float y, float maxWidth, String text) throws IOException {
        String[] words = text.split(" ");
        float lineHeight = fontSize + 2f;
        float currentX = x;
        float currentY = y;
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(currentX, currentY);

        for (String word : words) {
            float wordWidth = font.getStringWidth(word) / 1000 * fontSize;
            if (currentX + wordWidth > x + maxWidth) {
                // Переносим на новую строку
                currentX = x;
                currentY -= lineHeight;
                contentStream.newLineAtOffset(0, -lineHeight);
            }
            contentStream.showText(word + " ");
            currentX += wordWidth + font.getStringWidth(" ") / 1000 * fontSize;
        }
        contentStream.endText();

    }
}
