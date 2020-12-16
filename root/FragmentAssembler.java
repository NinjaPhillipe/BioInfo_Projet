package root;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import root.dataStuct.Frag;
import root.dataStuct.Graph;

public class FragmentAssembler
{
    public static void main(String[] args)
    {
        // TEST ALL
        ArrayList<String> frags_str = Utils.readFile("../output/collection1S.fasta");
        System.out.println("SIZE AFTER LOAD : "+frags_str.size());
        Frag[][] frags = Utils.do_all_frag(frags_str);
        Graph g = new Graph(frags);

        HamiltonianPath path = g.get_hamiltonian();

        LListCons consensus = new LListCons(frags, path);

        try {
            FileWriter myWriter = new FileWriter("../output/output.fasta");
            myWriter.write("> Groupe-JSP Collection 1 Longueur "+consensus.resS.length()+"\n");
            myWriter.write(consensus.resS);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        /* FAIT UN FICHIER COMPLEMENTAIRE ET INVERSER */

        String resCompInv = "";
        for(int i = consensus.resS.length()-1  ; i >=0;i-- )
        {
            switch(consensus.resS.charAt(i))
            {
                case 'a':
                    resCompInv+= "t";
                    break;
                case 'c':
                    resCompInv+= "g";
                    break;
                case 'g':
                    resCompInv+= "c";
                    break;
                case 't':
                    resCompInv+= "a";
                    break;
            }
        }
        try {
            FileWriter myWriter = new FileWriter("../output/outputci.fasta");
            myWriter.write("> Groupe-JSP Collection 1 Longueur "+resCompInv.length()+"\n");
            myWriter.write(resCompInv);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}