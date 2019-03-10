import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.StandardOpenOption.APPEND;

/**
 * Created by Justynaa on 2019-03-10.
 */
public class SaveTextFilesContent implements FileVisitor<Path> {

    Path targetFile;

    public SaveTextFilesContent(String targetFileName) {
        this.targetFile = Paths.get(targetFileName);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(file.toString().endsWith("txt")){
            saveFileContent(file);
        }
        return CONTINUE;
    }

    private void saveFileContent(Path file) throws IOException {
        FileChannel readChannel = FileChannel.open(file);
        FileChannel writerChannel = FileChannel.open(targetFile, APPEND);

        readChannel.transferTo(0, readChannel.size(), writerChannel);
        writerChannel.write(ByteBuffer.wrap("\r\n".getBytes("Cp1250")));

        writerChannel.close();
        readChannel.close();
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return CONTINUE;
    }
}
