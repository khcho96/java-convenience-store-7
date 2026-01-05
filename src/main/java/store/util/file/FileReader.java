package store.util.file;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private final java.io.FileReader fr;

    public FileReader(String fileName) throws IOException {
        fr = new java.io.FileReader(fileName, UTF_8);
    }

    // 파일 전체를 한 번에 읽기
    public String readAll() throws IOException {
        StringBuilder contents = new StringBuilder();
        int ch;
        while ((ch = fr.read()) != -1) {
            contents.append((char) ch);
        }
        fr.close();

        return contents.toString();
    }

    // 파일 전체를 한 줄 씩 나눠서 읽기
    public List<String> readLines() throws IOException {
        List<String> contents = new ArrayList<>();
        BufferedReader br = new BufferedReader(fr);

        String line;
        while ((line = br.readLine()) != null) {
            contents.add(line);
        }
        br.close();

        return new ArrayList<>(contents);
    }
}
