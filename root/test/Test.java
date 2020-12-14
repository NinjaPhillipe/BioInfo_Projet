package root.test;

import java.util.Arrays;

import junit.framework.*;

import root.Utils;
import root.dataStuct.Frag;
import root.Simi;

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

    public void test_compl_simi()
    {
        char[] f = "CAGCACTTGGATTCTCGG".toLowerCase().toCharArray();
        char[] g = "CAGCGTGG".toLowerCase().toCharArray();

        Frag frag1 = new Frag(f);
        Frag frag2 = new Frag(g);

        Simi simi = new Simi(50);

        simi.loadSimiGLo(frag1, frag2);
        simi.printSimi();

        assertEquals(simi.get_normal(),3);

    }
}
