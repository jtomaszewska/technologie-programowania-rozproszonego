import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Justynaa on 2019-03-09.
 */
public class Futil{
    public static void processDir(String dirName, String resultFileName) throws IOException {

        String absoluteFilePath = System.getProperty("user.home")+System.getProperty("file.separator")+resultFileName;
        Files.deleteIfExists(Paths.get(absoluteFilePath));
        File file = new File(absoluteFilePath);
        file.createNewFile();

        SaveTextFilesContent saveFiles = new SaveTextFilesContent(absoluteFilePath);
        Files.walkFileTree(Paths.get(dirName), saveFiles);
    }
}
