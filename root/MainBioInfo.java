package root;
import java. util. ArrayList;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class MainBioInfo
{
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
    
    public static String inverse(String chain)
    {
        int size = chain.length()-1;
        String res = "";
        while (size >= 0)
        {
            char tmp = chain.charAt(size);
            if      (tmp == 'A'){res = res + 'T';}
            else if (tmp == 'T'){res = res + 'A';}
            else if (tmp == 'C'){res = res + 'G';}
            else if (tmp == 'G'){res = res + 'C';}       
            size --;     
        }
        return res;
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

        // printSimi(ok);

        return ok;
    }

    public static String[] reconstructionChaine(int[][] tab,String stry,String strx)
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
        // System.out.println("max "+max+" en "+y_cur+"  "+x_cur);

        String resy = "";
        String resx = "";

        while (y_cur>0 && x_cur>0)
        {
            int haut = tab[y_cur-1][x_cur];
            int gauche  = tab[y_cur][x_cur-1];
            int diag =  tab[y_cur-1][x_cur-1];
            // int maxTmp = max(haut, max(gauche, diag));
            
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
        // System.out.println("STR:\n"+stry+"\n"+strx);
        // System.out.println("ALI:\n"+resy+"\n"+resx);
        String[] res = {resy,resx};
        return res;
    }

    /**
     * 
     * @param strList la liste des segments aligner
     * @return
     */
    public static ArrayList<String> doAllSimi(ArrayList<String> strList)
    {
        ArrayList<String> res = new ArrayList<>();

        // si il y a pas 2 fragments pour comparer
        if(strList.size() < 2 ) return res;
        
        // pour chaque fragments
        for(int i = 0; i < strList.size()-1 ; i++ )
        {
            // pour chaque fragments suivant 
            for(int j = i+1; j <= strList.size()-1 ; j++ )
            {
                int [][] simiTab = loadSimiGLo(strList.get(i), strList.get(j));
                String[] align = reconstructionChaine(simiTab, strList.get(i), strList.get(j));

                // ajoute les deux fragments aligner
                res.add(align[0]);
                res.add(align[1]);

                // ATTENTION PAS AJOUTER LES COMPLEMENTAIRE EN MEME TEMPS

                // stri_reverse = 
                // strj_reverse =


                // TODO LES FRAGS COMPLEMENTAIRE INVERSER 
                // cas ou i est renverser 
                // cas ou j est renverser 
                // cas ou les deux sont renverser 


                // 
            }
        }

        // verifier car pour 30 frag on a 756  normalement bon
        return res;
    }

    public static void main(String[] args)
    {
        // test 1 
        // String str1 = "CAGCACTTGGATTCTCGG";
        // String str2 = "CAGCGTGG";

        // int[][] a = loadSimiGLo(str1,str2);
        // String[] align =reconstructionChaine(a,str1,str2);
        // System.out.println(align[0]+"\n"+align[1]);
        /// /// /

        // Graph graph = new Graph();
        // int i = graph.addNode();
        // i = graph.addNode();
        // System.out.println("ok "+i);


        // System.out.println(Graph.prefix_suffixe("TCACACA","TCAFTCA"));
        // Graph graph = new Graph();



        // TEST 
        // String str1 = "ATTAGACCATGCGGC";
        // String str2 = "ATCGGCATTCAGT";

        // int[][] a = loadSimiGLo(str1,str2);
        // String[] align =reconstructionChaine(a,str1,str2);
        // System.out.println(align[0]+"\n"+align[1]);

        // TEST 4
        ArrayList<String> frags = readFile();

        ArrayList<String> align = doAllSimi(frags);
        System.out.println(align.size());


        Graph graph = new Graph();
        for(String st : align)
        {
            graph.addNode(st);
        } 


        graph.computeArc();

        graph.hamiltonian(); 

        // TEST 5
        // String t1 = "AA_TCGT" ;
        // String t2 = "AAATCGT" ;    
        // System.out.println(Graph.prefix_suffixe(t1,t2)); 
    }
}