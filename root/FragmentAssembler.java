package root;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import root.dataStuct.Frag;
import root.dataStuct.Graph;
import root.dataStuct.HamiltonianPath;

/**
 * Classe principale qui permet de lancer le programme
 */
public class FragmentAssembler
{
    public static void main(String[] args)
    {
        String input = args[0];
        String output = null;
        String outputci = null;
        if(args[1].equals("-out"))
            output = args[2];
        if(args[3].equals("-out-ic"))
            outputci = args[4];

        ArrayList<String> frags_str = Utils.readFile(input);
        Frag[][] frags = Frag.compute_frags(frags_str);
        Graph g = new Graph(frags);

        HamiltonianPath path = g.get_hamiltonian();

        LListCons consensus = new LListCons(frags, path);

        try {
            FileWriter myWriter = new FileWriter(output);
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
            FileWriter myWriter = new FileWriter(outputci);
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