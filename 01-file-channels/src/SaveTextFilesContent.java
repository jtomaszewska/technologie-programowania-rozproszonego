import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.READ;

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

        CharBuffer charBuffer;
        try (FileChannel readChannel = FileChannel.open(file, READ);
             FileChannel writerChannel = FileChannel.open(targetFile, APPEND)) {

            MappedByteBuffer mappedByteBufferRead = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, readChannel.size());

            if (mappedByteBufferRead != null) {
                charBuffer = Charset.forName("Cp1250").decode(mappedByteBufferRead);
                writerChannel.write(Charset.forName("UTF-8").encode(charBuffer));
            }
            readChannel.close();
            writerChannel.close();
        }
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
