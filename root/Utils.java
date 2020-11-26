package root;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import root.Frag;

public class Utils {

    public static ArrayList<String> readFile()
    {
        try 
        {
            File myObj = new File("../test.txt");

            Scanner myReader = new Scanner(myObj);

            String res = new String();
            ArrayList<String> test = new ArrayList<>();
            while (myReader.hasNextLine()) 
            {
                String data = myReader.nextLine();
                if(data.charAt(0) == '>')
                {
                    if(res.length() > 0)
                    {
                        test.add(res);
                        res = "";
                    }
                }
                else
                {
                    res += data;
                }
            }
            myReader.close();
            return test;

        } catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }


    public static void do_all(ArrayList<String> frag_chars)
    {
       Frag[][] frags = new Frag[frag_chars.size()][2];

       for(int i = 0 ; i < frag_chars.size() ; i++)
       {
           frags[i] = Frag.init_f_and_fprime(frag_chars.get(i).toCharArray());
       }
    }


     /**
     * Transforme la chaine en son complementaire inverse
     * @param chars
     * @return
     */
    public static void compl_inverse(char[] chars)
    {
        System.out.println(chars);

        // compl
        for(int i = 0 ; i < chars.length;i++)
        {
            switch (chars[i]) 
            {
                case 'A':
                    chars[i] = 'T';
                    break;
                case 'T':
                    chars[i] = 'A';
                    break;
                case 'G':
                    chars[i] = 'C';
                    break;
                case 'C':
                    chars[i] = 'G';
                    break;
                default:
                    System.out.println("UNKNOW ");
                    break;
            }
        }
        System.out.println(chars);
        
        //inverse
        for(int i = 0 ; i < chars.length/2 ; i++)
        {
            char tmp = chars[i];
            chars[i] = chars[chars.length-1-i];
            chars[chars.length-1-i] = tmp;
        }

        System.out.println(chars);
    }

    public static int[][] loadSimiGLo(Frag frag1, Frag frag2)
    {

        //      f  r  a  g  2
        //  f
        //  r
        //  a
        //  g
        //  1

        int [][] ok = new int[frag1.size()+1][frag2.size()+1];

        ok[0][0] = 0;

        for (int i = 1 ; i < frag1.size() ; i ++)
        {
            ok[i][0] = 0;
        }
        for (int i = 1 ; i < frag2.size() ; i ++)
        {
            ok[0][i] = 0;
        }

        for (int i = 1 ; i < ok.length ; i++)
        {
            for (int j = 1 ; j < ok[0].length; j++)
            {/// match regarde marche pas
                ok[i][j] = max(
                                ok[i][j-1]-2,                                    
                                max(
                                    ok[i-1][j-1]+pcost(frag1.get(i-1),frag2.get(j-1)),
                                    ok[i-1][j]-2
                                    )     
                                );
            }
        }

        printSimi(ok);

        return ok;
    }
    
    public static void printSimi(int[][] ok)
    {
        for (int i = 0 ; i < ok.length ; i++)
        {
            for (int j = 0 ; j < ok[0].length ; j++)
            {
                System.out.format("%3d",ok[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static int max(int a, int b)
    {
        return (a > b) ? a : b;
    }

    public static int pcost(byte a,byte b)
    {
        return (a == b) ? 1 : -1;
    }

   

    public static int get_normal(int[][] simi)
    {
        int max = simi[0][simi[0].length-1];
        for(int i = 1 ; i < simi.length ; i++ )
        {
            if (simi[i][simi[0].length-1] > max )
                max = simi[i][simi[0].length-1];
        }
        return max;
    }

    public static int get_invert(int[][] simi)
    {
        int max = simi[simi.length-1][0];
        for(int i = 1 ; i < simi[0].length ; i++ )
        {
            if (simi[simi.length-1][i] > max )
                max = simi[simi.length-1][i];
        }
        return max;
    }

}
