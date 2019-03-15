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

        String absoluteFilePath = System.getProperty("user.home") + System.getProperty("file.separator") + resultFileName;
        SaveTextFilesContent saveFiles = new SaveTextFilesContent(absoluteFilePath);
        try {
            FileChannel.open(Paths.get(absoluteFilePath), StandardOpenOption.WRITE).truncate(0).close();
            Files.walkFileTree(Paths.get(dirName), saveFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
