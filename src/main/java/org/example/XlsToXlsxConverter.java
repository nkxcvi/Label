package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XlsToXlsxConverter {
    public static void main(String[] args) throws IOException {
        ArrayList<String> FileName = new ArrayList<>();

        String folderPath = "C:\\Users\\User11\\Desktop\\Фарма\\«товар страна» (1)\\«товар страна»\\01"; // Укажите путь к папке

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
        System.out.println(FileName);

        // Путь для сохранения файла .xlsx
        String xlsxFilePath = "C:\\Users\\User11\\Desktop\\Фарма\\«товар страна» (1)\\«товар страна»\\01";

        // Попытка конвертировать .xls в .xlsx
            for (String s : FileName) {
                FileInputStream fis = new FileInputStream(folderPath +"\\"+ s);
                Workbook xlsWorkbook = new HSSFWorkbook(fis);

                // Создаем новый файл .xlsx
                Workbook xlsxWorkbook = new XSSFWorkbook();

                // Копируем листы из .xls в .xlsx
                for (int i = 0; i < xlsWorkbook.getNumberOfSheets(); i++) {
                    xlsxWorkbook.createSheet(xlsWorkbook.getSheetName(i));
                }

                // Сохраняем файл .xlsx
                FileOutputStream fos = new FileOutputStream(xlsxFilePath);
                xlsxWorkbook.write(fos);

                // Закрываем потоки
                fos.close();
                fis.close();
            }
            System.out.println("Конвертация успешно завершена.");

    }
}

