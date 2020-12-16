package root.test;

import java.util.Arrays;

import junit.framework.*;

import root.Utils;
import root.dataStuct.Frag;
import root.dataStuct.Overlap;
import root.Simi;

public class Test extends TestCase
{
    public void test_max()
    {
        assertEquals(Utils.max(1, 2, 3),3);
        assertEquals(Utils.max(1, 4, 3),4);
        assertEquals(Utils.max(7, 2, 3),7);
    }

    public void test_compl_inv()
    {
        char[] testChar     = "ACGTTTGACAC".toLowerCase().toCharArray();
        char[] targetChar   = "GTGTCAAACGT".toLowerCase().toCharArray();

        Frag f_fp[] = Frag.init_f_and_fprime(testChar);
        
        char[] testF = f_fp[0].toString().toCharArray();
        char[] testFP = f_fp[1].toString().toCharArray();

        for(int i = 0 ; i < 0 ; i++)
        {
            assertEquals(testF[i] ,testChar[i]  );
            assertEquals(testFP[i],targetChar[i]);
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

        // assertEquals(simi.get_normal(),3);

    }
    public void test_compl_simi2()
    {
        char[] f = "AAAACCGTAAAA".toLowerCase().toCharArray();
        char[] g = "CCGT".toLowerCase().toCharArray();

        Frag frag1 = new Frag(f);
        Frag frag2 = new Frag(g);

        Simi simi = new Simi(50);

        simi.loadSimiGLo(frag1, frag2);
        simi.printSimi();

        Overlap over = new Overlap(simi, false);
        // System.out.println(over);

        // assertEquals(simi.get_normal(),3);

    }
    // public void test_compl_simi3()
    // {
    //     char[] f = "CAACGT".toLowerCase().toCharArray();
    //     char[] g = "CCGTA".toLowerCase().toCharArray();

    //     Frag frag1 = new Frag(f);
    //     Frag frag2 = new Frag(g);

    //     Simi simi = new Simi(50);

    //     simi.loadSimiGLo(frag1, frag2);
    //     simi.printSimi();

    //     Overlap over = new Overlap(simi, false, frag1, frag2);
    //     System.out.println(over);

    //     // assertEquals(simi.get_normal(),3);

    // }
    // public void test_compl_simi4()
    // {
    //     char[] f = "ACGCT".toLowerCase().toCharArray();
    //     char[] g = "GTAC".toLowerCase().toCharArray();

    //     Frag frag1 = new Frag(f);
    //     Frag frag2 = new Frag(g);

    //     Simi simi = new Simi(50);

    //     simi.loadSimiGLo(frag1, frag2);
    //     simi.printSimi();

    //     Overlap over = new Overlap(simi,false);
    //     System.out.println("overlap "+over+"\n end");

    //     // assertEquals(simi.get_normal(),3);

    // }
}
