package data_matrix_with_gtin;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GenerateBarcode {
    public static void generateBarcodeFromGTIN(String gtin, PDPageContentStream contentStream, PDDocument document)
            throws WriterException, IOException {
        // Задаем координаты и размеры для штрихкода
        float barcodeX = 210;         // X-координата начала штрихкода
        float barcodeY = 160;        // Y-координата начала штрихкода
        float barcodeWidth = 60;    // Ширина штрихкода
        float barcodeHeight = 30;   // Высота штрихкода

        // Создание штрихкода из GTIN
        BitMatrix bitMatrix = new MultiFormatWriter().encode(gtin, BarcodeFormat.CODE_128, (int) barcodeWidth, (int) barcodeHeight);
        // Создание изображения штрихкода и добавление его в PDF
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
        PDImageXObject ximage = PDImageXObject.createFromByteArray(document, out.toByteArray(), "code128");
        // Добавление изображения штрихкода на страницу
        contentStream.drawImage(ximage, barcodeX, barcodeY, barcodeWidth, barcodeHeight);
    }
}
