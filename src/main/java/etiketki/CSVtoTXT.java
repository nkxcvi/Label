package etiketki;


import java.io.*;

public class CSVtoTXT {
    public static void main(String[] args) {
        String csvFolderPath = "C:\\Users\\User11\\Desktop\\nike";  // Путь к папке с файлами CSV
        String txtFolderPath = "C:\\Users\\User11\\Desktop\\nike";  // Путь к папке, в которой будут сохранены файлы TXT

        File csvFolder = new File(csvFolderPath);
        File[] csvFiles = csvFolder.listFiles();

        if (csvFiles != null) {
            for (File csvFile : csvFiles) {
                if (csvFile.isFile() && csvFile.getName().toLowerCase().endsWith(".csv")) {
                    String txtFileName = csvFile.getName().replace(".csv", ".txt");
                    File txtFile = new File(txtFolderPath, txtFileName);

                    try (
                            BufferedReader br = new BufferedReader(new FileReader(csvFile));
                            BufferedWriter bw = new BufferedWriter(new FileWriter(txtFile))
                    ) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            // Дополнительная обработка строки CSV, если необходимо
                            // Например, можно разделить значения по запятым и выполнить другие преобразования

                            // Запись преобразованной строки в файл TXT
                            bw.write(line);
                            bw.newLine();
                        }
                        System.out.println("Файл " + csvFile.getName() + " успешно преобразован в " + txtFileName);
                    } catch (IOException e) {
                        System.out.println("Ошибка при чтении/записи файлов: " + e.getMessage());
                    }
                }
            }
        }

    }
}

