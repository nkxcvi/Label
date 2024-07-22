package etiketki;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static io.restassured.RestAssured.*;

public class Horizontal6x4 {
    public static void main(String[] args) throws FileNotFoundException {
        String gtin = null;
        String text = null;
        String ki = null;
        //
        ArrayList<String> km = new ArrayList<>();
        File file = new File("C:\\Users\\User11\\Desktop\\123\\444.txt");
        Scanner scanner = new Scanner(file);

        int ji = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] ss = line.split(" ");
            km.add(ss[0]);
            StringBuilder stringBuilder = new StringBuilder();
            for (String a : ss) {
                stringBuilder.append(a);
                stringBuilder.append(" ");
                text = stringBuilder.toString();
                //System.out.println(text);

                // Задаем текст для кодирования
                gtin = text.substring(2, 16);
                ki = text.substring(0, 31);
            }

            String model = null;
            String size = null;
            try {
                baseURI = "https://api.nc.ismet.kz/v3/";
                basePath = "product";
                Response response =
                        given().log().all().queryParam("apikey", "wj1sqdp4qbm67qn3")
                                .queryParam("gtin", gtin)
                                .when().post()
                                .then()
                                .extract().response();
                String place = response.asPrettyString();
                System.out.println(place);

                //JsonObject = JsonParser
                JsonObject jsonObject = new JsonParser().parse(place).getAsJsonObject();
                JsonArray result = jsonObject.getAsJsonArray("result");
                JsonArray good_attrs = result.get(0).getAsJsonObject().get("good_attrs").getAsJsonArray();
                for (int i = 0; i < good_attrs.size(); i++) {
                    if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13914")) {
                        model = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                    }
                    if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13886")) {
                        size = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                    }

                }
                // Создание DataMatrix
                BitMatrix bitMatrix = new DataMatrixWriter().encode(text, BarcodeFormat.DATA_MATRIX, 60, 60);

                // Создание PDF
                PDDocument document = new PDDocument();
                PDPage page = new PDPage(new PDRectangle(170.721474f, 113.814316f));
                document.addPage(page);

                // Добавление текста в PDF
                //PDType0Font font = PDType0Font.load(document, new File("DejaVuSans.ttf"));
                PDType0Font font = PDType0Font.load(document, new File("calibri.ttf"));
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                // Создание изображения DataMatrix и добавление его в PDF
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
                PDImageXObject ximage = PDImageXObject.createFromByteArray(document, out.toByteArray(), "datamatrix");
                contentStream.drawImage(ximage, 110, 55);
                //SN
                contentStream.beginText();
                contentStream.setFont(font, 4);
                contentStream.newLineAtOffset(10, 100);//положение
                contentStream.showText("SN: " + ki);//текст
                contentStream.endText();
                //GTIN
                contentStream.beginText();
                contentStream.setFont(font, 4);
                contentStream.newLineAtOffset(10, 90);//положение
                contentStream.showText("код товара (gtin): " + gtin);//текст
                contentStream.endText();
                //положение Модель/Артикул
                contentStream.beginText();
                contentStream.setFont(font, 5);
                contentStream.newLineAtOffset(10, 43);//положение
                contentStream.showText("МОДЕЛЬ/АРТИКУЛ: " + model);//текст
                contentStream.endText();
                //Размер
                contentStream.beginText();
                contentStream.setFont(font, 5);
                contentStream.newLineAtOffset(10, 53);//положение
                contentStream.showText("РАЗМЕР: " + size);//текст
                contentStream.endText();
                //Line
                contentStream.moveTo(200, 60);
                contentStream.lineTo(0, 60);
                contentStream.stroke();
                contentStream.close();
                document.save("C:\\Users\\User11\\Desktop\\n2\\" + ji + "-код.pdf");
                ji++;
                document.close();
            } catch (JsonSyntaxException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
