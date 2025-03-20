import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class PdfImageOverlay {

    public static void main(String[] args) {
        try {
            String pdfPath = "C:\\Users\\user1\\Desktop\\file\\0037.pdf";
            String imagePath = "C:\\Users\\user1\\Desktop\\file\\1.png";
            String outputPath = "C:\\Users\\user1\\Desktop\\file\\0037_octa.pdf";

            // Загружаем PDF
            PDDocument document = PDDocument.load(new File(pdfPath));

            // Загружаем изображение
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            // Поворачиваем на 90 градусов по часовой стрелке
            AffineTransform tx = new AffineTransform();
            tx.translate(originalImage.getHeight(), 0);
            tx.rotate(Math.toRadians(90));

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage rotatedImage = new BufferedImage(
                    originalImage.getHeight(), originalImage.getWidth(), originalImage.getType()
            );
            op.filter(originalImage, rotatedImage);

            // Создаем изображение для PDF
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, rotatedImage);

            // Вставляем изображение на каждую страницу
            for (PDPage page : document.getPages()) {
                PDPageContentStream contentStream = new PDPageContentStream(
                        document, page, PDPageContentStream.AppendMode.APPEND, true, true
                );

                float x = 100;
                float y = 240;
                float width = 300;
                float height = 300;

                contentStream.drawImage(pdImage, x, y, width, height);
                contentStream.close();
            }

            // Сохраняем PDF
            document.save(outputPath);
            document.close();

            System.out.println("✅ PDF сохранен с повернутым изображением: " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
