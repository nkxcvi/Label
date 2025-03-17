package PharmaDM;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DatamatrixForAmaxa {

    public static void main(String[] args) throws IOException {
        String gtin = null;
        String text = null;
        String ki = null;

        ArrayList<String> km = new ArrayList<>();
        ArrayList<String> FileName = new ArrayList<>();

        File folder = new File("C:\\Users\\user1\\Desktop\\Кайрат\\ЛС\\УОТ\\Amaxa Ltd\\СУЗ\\14022025\\KM_14032025_05060461190264_Префемин");
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
            Scanner scanner = new Scanner(new File("C:\\Users\\user1\\Desktop\\Кайрат\\ЛС\\УОТ\\Amaxa Ltd\\СУЗ\\14022025\\KM_14032025_05060461190264_Префемин\\" + FileName.get(y)));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                km.add(line);
                for (String a : km) {
                    text = a;
                    gtin = a.substring(2, 16);
                    ki = a.substring(18, 31);
                }
                try {

                    Map<EncodeHintType, Object> hints = new HashMap<>();
                    hints.put(EncodeHintType.DATA_MATRIX_SHAPE, com.google.zxing.datamatrix.encoder.SymbolShapeHint.FORCE_SQUARE);
                    hints.put(EncodeHintType.MARGIN, 0); // размер поля (padding)


                    // Создание DataMatrix
                    BitMatrix bitMatrix = new DataMatrixWriter().encode(text, BarcodeFormat.DATA_MATRIX, 40, 40, hints);
                    // Создание PDF
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage(new PDRectangle(119.5050318f, 56.907158f)); //1.5 x 1.5 см = 42.6803685f, 120f = 4 см 113.814316f, 2 cm = 56.907158f, 4.2 cm = 119.5050318f
                    document.addPage(page);

                    // Добавление текста в PDF
                    PDType0Font font = PDType0Font.load(document, new File("Arial.ttf"));

                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    // Создание изображения DataMatrix и добавление его в PDF
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
                    PDImageXObject ximage = PDImageXObject.createFromByteArray(document, out.toByteArray(), "datamatrix");
                    contentStream.drawImage(ximage, 2, 10);
                    //SN
                    drawText(contentStream, font, 7, 43, 35, "GTIN: " + gtin);
                    drawText(contentStream, font, 7, 43, 25, "SN: " + ki);

                    //Line
//                    contentStream.moveTo(300, 65);
//                    contentStream.lineTo(0, 65);
//                    contentStream.stroke();
                    contentStream.close();


                    //File directory = new File("C:\\Users\\User11\\Desktop\\Test\\" + FileName.get(y)); //в каждом файле
                    File directory = new File("C:\\Users\\user1\\Desktop\\Кайрат\\ЛС\\УОТ\\Amaxa Ltd\\СУЗ\\14022025\\DM_14032025_05060461190264_Префемин");
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
