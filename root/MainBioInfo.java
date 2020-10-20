package root;

import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner; 

import root.Graph;

public class MainBioInfo
{
    public static void readFile()
    {
        try 
        {
            File myObj = new File("test.txt");

            Scanner myReader = new Scanner(myObj);

            String res = new String();

            while (myReader.hasNextLine()) 
            {
                String data = myReader.nextLine();
                
                if(data.charAt(0) == '>')
                {
                    // System.out.println("\n\nFRAG ");
                    // for(int i = 0 ; i < res.length() ; i++)
                    // {
                    //     System.out.println(""+res.charAt(i));
                    // }
                    if(res.length() > 0)
                    {
                        
                    }
                    else
                    {

                    }

                    res = new String();
                }
                else
                {
                    res += data;
                }

                // System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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

    public static int pcost(char a,char b)
    {
        return (a == b) ? 1 : -1;
    }

    public static int[][] loadSimiGLo(String frag1, String frag2)
    {

        //      f  r  a  g  2
        //  f
        //  r
        //  a
        //  g
        //  1

        int [][] ok = new int[frag1.length()+1][frag2.length()+1];

        ok[0][0] = 0;

        for (int i = 1 ; i < frag1.length() ; i ++)
        {
            ok[i][0] = 0;
        }
        for (int i = 1 ; i < frag2.length() ; i ++)
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
                                    ok[i-1][j-1]+pcost(frag1.charAt(i-1),frag2.charAt(j-1)),
                                    ok[i-1][j]-2
                                    )     
                                );
            }
        }

        printSimi(ok);

        return ok;
    }

    public static String reconstructionChaine(int[][] tab,String stry,String strx)
    {
        // find max 
        int max = Integer.MIN_VALUE;
        int y_cur = 0;
        int x_cur = 0;
        for (int i = 0 ; i < tab.length ; i++)
        {
            if(tab[i][tab[0].length-1] > max)
            {
                max = tab[i][tab[0].length-1];
                y_cur = i;
                x_cur = tab[0].length-1;
            }

        }
        for (int i = 0 ; i < tab[0].length ; i++)
        {
            if(tab[tab.length-1][i] > max)
            {
                max = tab[tab.length-1][i];
                y_cur = tab.length-1;
                x_cur = i;
            }

        }
        System.out.println("max "+max+" en "+y_cur+"  "+x_cur);

        String resy = "";
        String resx = "";

        while (y_cur>0 && x_cur>0)
        {
            int haut = tab[y_cur-1][x_cur];
            int gauche  = tab[y_cur][x_cur-1];
            int diag =  tab[y_cur-1][x_cur-1];
            int maxTmp = max(haut, max(gauche, diag));
            
            if (diag >= 0)
            {
                // diag
                x_cur-=1;
                y_cur-=1;
                resy = stry.charAt(y_cur)+resy;
                resx = strx.charAt(x_cur)+resx;
            }
            else if (haut > gauche)
            {
                // haut
                y_cur-=1;
                resy = stry.charAt(y_cur)+resy;
                resx = "_"+resx;
            }
            else 
            {
                // gauche
                x_cur-=1;
                resy = "_"+resy;
                resx = strx.charAt(x_cur)+resx;
            }
            

        }


        // construit


        System.out.println("STR:\n"+stry+"\n"+strx);
        System.out.println("ALI:\n"+resy+"\n"+resx);
        return resy+"\n"+resx;
    }

    public static void main(String[] args)
    {
        // String str1 = "CAGCACTTGGATTCTCGG";
        // String str2 = "CAGCGTGG";

        // int[][] a = loadSimiGLo(str1,str2);
        // reconstructionChaine(a,str1,str2);

        // Graph graph = new Graph();
        // int i = graph.addNode();
        // i = graph.addNode();
        // System.out.println("ok "+i);

        System.out.println(Graph.prefix_suffixe("TCA","TATATCA"));
    }
}