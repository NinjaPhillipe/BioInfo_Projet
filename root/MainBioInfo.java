package root;
import java. util. ArrayList;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainBioInfo
{
    // public static String[] reconstructionChaine(int[][] tab,String stry,String strx)
    // {
    //     // find max 
    //     int max = Integer.MIN_VALUE;
    //     int y_cur = 0;
    //     int x_cur = 0;
    //     for (int i = 0 ; i < tab.length ; i++)
    //     {
    //         if(tab[i][tab[0].length-1] > max)
    //         {
    //             max = tab[i][tab[0].length-1];
    //             y_cur = i;
    //             x_cur = tab[0].length-1;
    //         }

    //     }
    //     for (int i = 0 ; i < tab[0].length ; i++)
    //     {
    //         if(tab[tab.length-1][i] > max)
    //         {
    //             max = tab[tab.length-1][i];
    //             y_cur = tab.length-1;
    //             x_cur = i;
    //         }

    //     }
    //     // System.out.println("max "+max+" en "+y_cur+"  "+x_cur);

    //     String resy = "";
    //     String resx = "";

    //     while (y_cur>0 && x_cur>0)
    //     {
    //         int haut = tab[y_cur-1][x_cur];
    //         int gauche  = tab[y_cur][x_cur-1];
    //         int diag =  tab[y_cur-1][x_cur-1];
    //         // int maxTmp = max(haut, max(gauche, diag));
            
    //         if (diag >= 0)
    //         {
    //             // diag
    //             x_cur-=1;
    //             y_cur-=1;
    //             resy = stry.charAt(y_cur)+resy;
    //             resx = strx.charAt(x_cur)+resx;
    //         }
    //         else if (haut > gauche)
    //         {
    //             // haut
    //             y_cur-=1;
    //             resy = stry.charAt(y_cur)+resy;
    //             resx = "_"+resx;
    //         }
    //         else 
    //         {
    //             // gauche
    //             x_cur-=1;
    //             resy = "_"+resy;
    //             resx = strx.charAt(x_cur)+resx;
    //         }
    //     }
    //     // construit
    //     // System.out.println("STR:\n"+stry+"\n"+strx);
    //     // System.out.println("ALI:\n"+resy+"\n"+resx);
    //     String[] res = {resy,resx};
    //     return res;
    // }

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

        // // TEST 4
        // ArrayList<String> frags = readFile();

        // ArrayList<String> align = doAllSimi(frags);
        // System.out.println(align.size());


        // Graph graph = new Graph();
        // for(String st : align)
        // {
        //     graph.addNode(st);
        // } 


        // graph.computeArc();

        // graph.hamiltonian(); 

        // TEST 5
        // String t1 = "AA_TCGT" ;
        // String t2 = "AAATCGT" ;    
        // System.out.println(Graph.prefix_suffixe(t1,t2)); 


        // TEST FRAG 
        Frag frag = new Frag("ACGTAGGT".toCharArray());
        System.out.println(frag);


        // TEST 
        Frag frag1 = new Frag("CAGCACTTGGATTCTCGG".toCharArray());
        Frag frag2 = new Frag("CAGCGTGG".toCharArray());

        int[][] simi = Utils.loadSimiGLo(frag1,frag2);

        int a = Utils.get_normal(simi);
        int b = Utils.get_invert(simi);

        System.out.println("fdff "+a+" "+b);



    }
}