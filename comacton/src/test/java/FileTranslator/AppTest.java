package FileTranslator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

        List<String> strings=new LinkedList<String>(){{
            add("satish");add("Vishal");add("Bethany");
       }};
        System.out.println(String.join(",",strings));
        Collections.sort(strings);
        System.out.println(String.join(",",strings));

        assertTrue( true );
    }
}
