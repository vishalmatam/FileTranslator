package FileTranslator;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * GoogleAPITranslatorService - This service handles calling the Google Translate URL for each englishWord from a file
 * and returns extracted the translated word from the google translate API's response
 */
public class GoogleAPITranslatorService implements TranslatorService {
    private Pattern pattern = Pattern.compile("^\\[\\[\\[\\\"(.*)\\\",\"");
    private static final String GOOGLE_TRANSLATE_URL = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=sv&dt=t&q={%s}";

    /**
     *
     * @param englishWord
     * @return translated Swedish word/englishWord(if no translation available
     */
    @Override
    public String translate(String englishWord) {
        String googleResponse = callService(englishWord);
        return extract(googleResponse);
    }

    /**
     * Calls google Translate Service and returns the response. Parsing of the response needs to be done by the caller
     *
     * @param englishWord (sample : attitude)
     * @return googleResponse (sample: [[["{attityd}","{attitude}",null,null,2]],null,"en"])
     */
    private String callService(String englishWord) {
        String googleResponse = null;
        URLConnection urlc = null;
        try {
            URL url = new URL(String.format(GOOGLE_TRANSLATE_URL, englishWord));
            urlc = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        try (Scanner scanner = new Scanner(urlc.getInputStream(), "UTF-8")) {
            scanner.useDelimiter("\\A");
            googleResponse = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleResponse;

    }

    /**
     * Extracts the translated word from googleResponse
     * @param googleResponse (sample: [[["{attityd}","{attitude}",null,null,2]],null,"en"])
     * @return (sample: attityd)
     */
    private String extract(String googleResponse) {
       // System.out.println("Google Response : " + googleResponse);

        String prefix = "[[[\"{", suffix = "}\",\"";

        if (googleResponse != null && googleResponse.contains(prefix)) {
            return googleResponse.substring(prefix.length(), googleResponse.indexOf(suffix));
        }
        throw new IllegalArgumentException("Invalid Response");
    }
}
