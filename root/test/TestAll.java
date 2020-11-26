package root.test;

import root.Utils;



public class TestAll {

    public static boolean test_compl_inv()
    {
        char[] chars1 = "ACGTTTGACAC".toCharArray();
        char[] chars2 = "GTGTCAAACGT".toCharArray();

        Utils.compl_inverse(chars1);

        if(chars1.length != chars2.length) return false;

        for(int i = 0 ; i < chars1.length ; i++)
        {
            if (chars1[i] != chars2[i]) return false;
        }
        return true;
    }

    public static void main(String[] args)
    {
        System.out.println(test_compl_inv());
    }
}
