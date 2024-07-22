package org.example;


import java.io.*;
import java.util.ArrayList;

public class AlltxtTo1txt {
    public static void main(String[] args) throws IOException {

        ArrayList<String> name = new ArrayList<>();

        String path = "C:\\Users\\User11\\Documents\\DialogCompany\\27.12.23";
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) {
                for (File file1 : files) {
                    String fileName = file1.getName();
                    //System.out.println(fileName);
                    name.add(fileName);
                }
            }
        }
        FileWriter fileWriter = new FileWriter("C:\\Users\\User11\\Documents\\DialogCompany\\27.12.23\\Все КМ в файле.txt");
        for (int i = 0; i < name.size(); i++) {

            try (BufferedReader reader = new BufferedReader(new FileReader(path + "\\" + name.get(i)))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    fileWriter.write(line + "\n");
                }
                fileWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        fileWriter.close();
    }
}