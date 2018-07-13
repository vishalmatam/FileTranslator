package FileTranslator;

import net.jcip.annotations.ThreadSafe;

import java.io.*;

/**
 * FileManager handles reading an input file and for each line of the file calls the translator service
 * and writes the translatedWord to the output file
 */
@ThreadSafe
public class FileManager {

    private static final String OUTPUT_FILENAME_PATTERN = "%s_translated.txt";
    private final TranslatorService translatorService;

    public FileManager(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    /**
     *
     * @param inputFileName (sample: Set1.txt -> containing one englishWord per line)
     * @return outputFileName (sample: Set1_translated.txt - containing swedish word per line/englishWord if no translation was available)
     */
    public String readTranslateAndWrite(String inputFileName) {
        System.out.println("Reading file :" + inputFileName);
        String outputFileName = String.format(OUTPUT_FILENAME_PATTERN, inputFileName);
        try (FileWriter fileWriter = new FileWriter(new File(outputFileName));
             BufferedReader inputBufferedReader = new BufferedReader(new FileReader(new File(inputFileName)))
        ) {
            inputBufferedReader.lines()
                    .map(translatorService::translate)
                    .forEach(word -> {
                        try {
                            fileWriter.write(word);
                            fileWriter.write(Character.LINE_SEPARATOR);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            return outputFileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }
    }
}
