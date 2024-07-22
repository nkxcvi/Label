package etiketki;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;
//import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.datamatrix.DataMatrixWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataMatrixReaderExample {

    public static void main(String[] args) {
        try {
            // Открываем PDF файл
            PDDocument document = PDDocument.load(new File("C:\\Users\\User11\\Desktop\\123\\11page_1.pdf"));

            // Используем PDFRenderer для рендеринга страниц в изображения
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Создаем список для хранения найденных DataMatrix кодов
            List<String> dataMatrixCodes = new ArrayList<>();

            // Обходим каждую страницу PDF и попытаемся распознать DataMatrix коды
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                PDPage pdfPage = document.getPage(page);
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

                // Используем ZXing для распознавания DataMatrix кодов
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new DataMatrixReader();
                Result result = reader.decode(bitmap);

                // Если код был успешно распознан, добавляем его в список
                if (result != null) {
                    dataMatrixCodes.add(result.getText());
                }
            }

            // Выводим найденные DataMatrix коды
            for (String code : dataMatrixCodes) {
                System.out.println("DataMatrix Code: " + code);
            }

            // Закрываем PDF документ
            document.close();
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            throw new RuntimeException(e);
        } catch (FormatException e) {
            throw new RuntimeException(e);
        }
    }
}
