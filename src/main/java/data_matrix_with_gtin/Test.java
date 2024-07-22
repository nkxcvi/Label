package data_matrix_with_gtin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static io.restassured.RestAssured.*;

public class Test {
    static String gtin;
    static String ki;
    static String text;
    static ArrayList<String> km = new ArrayList<>();
    static ArrayList<String> FileName = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {

        String folderPath = "C:\\Users\\User11\\Desktop\\Кайрат\\Samal"; // Укажите путь к папке
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
        int num = 0;

        for (String s : FileName) {
            Scanner scanner = new Scanner(new File("C:\\Users\\User11\\Desktop\\Кайрат\\Samal\\" + s));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                km.add(line);
                for (String a : km) {
                    text = a;
                    gtin = a.substring(3, 16);
                    ki = a.substring(0, 31);
                }

                try {
                    baseURI = "https://elk.prod.markirovka.ismet.kz/api/v3/true-api/";
                    basePath = "cises/listV2";
                    Response response = given().log().all()
                            .header("Content-Type", "application/json")
                            .queryParam("cis", ki)
                            //.header("Authorization", "Bearer" + token)
                            .header("Authorization", "Bearer" + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0X2dyb3VwX2luZm8iOlt7Im5hbWUiOiJzaG9lcyIsInN0YXR1cyI6IjUiLCJ0eXBlcyI6WyJJTVBPUlRFUiIsIlRSQURFX1BBUlRJQ0lQQU5UIl19XSwidXNlcl9zdGF0dXMiOiJBQ1RJVkUiLCJ1c2VyX25hbWUiOm51bGwsImlubiI6IjAyMDg0MDAwMTQyNiIsInBpZCI6NjAwMDQ5OTQ0LCJhdXRob3JpdGllcyI6WyJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkcuV1JJVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5TSElQTUVOVC5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRUFHR1JFR0FUSU9OLkNSRUFURSIsIlJPTEVfSEVBRF9UUCIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLkNSRUFUSU5HLURSQUZULkNSRUFURSIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLlJFTUFSS0lORy5DUkVBVEUiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5SRUFESU5HLUJZLU9QRVJBVE9SLkNSRUFURSIsIlJPTEVfT1JHX0lNUE9SVEVSIiwiUk9MRV9VU0VSIiwiUk9MRV9PUkdfVFJBREVfUEFSVElDSVBBTlQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5SRUFESU5HLUJZLVNVWi5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5DT05UUkFDVC1DT01NSVNTSU9OSU5HLkNSRUFURSIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLk1PRElGWUlORy1EUkFGVC5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5BQ0NFUFRBTkNFLlJFQUQiLCJST0xFX09SR1_Qo9GH0LDRgdGC0L3QuNC6INC-0LHQvtGA0L7RgtCwIiwiQ1JQVC1GQUNBREUuQ0lTLUNPTlRST0xMRVIuUkVQT1JULkRPV05MT0FEIiwiUk9MRV9TVVoiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkctRFJBRlQuUkVBRCIsIkNSUFQtRkFDQURFLkFQUC1VU0VSLUNPTlRST0xMRVIuTElTVC1BQ1RJVkUuUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLlNISVBNRU5ULlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkcuQURNSU5JU1RSQVRJT04iLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5SRUFESU5HLUJZLU9QRVJBVE9SLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRUNFSVBULkNSRUFURSIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLklNUE9SVC1DT01NSVNTSU9OSU5HLkNSRUFURSIsIlJPTEVfT1JHX9CY0LzQv9C-0YDRgtC10YAg0YLQvtCy0LDRgNCwIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuTU9ESUZZSU5HLkRPV05MT0FEIiwiQ1JQVC1LTS1PUkRFUlMuT1JERVItRkFDQURFLUNPTlRST0xMRVIuUkVBRElORy5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuUkVNQVJLSU5HLlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkcuREVMRVRFIiwiRUxLLVJFR0lTVFJBVElPTi5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuQUNDRVBUQU5DRS5DUkVBVEUiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5NT0RJRllJTkcuUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLklOREktQ09NTUlTU0lPTklORy5DUkVBVEUiLCJST0xFX0FETUlOIiwiQ1JQVC1GQUNBREUuUFJPRklMRS1DT05UUk9MTEVSLkNPTVBBTlkuUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkFHR1JFR0FUSU9OLkNSRUFURSIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLlJFQURJTkcuQ1JFQVRFIiwiSU5OXzAyMDg0MDAwMTQyNiIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkNPTU1JU1NJT05JTkcuUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkRJU0FHR1JFR0FUSU9OLlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5DUkVBVElORy1EUkFGVC5SRUFEIiwiQ1JQVC1GQUNBREUuTUFSS0VELVBST0RVQ1RTLUNPTlRST0xMRVIuUkVBRCIsIkNSUFQtRkFDQURFLkRPQy1DT05UUk9MTEVSLkFHR1JFR0FUSU9OLlJFQUQiLCJDUlBULUZBQ0FERS5BUFAtVVNFUi1DT05UUk9MTEVSLkxJU1QtUkVNT1ZFRC5SRUFEIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuS00tQVBQTElFRC1DQU5DRUwuQ1JFQVRFIiwiQ1JQVC1GQUNBREUuRE9DLUNPTlRST0xMRVIuQ09NTUlTU0lPTklORy5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5ESVNBR0dSRUdBVElPTi5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5MT0FOLkNSRUFURSIsIkNSUFQtS00tT1JERVJTLk9SREVSLUZBQ0FERS1DT05UUk9MTEVSLk1PRElGWUlORy5DUkVBVEUiLCJFTEstUkVHSVNUUkFUSU9OLldSSVRFIiwiQ1JQVC1GQUNBREUuQ0lTLUNPTlRST0xMRVIuU0VBUkNILlJFQUQiLCJDUlBULUZBQ0FERS5QUk9GSUxFLUNPTlRST0xMRVIuQ09NUEFOWS5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5SRUFHR1JFR0FUSU9OLlJFQUQiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5MT0FOLlJFQUQiLCJDUlBULUtNLU9SREVSUy5PUkRFUi1GQUNBREUtQ09OVFJPTExFUi5SRUFESU5HLUJZLVNVWi5SRUFEIiwiRUxLLVJFR0lTVFJBVElPTi5DUkVBVEUiLCJDUlBULUZBQ0FERS5ET0MtQ09OVFJPTExFUi5LTS1DQU5DRUwuQ1JFQVRFIl0sImNsaWVudF9pZCI6ImNycHQtc2VydmljZSIsImZ1bGxfbmFtZSI6ItCn0JbQkNCdINCm0K_QnSIsInNjb3BlIjpbInRydXN0ZWQiXSwiaWQiOjYwMDE0MTYxMCwiZXhwIjoxNzA2MDQ0NzE1LCJvcmdhbmlzYXRpb25fc3RhdHVzIjoiUkVHSVNURVJFRCIsImp0aSI6IjM3MTNlMmE1LTg0NzQtNDBiYy05NTY4LThlM2NlNDM5ZTQ1ZiJ9.nJ4amPPoWoSN-IAR9nPevms61RE16v2PDrTPM1YjC6w")
                            .when().get()
                            .then().extract().response();
                    String resp = response.asPrettyString();
                    //System.out.println(resp);

                    JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("results").getAsJsonArray();
                    String name = jsonArray.get(0).getAsJsonObject().get("name").getAsString();
                    String productTypeDesc = jsonArray.get(0).getAsJsonObject().get("productTypeDesc").getAsString();
                    String brand = jsonArray.get(0).getAsJsonObject().get("brand").getAsString();
                    String model = jsonArray.get(0).getAsJsonObject().get("model").getAsString();
                    String color = jsonArray.get(0).getAsJsonObject().get("color").getAsString();
                    String productSize = jsonArray.get(0).getAsJsonObject().get("productSize").getAsString();
                    String materialUpper = jsonArray.get(0).getAsJsonObject().get("materialUpper").getAsString();
                    String materialLining = jsonArray.get(0).getAsJsonObject().get("materialLining").getAsString();
                    String materialDown = jsonArray.get(0).getAsJsonObject().get("materialDown").getAsString();
                    String producerCountry = jsonArray.get(0).getAsJsonObject().get("producerCountry").getAsString();

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
                    // Создание штрихкода из GTIN и добавление в PDF
                    GenerateBarcode.generateBarcodeFromGTIN(gtin, contentStream, document);
                    //SN
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(100, 140);//положение
                    contentStream.showText("SN: " + ki);//текст
                    contentStream.endText();
                    //gtin
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(220, 155);//положение
                    contentStream.showText(gtin);//текст
                    contentStream.endText();
                    //ТОВАРНЫЙ ЗНАК
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(100, 130);//положение
                    contentStream.showText("ТОВАРНЫЙ ЗНАК: " + brand);//текст
                    contentStream.endText();
                    //ТЕКСТ ПО ЖЕЛАНИЮ
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(100, 85);//положение
                    contentStream.showText("");//текст
                    contentStream.endText();
                    //"ПОЛНОЕ НАИМЕНОВАНИЕ"
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 75);//положение
                    contentStream.showText("ПОЛНОЕ НАИМЕНОВАНИЕ: " + name);//текст
                    contentStream.endText();
                    //МОДЕЛЬ/АРТИКУЛ
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 65);//положение
                    contentStream.showText("МОДЕЛЬ/АРТИКУЛ: " + model);
                    contentStream.endText();
                    //ВИД ОБУВИ
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 55);//положение
                    contentStream.showText("ВИД ОБУВИ: " + productTypeDesc);
                    contentStream.endText();

                    //техническая карточка
//              //ВИД ОБУВИ
//              contentStream.beginText();
//              contentStream.setFont(font, 6);
//              contentStream.newLineAtOffset(10, 55);//положение
//              contentStream.showText("ВИД ОБУВИ: " + "Домашняя обувь");
//              contentStream.endText();
//              //АДРЕС
//              contentStream.beginText();
//              contentStream.setFont(font, 6);
//              contentStream.newLineAtOffset(10, 45);//положение
//              contentStream.showText("АДРЕС: РК. г. Алматы, просепект Достык 134, офис 111");
//              contentStream.endText();
//              //ТОО
//              contentStream.beginText();
//              contentStream.setFont(font, 6);
//              contentStream.newLineAtOffset(10, 35);//положение
//              contentStream.showText("ТОВАРИЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"АРСЕНАЛ ФЭШН\"");
//              contentStream.endText();
                    //РАЗМЕР
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 45);//положение
                    contentStream.showText("РАЗМЕР: " + productSize);
                    contentStream.endText();
                    //ЦВЕТ
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 35);//положение
                    contentStream.showText("ЦВЕТ: " + color);
                    contentStream.endText();
                    //МАТЕРИАЛ ВЕРХА
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 25);//положение
                    contentStream.showText("МАТЕРИАЛ ВЕРХА: " + materialUpper);
                    contentStream.endText();
                    //МАТЕРИАЛ ПОДКЛАДКИ
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 15);//положение
                    contentStream.showText("МАТЕРИАЛ ПОДКЛАДКИ: " + materialLining);
                    contentStream.endText();
                    //МАТЕРИАЛ НИЗА/ПОДОШВЫ
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 5);//положение
                    contentStream.showText("МАТЕРИАЛ НИЗА/ПОДОШВЫ: " + materialDown);
                    contentStream.endText();
                    //АДРЕС
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(100, 110);//положение
                    contentStream.showText("СТРАНА ПРОИЗВОДСТВА: " + producerCountry);//текст
                    contentStream.endText();
                    //Line
                    contentStream.moveTo(300, 95);
                    contentStream.lineTo(0, 95);
                    contentStream.stroke();
                    contentStream.close();

                    document.save("C:\\Users\\User11\\Desktop\\Кайрат\\Samal" + "\\" + num + "-код.pdf");
                    num++;
                    document.close();

                } catch (IOException | WriterException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
