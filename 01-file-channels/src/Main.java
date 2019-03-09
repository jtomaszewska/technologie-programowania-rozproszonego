/**
 * Created by Justynaa on 2019-03-09.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.home"));
        String dirName = System.getProperty("user.home")+"/TPO1dir";
        String resultFileName = "TPO1res.txt";
        Futil.processDir(dirName, resultFileName);
    }
}
