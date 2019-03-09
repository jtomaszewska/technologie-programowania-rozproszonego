import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by Justynaa on 2019-03-09.
 */
public class Futil {
    public static void processDir(String dirName, String resultFileName) throws IOException {


        Files.walk(Paths.get(dirName))
                .filter((path) -> Files.isRegularFile(path) && path.toString().endsWith("txt"))
                .forEach(System.out::println);


        File[] files = new File(dirName).listFiles();
        //TODO sprawdzanie czy to plik tekstowy
        for (File file :
                files) {
            if (file.isFile()){
             //   saveFileText(dirName+"/"+file.getName());
            }
        }

        //wczytywać te pliki (Cp1250) i dopisywać ich zawartości do pliku o nazwie TPO1res.txt utf8

        //FileVisitor do przeglądania katalogu

        //kanały plikowe (klasa FileChannel) do odczytu/zapisu plików
        //        Path path = Paths.get(dirName);
        //        FileChannel fileChannel = FileChannel.open(path);

    }

    private static void saveFileText(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String textRead = bufferedReader.readLine();
        System.out.println("File contents: ");

        while (textRead != null) {
          System.out.println("     " + textRead);
            textRead = bufferedReader.readLine();
        }
        fileReader.close();
        bufferedReader.close();
    }
}
