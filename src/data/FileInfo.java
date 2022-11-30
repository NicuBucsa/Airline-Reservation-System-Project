package data;
import java.io.*;
import java.util.Arrays;


public class FileInfo {
    public static BufferedReader createReader() {
        File input = new File("resources/input.txt");
        try {
            return new BufferedReader(new FileReader(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    public static BufferedWriter createWriter() {
        File output = new File("resources/output.txt");
        try {
            return new BufferedWriter(new FileWriter(output));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void createResources(File resources) {
        resources.mkdir();
        File app = new File(resources, "application.properties");
        try {
            app.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteResources(File resources) {
        boolean delete = resources.delete();
        if (resources.listFiles() != null) {
            File[] files = resources.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    File[] fileFromSubFolder = file.listFiles();
                    for (File file1 : fileFromSubFolder) {
                        file1.delete();
                    }
                    file.delete();
                }
            }
        }
        resources.delete();
    }

    public static void showResources(File resources) {
        if (resources.listFiles() != null) {
            File[] files = resources.listFiles();
            Arrays.stream(files).forEach(file -> {
                System.out.println(file);
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    Arrays.stream(listFiles).forEach(f -> System.out.println(f));
                }
            });
        }
    }

    public static void createInput(File input) {
        if (!input.exists()) {
            try {
                input.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createOutput(File output) {
        if (!output.exists()) {
            try {
                output.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
