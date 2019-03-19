import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Justynaa on 2019-03-09.
 */
public class Futil{
    public static void processDir(String dirName, String resultFileName) {

        SaveTextFilesContent saveFiles = new SaveTextFilesContent(resultFileName);
        try {
            Files.walkFileTree(Paths.get(dirName), saveFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
