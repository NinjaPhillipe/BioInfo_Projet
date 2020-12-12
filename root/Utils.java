package root;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import root.dataStuct.Frag;

public class Utils 
{
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
     * @param chars
     * @return
     */
    public static void compl_inverse(char[] chars)
    {
        // System.out.println(chars);

        // compl
        for(int i = 0 ; i < chars.length;i++)
        {
            switch (chars[i]) 
            {
                case 'a':
                    chars[i] = 't';
                    break;
                case 't':
                    chars[i] = 'a';
                    break;
                case 'g':
                    chars[i] = 'c';
                    break;
                case 'c':
                    chars[i] = 'g';
                    break;
                default:
                    System.out.println("UNKNOW ");
                    break;
            }
        }
        // System.out.println(chars);
        
        //inverse
        for(int i = 0 ; i < chars.length/2 ; i++)
        {
            char tmp = chars[i];
            chars[i] = chars[chars.length-1-i];
            chars[chars.length-1-i] = tmp;
        }

        // System.out.println(chars);
    }


}
