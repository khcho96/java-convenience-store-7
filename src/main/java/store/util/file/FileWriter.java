package store.util.file;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;

public class FileWriter {

    private final String fileName;
    private java.io.FileWriter fw;

    public FileWriter(String fileName) {
        this.fileName = fileName;
    }

    // 파일에 내용 한 번에 쓰기 - append 기본값(false)
    public void writeAll(String contents) throws IOException {
        fw = new java.io.FileWriter(fileName, UTF_8, false);
        fw.write(contents);
        fw.close();
    }

    // 파일에 내용 한 번에 쓰기 - append 선택
    public void writeAll(String contents, boolean append) throws IOException {
        fw = new java.io.FileWriter(fileName, UTF_8, append);
        fw.write(contents);
        fw.close();
    }

    // 파일에 내용 한 줄씩 쓰기 - append 기본값(false)
    public void writeLine(String contents) throws IOException {
        fw = new java.io.FileWriter(fileName, UTF_8, false);
        fw.write(contents);
        fw.close();
    }

    // 파일에 내용 한 줄씩 쓰기 - append 선택
    public void writeLine(String contents, boolean append) throws IOException {
        fw = new java.io.FileWriter(fileName, UTF_8, append);
        fw.write(contents);
        fw.close();
    }
}
