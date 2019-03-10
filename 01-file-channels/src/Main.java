import java.io.IOException;

/**
 * Created by Justynaa on 2019-03-09.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String dirName = System.getProperty("user.home")+"/TPO1dir";
        String resultFileName = "TPO1res.txt";
        Futil.processDir(dirName, resultFileName);
    }
}
