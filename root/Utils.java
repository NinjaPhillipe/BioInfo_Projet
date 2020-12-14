package root;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import root.dataStuct.Frag;

public class Utils 
{
    /**
     * Lire le fichier fasta passer en paramettre
     * @param path Le chemin du fichier
     * @return Une ArrayList des frqagments trouver
     */
    public static ArrayList<String> readFile(String path)
    {
        try 
        {
            File myObj = new File(path);

            Scanner myReader = new Scanner(myObj);

            String tmp = new String();
            ArrayList<String> res = new ArrayList<>();

            while (myReader.hasNextLine()) 
            {
                String data = myReader.nextLine();

                /* Si c'est un nouveau fragment */
                if(data.charAt(0) == '>')
                {
                    if(tmp.length() > 0)
                    {
                        res.add(tmp);
                        tmp = "";
                    }
                }
                else
                {
                    tmp += data;
                }
            }
            /* C'est la fin du fichier */
            res.add(tmp);

            myReader.close();
            return res;

        } catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }


    public static Frag[][] do_all_frag(ArrayList<String> frag_chars)
    {
       Frag[][] frags = new Frag[frag_chars.size()][2];

       for(int i = 0 ; i < frag_chars.size() ; i++)
       {
           frags[i] = Frag.init_f_and_fprime(frag_chars.get(i).toCharArray());
       }
       return frags;
    }


     /**
     * Transforme la chaine en son complementaire inverse
     * @param chars Chaine dont on veut le complementaire inverse
     */
    public static char[] compl_inverse(char[] chars)
    {
        char [] res = new char[chars.length];
        /* COMPLEMENTE LA CHAINE */
        for(int i = 0 ; i < chars.length;i++)
        {
            switch (chars[i]) 
            {
                case 'a':
                    res[i] = 't';
                    break;
                case 't':
                    res[i] = 'a';
                    break;
                case 'g':
                    res[i] = 'c';
                    break;
                case 'c':
                    res[i] = 'g';
                    break;
                default:
                    System.out.println("UNKNOW ");
                    break;
            }
        }
        
        /* INVERSE LA CHAINE */
        for(int i = 0 ; i < res.length/2 ; i++)
        {
            char tmp = res[i];
            res[i] = res[res.length-1-i];
            res[res.length-1-i] = tmp;
        }
        return res;
    }

    public static int max(int a, int b)
    {
        return (a > b) ? a : b;
    }
    public static int max(int a, int b,int c)
    {
        return a>b?(a>c?a:c):(b>c?b:c);
    }


}
