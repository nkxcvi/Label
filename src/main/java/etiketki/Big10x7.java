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
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static io.restassured.RestAssured.*;

public class Big10x7 {

    public static void main(String[] args) throws FileNotFoundException {

        String gtin = null;
        String text = null;
        String ki = null;

        ArrayList<String> FileName = new ArrayList<>();
        ArrayList<String> km = new ArrayList<>();

        String folderPath = "C:\\Users\\user1\\OneDrive - ТОО Казахстанская марка\\Рабочий стол\\Кайрат\\delta\\536"; // Укажите путь к папке
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
        int ji = 0;
        for (int y = 0; y < FileName.size(); y++) {
            File file = new File("C:\\Users\\user1\\OneDrive - ТОО Казахстанская марка\\Рабочий стол\\Кайрат\\delta\\536\\" + FileName.get(y));
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
                    gtin = text.substring(2, 16);
                    ki = text.substring(0, 31);
                }

                try {
                    baseURI = "https://api.nc.ismet.kz/v3/";
                    //baseURI = "https://api.nc.stage.ismet.kz/v3/";
                    basePath = "product";
                    Response response =
                            given().log().all().queryParam("apikey", "wj1sqdp4qbm67qn3")
                            //given().log().all().queryParam("apikey", "obxym166uwuyiyr7")
                                    .queryParam("gtin", gtin)
                                    .when().post()
                                    .then()
                                    .extract().response();
                    String place = response.asPrettyString();
                    //System.out.println(place);

                    String s1 = "";
                    String TZ = "";
                    String model = "";
                    String vid = "";
                    String size = "";
                    String color = "";
                    String top = "";
                    String inside = "";
                    String bottom = "";
                    String tnvd = " ";
                    String NO = "";
                    String country = "";
                    //JsonObject = JsonParser
                    JsonObject jsonObject = new JsonParser().parse(place).getAsJsonObject();

                    JsonArray result = jsonObject.getAsJsonArray("result");
                    JsonArray good_attrs = result.get(0).getAsJsonObject().get("good_attrs").getAsJsonArray();

                    for (int i = 0; i < good_attrs.size(); i++) {
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("2478")) {
                            s1 = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("2504")) {
                            TZ = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13914")) {
                            model = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13905")) {
                            vid = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13886")) {
                            size = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("15799")) {
                            color = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13939")) {
                            top = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13942")) {
                            inside = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13948")) {
                            bottom = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("15798")) {
                            NO = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }
                        if (good_attrs.get(i).getAsJsonObject().get("attr_id").getAsString().equals("13747")) {
                            country = good_attrs.get(i).getAsJsonObject().get("attr_value").getAsString();
                        }

                    }

                    JsonArray categories = result.get(0).getAsJsonObject().get("categories").getAsJsonArray();
                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).getAsJsonObject().get("cat_id").getAsString().equals("316774") || categories.get(i).getAsJsonObject().get("cat_id").getAsString().equals("316735") || categories.get(i).getAsJsonObject().get("cat_id").getAsString().equals("316771") || categories.get(i).getAsJsonObject().get("cat_id").getAsString().equals("316728") || categories.get(i).getAsJsonObject().get("cat_id").getAsString().equals("316721") || categories.get(i).getAsJsonObject().get("cat_id").getAsString().equals("316731") || categories.get(i).getAsJsonObject().get("cat_id").getAsString().equals("316785") || categories.get(i).getAsJsonObject().get("cat_id").getAsString().equals("316730")) {
                            tnvd = categories.get(i).getAsJsonObject().get("cat_name").getAsString();
                        }

                    }

                    // Создание DataMatrix
                    BitMatrix bitMatrix = new DataMatrixWriter().encode(text, BarcodeFormat.DATA_MATRIX, 90, 90);
                    // Создание PDF
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage(new PDRectangle(283.46f, 198.43f)); //100x70
                    //PDPage page = new PDPage(new PDRectangle(227.62863199999f, 156.4946845f));//80 x 55
                    document.addPage(page);
                    // Добавление текста в PDF
                    //PDType0Font font = PDType0Font.load(document, new File("DejaVuSans.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("calibri.ttf"));
                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    // Создание изображения DataMatrix и добавление его в PDF
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
                    PDImageXObject ximage = PDImageXObject.createFromByteArray(document, out.toByteArray(), "datamatrix");
                    contentStream.drawImage(ximage, 5, 100);
                    //SN
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(100, 150);//положение
                    contentStream.showText("SN: " + ki);//текст
                    contentStream.endText();
                    //"Страна производства"
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 85);//положение
                    contentStream.showText("СТРАНА ПРОИЗВОДСТВА: " + country);//текст
                    contentStream.endText();
//                  //ТОВАРНЫЙ ЗНАК
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(100, 140);//положение
                    contentStream.showText("ТОВАРНЫЙ ЗНАК: " + TZ);//текст
                    contentStream.endText();
                    //"Назначение обуви"
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 75);//положение
                    contentStream.showText("НАЗНАЧЕНИЕ ОБУВИ: " + NO);
                    contentStream.endText();
                    //положение Модель/Артикул
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 65);//положение
                    contentStream.showText("МОДЕЛЬ/АРТИКУЛ: " + model);
                    contentStream.endText();
                    //Вид обуви
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 55);//положение
                    contentStream.showText("ВИД ОБУВИ: " + vid);
                    contentStream.endText();
                    //Размер
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 45);//положение
                    contentStream.showText("РАЗМЕР: " + size);
                    contentStream.endText();
                    //Цвет
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 35);//положение
                    contentStream.showText("ЦВЕТ: " + color);
                    contentStream.endText();
                    //Материал верха
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 25);//положение
                    contentStream.showText("МАТЕРИАЛ ВЕРХА: " + top);
                    contentStream.endText();
                    //Материал подкладки
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 15);//положение
                    contentStream.showText("МАТЕРИАЛ ПОДКЛАДКИ: " + inside);
                    contentStream.endText();
                    //Материал низа / подошвы
                    contentStream.beginText();
                    contentStream.setFont(font, 6);
                    contentStream.newLineAtOffset(10, 5);//положение
                    contentStream.showText("МАТЕРИАЛ НИЗА/ПОДОШВЫ: " + bottom);
                    contentStream.endText();
                    //ТОО
                    contentStream.beginText();
                    contentStream.setFont(font, 4);
                    contentStream.newLineAtOffset(100, 120);//положение
                    contentStream.showText("ТОВАРИЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ДЖОМА-НС\"");//текст
                    contentStream.endText();
                    //БИН
                    contentStream.beginText();
                    contentStream.setFont(font, 4);
                    contentStream.newLineAtOffset(100, 115);//положение
                    contentStream.showText("БИН: 170540021330");//текст
                    contentStream.endText();
                    //АДРЕС
                    contentStream.beginText();
                    contentStream.setFont(font, 4);
                    contentStream.newLineAtOffset(100, 110);//положение
                    contentStream.showText("АДРЕС: г. Астана, ул. Жанибека Тархана 9, н.п. 58");//текст
                    contentStream.endText();

                    //Line
                    contentStream.moveTo(300, 95);
                    contentStream.lineTo(0, 95);
                    contentStream.stroke();
                    contentStream.close();

                    document.save("C:\\Users\\User11\\Desktop\\Test\\" + ji + "-код.pdf");

                    ji++;
                    document.close();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}





