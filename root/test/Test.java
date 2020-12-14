package root.test;

import java.util.Arrays;

import junit.framework.*;

import root.Utils;

public class Test extends TestCase
{
    public void test_compl_inv()
    {
        char[] testChar     = "ACGTTTGACAC".toLowerCase().toCharArray();
        char[] targetChar   = "GTGTCAAACGT".toLowerCase().toCharArray();

        Utils.compl_inverse(testChar);

        System.out.println("test char : "+Arrays.toString(testChar));


        assertEquals(testChar.length,targetChar.length);

        for(int i = 0 ; i < testChar.length ; i++)
        {
            assertEquals(testChar[i],targetChar[i]);
        }
        
    }
}
