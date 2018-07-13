package FileTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class BatchFileProcessor {

    private static final String BATCHED_FILE_PATTERN = "/Users/vishalmatam/actondata/Batched_%s.txt";


    public void process(List<String> outputFileNames) {
        System.out.println("Output FileNames :" + outputFileNames.stream().collect(Collectors.joining(",")));

        List<BufferedReader> bufferedReaders = new LinkedList<>();
        String batchedFileName = String.format(BATCHED_FILE_PATTERN, System.currentTimeMillis());

        System.out.println("Writing Batched File :" + batchedFileName);
        try (FileWriter fileWriter = new FileWriter(new File(batchedFileName))) {
            for (String outputFileName : outputFileNames) {
                bufferedReaders.add(new BufferedReader(new FileReader(new File(outputFileName))));
            }
            while (!bufferedReaders.isEmpty()) {
                Iterator<BufferedReader> bufferedReaderIterator = bufferedReaders.iterator();
                List<String> batchLines = new ArrayList<>();
                while (bufferedReaderIterator.hasNext()) {
                    BufferedReader bufferedReader = bufferedReaderIterator.next();
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        batchLines.add(line);
                    } else {
                        bufferedReaderIterator.remove();
                    }
                }
                Collections.sort(batchLines);
                fileWriter.write(String.join("\r\n", batchLines));
                fileWriter.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
