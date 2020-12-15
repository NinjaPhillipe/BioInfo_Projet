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

    public static int max(int a, int b)
    {
        return (a > b) ? a : b;
    }
    public static int max(int a, int b,int c)
    {
        return a>b?(a>c?a:c):(b>c?b:c);
    }


}
