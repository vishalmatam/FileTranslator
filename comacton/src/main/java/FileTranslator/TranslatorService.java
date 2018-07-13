package FileTranslator;

/**
 * Takes English word as input and calls Google aPI service and converts to Swedish word
 */
public interface TranslatorService {
    /**
     * Translates the given english Word to Swedish
     * @param englishWord
     * @return Swedish Word
     */
    String translate(String englishWord);
}
