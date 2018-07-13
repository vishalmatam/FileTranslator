package FileTranslator;

import com.google.common.annotations.VisibleForTesting;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


public class App {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final TranslatorService translatorService = new GoogleAPITranslatorService();
    private static final BatchFileProcessor batchFileProcessor = new BatchFileProcessor();

    public static void main(String[] fileNames) {
        System.out.println("Welcome to File Translator Service!");
        if (fileNames == null || fileNames.length == 0) {
            System.out.println("No Input files specified");
            return;
        }
        List<Future<String>> futureList = new LinkedList<>();
        for (final String fileName : fileNames) {
            FileManager fileManager = getFileManager();
            futureList.add(executorService.submit(() -> fileManager.readTranslateAndWrite(fileName)));
        }

        List<String> outputFileNames = getOutputFileNames(futureList);
        batchFileProcessor.process(outputFileNames);
        executorService.shutdownNow();
    }

    private static List<String> getOutputFileNames(List<Future<String>> futureList) {
        List<String> outputFileNames = futureList.parallelStream()
                .map(future -> {
                    String outputFileName = null;
                    try {
                        outputFileName = future.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return outputFileName;
                })
                .filter(fileName->fileName!=null)
                .collect(Collectors.toList());
        return outputFileNames;
    }

    @VisibleForTesting
    protected static FileManager getFileManager() {
        return new FileManager(translatorService);
    }
}
