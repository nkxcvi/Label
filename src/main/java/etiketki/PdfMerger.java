package etiketki;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.util.ArrayList;

public class PdfMerger {
    public static void main(String[] args) {

        try {
            // Путь к папке с PDF-файлами
            String folderPath1 = "C:\\Users\\user1\\Desktop\\Кайрат\\delta\\24.05.2024\\315 PDF";

            // Путь и имя объединенного файла PDF
            String outputFileName = "C:\\Users\\user1\\Desktop\\Кайрат\\delta\\24.05.2024\\315.pdf";

            // Создание документа, в который будут объединены файлы
            Document document = new Document();

            // Создание объекта PdfCopy для объединения файлов
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFileName));

            // Открытие документа
            document.open();

            // Получение списка файлов в указанной папке
            File folder1 = new File(folderPath1);
            File[] files = folder1.listFiles();

            // Объединение файлов PDF
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".pdf")) {
                    PdfReader reader = new PdfReader(file.getAbsolutePath());

                    // Обработка каждой страницы в файле PDF
                    for (int pageNum = 1; pageNum <= reader.getNumberOfPages(); pageNum++) {
                        PdfImportedPage page = copy.getImportedPage(reader, pageNum);
                        copy.addPage(page);
                    }

                    reader.close();
                }
            }

            // Закрытие документа
            document.close();

            System.out.println("Файлы успешно объединены в " + outputFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

