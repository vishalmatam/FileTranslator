package FileTranslator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FileManagerTest {

    @Mock
    TranslatorService translatorService;

    @InjectMocks
    FileManager fileManager;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        fileManager = new FileManager(translatorService);

    }

    @Test
    public void readTranslateAndWrite_Should_Return_An_OutputFileName(){
        String inputFileName = "/Users/vishalmatam/actondata/Test.txt";
        fileManager.readTranslateAndWrite(inputFileName);
        Mockito.when(translatorService.translate(Mockito.anyString())).thenReturn(anyString());
        verify(translatorService);
    }
}
