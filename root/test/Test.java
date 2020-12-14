package root.test;

import java.util.Arrays;

import junit.framework.*;

import root.Utils;

public class Test extends TestCase
{
    public void test_compl_inv()
    {
        char[] testChar     = "ACGTTTGACAC".toCharArray();
        char[] targetChar   = "GTGTCAAACGT".toCharArray();

        Utils.compl_inverse(targetChar);

        System.out.println("test char : "+Arrays.toString(targetChar));


        assertEquals(testChar.length,targetChar.length);

        for(int i = 0 ; i < testChar.length ; i++)
        {
            assertEquals(testChar[i],targetChar[i]);
        }
        
    }
}
