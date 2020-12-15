package root;
import java.util.ArrayList;
import root.dataStuct.Frag;
import root.dataStuct.Graph;

public class MainBioInfo
{
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


        // // TEST FRAG 
        // Frag frag = new Frag("ACGTAGGT".toCharArray());
        // System.out.println(frag);


        // // TEST 
        // Frag frag1 = new Frag("CAGCACTTGGATTCTCGG".toLowerCase().toCharArray());
        // Frag frag2 = new Frag("CAGCGTGG".toLowerCase().toCharArray());

        // int[][] simi = Utils.loadSimiGLo(frag1,frag2);
        // Utils.printSimi(simi);

        // int a = Utils.get_normal(simi);
        // int b = Utils.get_invert(simi);

        // Overlap overlap = new Overlap(simi, false);
        // System.out.println(overlap);

        // System.out.println("fdff "+a+" "+b);



        // TEST ALL
        ArrayList<String> frags_str = Utils.readFile("../output/collection1S.fasta");
        System.out.println("SIZE AFTER LOAD : "+frags_str.size());
        Frag[][] frags = Utils.do_all_frag(frags_str);
        Graph g = new Graph(frags);

        g.doall();
    }
}