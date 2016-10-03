package unique;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Кочмарский on 03.10.2016.
 */
public class UniqueStringTest {
    @Test
    public void getUniqueStringTest(){
        assertEquals(UniqueString.getUniqueString("abbcdabc"),"bcda");
        assertEquals(UniqueString.getUniqueString("cbacdcbc"),"bacd");
    }
}
