package etiketki;

import com.google.gson.JsonArray;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static io.restassured.RestAssured.*;

public class Big80x55 {
    public static void main(String[] args) throws FileNotFoundException {
            String gtin = null;
            String text = null;
            String ki = null;


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
                        stringBuilder.append("");
                        text = stringBuilder.toString();

                        // Задаем текст для кодирования
                        gtin = text.substring(0, 14);
                        System.out.println(gtin);
                        //ki = text.substring(0, 31);
                        //ki = text.substring(0, 32);
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
                        //String bottom = results.get(0).getAsJsonObject().get("materialDown").getAsString();
                        //String inside = results.get(0).getAsJsonObject().get("materialLining").getAsString();
                        //String top = results.get(0).getAsJsonObject().get("materialUpper").getAsString();
                        //String color = results.get(0).getAsJsonObject().get("color").getAsString();
                        //String size = results.get(0).getAsJsonObject().get("productSize").getAsString();
                        //String vid = results.get(0).getAsJsonObject().get("productTypeDesc").getAsString();
                        //String model = results.get(0).getAsJsonObject().get("model").getAsString();
                        //String TZ = results.get(0).getAsJsonObject().get("brand").getAsString();
                        //String country = results.get(0).getAsJsonObject().get("producerCountry").getAsString();
                        //String NO = results.get(0).getAsJsonObject().get("productTypeDesc").getAsString();
                        //String company = results.get(0).getAsJsonObject().get("productTypeDesc").getAsString();
                        String name = results.get(0).getAsJsonObject().get("name").getAsString();



                        //Техническая карточка - 1
//                    String name = results.get(0).getAsJsonObject().get("name").getAsString();
//                    String gtin1 = results.get(0).getAsJsonObject().get("gtin").getAsString();
//                    String color = results.get(0).getAsJsonObject().get("color").getAsString();
//                    String tnVedCode10 = results.get(0).getAsJsonObject().get("tnVedCode10").getAsString();
//                    String productSize = results.get(0).getAsJsonObject().get("productSize").getAsString();


                        // Создание DataMatrix
                        BitMatrix bitMatrix = new DataMatrixWriter().encode(text, BarcodeFormat.DATA_MATRIX, 20, 20);
                        // Создание PDF
                        PDDocument document = new PDDocument();
                        PDPage page = new PDPage(new PDRectangle(85.360737f, 56.907158f)); //100x70
//                    float width = 227.62863199999f;
//                    float height = 156.4946845f;
//                    PDPage page = new PDPage(new PDRectangle(width, height));//80x55
                        document.addPage(page);

                        // Добавление текста в PDF
                        //PDType0Font font = PDType0Font.load(document, new File("DejaVuSans.ttf"));
                        PDType0Font font = PDType0Font.load(document, new File("calibri.ttf"));
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        // Создание изображения DataMatrix и добавление его в PDF
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
                        PDImageXObject ximage = PDImageXObject.createFromByteArray(document, out.toByteArray(), "datamatrix");
                        contentStream.drawImage(ximage, 20, 10);
                        //SN
                        drawText(contentStream, font, 6, 100, 150, "SN: " + ki);
                        //name
                        //drawText(contentStream, font, 6, 10, 40,  name);
                        //ТОВАРНЫЙ ЗНАК
//                        drawText(contentStream, font, 6, 100, 140, "ТОВАРНЫЙ ЗНАК: " + TZ);
                        // ТН ВЭД
                        // drawText(contentStream, font, 6, 100, 170, "ТН ВЭД: " + tnvd.substring(0, 10));
                        // "Страна производства"
//                        drawText(contentStream, font, 6, 10, 85, "СТРАНА ПРОИЗВОДСТВА: " + country);
//                        // "Назначение обуви"
//                        drawText(contentStream, font, 6, 10, 75, "НАЗНАЧЕНИЕ ОБУВИ: " + NO);
//                        // Модель/Артикул
//                        drawText(contentStream, font, 6, 10, 65, "МОДЕЛЬ/АРТИКУЛ: " + model);
//                        // Вид обуви
//                        drawText(contentStream, font, 6, 10, 55, "ВИД ОБУВИ: " + vid);
//                        // Размер
//                        drawText(contentStream, font, 6, 10, 45, "РАЗМЕР: " + 45);
//                        // Цвет
//                        drawText(contentStream, font, 6, 10, 35, "ЦВЕТ: " + color);
//                        // Материал верха
//                        drawText(contentStream, font, 6, 10, 25, "МАТЕРИАЛ ВЕРХА: " + top);
//                        // Материал подкладки
//                        drawText(contentStream, font, 6, 10, 15, "МАТЕРИАЛ ПОДКЛАДКИ: " + inside);
//                        // Материал низа / подошвы
//                        drawText(contentStream, font, 6, 10, 5, "МАТЕРИАЛ НИЗА/ПОДОШВЫ: " + bottom);
                        //ТОО
//                        drawText(contentStream, font, 4, 100, 120, "ТОВАРИЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ДЖОМА-НС\"");
//                        //drawText(contentStream, font, 4, 100, 120, "ЮРИДИЧЕСКИЙ АДРЕС");
//                        //БИН
//                        drawText(contentStream, font, 4, 100, 115, "БИН: 200140029815");
//                        // Адрес
//                        String ads = "г. Астана, ул. Жанибека Тархана 9, н.п. 58";
//                        drawTextWithLineBreak(contentStream, font, 4, 100, 110, 100, ads);


                        //Line
                        //contentStream.moveTo(300, 95);
                        //contentStream.lineTo(0, 95);
                        contentStream.stroke();
                        contentStream.close();


                        //File directory = new File("C:\\Users\\User11\\Desktop\\Test\\" + FileName.get(y)); //в каждом файле
                        File directory = new File("C:\\Users\\User11\\Desktop\\Test\\");
                        directory.mkdirs();
                        document.save(directory + "\\" + ji + "-код.pdf");
                        ji++;
                        document.close();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
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

//    private static void drawTextWithLineBreak(PDPageContentStream contentStream, PDFont font, int fontSize, float x, float y, float maxWidth, String text) throws IOException {
//        String[] words = text.split(" ");
//        float lineHeight = fontSize + 2f;
//        float currentX = x;
//        float currentY = y;
//        contentStream.beginText();
//        contentStream.setFont(font, fontSize);
//        contentStream.newLineAtOffset(currentX, currentY);
//
//        for (String word : words) {
//            float wordWidth = font.getStringWidth(word) / 1000 * fontSize;
//            if (currentX + wordWidth > x + maxWidth) {
//                // Переносим на новую строку
//                currentX = x;
//                currentY -= lineHeight;
//                contentStream.newLineAtOffset(0, -lineHeight);
//            }
//            contentStream.showText(word + " ");
//            currentX += wordWidth + font.getStringWidth(" ") / 1000 * fontSize;
//        }
//        contentStream.endText();
//    }
}


