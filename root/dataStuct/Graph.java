package root.dataStuct;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import root.LListCons;
import root.MultiThreadAlign;
import root.Simi;
import root.dataStuct.Arc;

public class Graph 
{
    private int n_node;
    private Frag[][] node_data;
    private ArrayList<Arc> arcs; 

    public Graph(Frag[][] frags)
    {
        this.node_data = frags;
        n_node = frags.length;
        arcs = new ArrayList<>();

        // calcul tout les arcs
        computeArc();
    }

    /**
     * Calcul tout les arcs en faisant attention de ne pas mettre
     * d'arc de f vers fprime et inversement
     */
    private void computeArc()
    {
        /* SETUP CORES */
        int cores = Runtime.getRuntime().availableProcessors();
        MultiThreadAlign threads[] = new MultiThreadAlign[cores];

        int prob_size = node_data.length;

        int start = 0;
        int end = prob_size/cores;

        for(int i = 0 ; i < cores ; i++)
        {

            threads[i] = new MultiThreadAlign(i,start,end,node_data);
            start = end;
            if(i == cores-2)
                end = prob_size;
            else 
                end   = end+(prob_size/cores); 
            threads[i].start();
        }

        for(MultiThreadAlign t : threads)
        {
            t.join();
        }
        for(MultiThreadAlign t : threads)
        {
            arcs.addAll(t.getArcs());
        }
        


        


        // // pour allouer une seule fois la memoire
        // Simi simi = new Simi(700);
        // Frag f1,f2,f1p,f2p;
        // // pour chaque noeud 
        // for(int f = 0; f < node_data.length ; f++ )
        // {
        //     System.out.println(f+"/"+node_data.length);
        //     // pour chaque autre noeud
        //     for(int g = 0; g < node_data.length ; g++ )
        //     {
        //         if(f != g)
        //         {
        //             f1 = node_data[f][0];
        //             f2 = node_data[g][0];
        //             f1p = node_data[f][1];
        //             f2p = node_data[g][1];
        //             /* generer les 4 matrices */
        //             /* genere le cas f g */
        //             simi.loadSimiGLo(f1,f2); /* LE POIDS C EST VRAIMENT DU CACA MVA MANGER TES MORTS */
        //             arcs.add(new Arc(f,false,g,false,new Overlap(simi, false,f1,f2)));
        //             arcs.add(new Arc(f,false,g,false,new Overlap(simi, true,f1,f2)));

        //             /* genere le cas fp g */
        //             simi.loadSimiGLo(f1p, f2);
        //             arcs.add(new Arc(f,true,g,false,new Overlap(simi, false,f1p,f2)));
        //             arcs.add(new Arc(f,true,g,false,new Overlap(simi, true,f1p,f2)));

        //             /* genere le cas f gp */
        //             simi.loadSimiGLo(f1, f2p);
        //             arcs.add(new Arc(f,false,g,true,new Overlap(simi, false,f1,f2p)));
        //             arcs.add(new Arc(f,false,g,true,new Overlap(simi, true,f1,f2p)));

        //             /* genere le cas fp gp */
        //             simi.loadSimiGLo(f1p,f2p);
        //             arcs.add(new Arc(f,true,g,true,new Overlap(simi, false,f1p,f2p)));
        //             arcs.add(new Arc(f,true,g,true,new Overlap(simi, true,f1p,f2p)));
        //         }
        //     }
        // }

        System.out.println(arcs.size());
    }
    
    /**
     * 
     * @param make_set
     * @param a
     * @return id of set
     */
    private int findSet(ArrayList<ArrayList<Integer>> make_set ,int a)
    {
        for(int i = 0; i < make_set.size() ; i++)
        {
            if(make_set.get(i).contains(a))
            {
                return i;
            }
        }
        // ON DOIT PAS ARRIVER LA SINON C EST DE LA MERDE
        // throw exception
        System.out.println("C EST DE LA MERDE");
        return 0;
    } 

    public static int min( int a,int b)
    {
        if (a >b )
        {
            return b;
        }
        return a;
    }
    private ArrayList<Arc> get_hamiltonian()
    {
        int in[]  = new int[n_node];
        int out[] = new int[n_node];

        // SET
        ArrayList<ArrayList<Integer>> make_set = new ArrayList<>(n_node); 
        for(int i = 0 ; i < n_node ; i++ )
        {
            in[i]=0;
            out[i]=0;
            // MAKESET
            // make_set.set(i, new ArrayList<Integer>());
            make_set.add(new ArrayList<Integer>());
            make_set.get(i).add(i);
        }

        // tri arc
        Collections.sort(this.arcs);

        ArrayList<Arc> res = new ArrayList<>();
        
        // premier noeud passe entree interdite car sinon cycle
        in[this.arcs.get(0).src] = 1;

        // System.out.println("nbr arc "+ arcs.size());
        for(Arc arc: this.arcs)
        {
            /* && FINDSET(f)!=FINDSET(g) */
            if(in[arc.dest]==0 && out[arc.src]==0 && findSet(make_set, arc.src)!=findSet(make_set, arc.dest) )
            {
                // SELECT(f,g)
                res.add(arc);

                in[arc.dest] = 1;
                out[arc.src] = 1;

                // UNION(FINDSET(f),FINDSET(g))
                {
                    // trouve les id des set
                    int a = findSet(make_set, arc.src);
                    int b = findSet(make_set, arc.dest);

                    // union des set
                    make_set.get(a).addAll(make_set.get(b));
                    make_set.remove(b);
                }
            }
            if(make_set.size() == 1)
            {
                return res;
            }
        }
        // ERROR
        System.out.println("Can't get hamiltonian path");
        return null;
    }

    private ArrayList<Arc> sortHamiltonian(ArrayList<Arc> chemin)
    {
        ArrayList<Arc> sorted = new ArrayList<>(chemin.size());

        sorted.add(chemin.get(0)); // PAS BON ON NE SAIT PAS SI LE PREMIER EST LE DEBUT DU CHEMIN HAMILTONIAN
        int current = chemin.remove(0).dest;
        while(!chemin.isEmpty())
        {
            // System.out.println(""+current);
            int next = 0;
            for(Arc a:chemin)
            {
                // si on a trouver l'arc
                if(a.src == current)
                    break;
                next++;
            }

            sorted.add(chemin.get(next));


            current = chemin.remove(next).dest;
        }

        return sorted;
    }

    public ArrayList<String> doall()
    {
        System.out.println("Start Hamiltonian");

        ArrayList<Arc> chemin =  this.get_hamiltonian();

        ArrayList<Arc> res = sortHamiltonian(chemin);

        //print arc 
        for (Arc arc : res )
        {
            System.out.println(arc);
        }

        LListCons consensus = new LListCons(this.node_data, res);
        // System.out.println(consensus.resS);
        System.out.println("Res length : "+consensus.resS.length());

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

        ////// NEED REWORK

        // utiliser OVERLAP AFIN DE DETERMINER le vote de consensus


        // LListCons consensus = new LListCons(node_data.get(res.get(0).src));
        // // System.out.println("first  : "+node_data.get(res.get(0).src));
        // // System.out.println("second : "+node_data.get(res.get(0).dest));
        // System.out.println("con s "+consensus.getSize());

        // String chaine = "";

        // for (Arc arc : res )
        // {
        //     // System.out.println("\nSRC: "+arc.src+"  W: "+arc.weight+"  DST: "+arc.dest);
        //     System.out.println("\nSRC: "+node_data.get(arc.src).length()+"  W: "+arc.weight+"  DST: "+node_data.get(arc.dest).length());
        //     chaine+=consensus.add(node_data.get(arc.dest),arc.weight);
        // }

        // System.out.println("\n"+chaine.length());


        return null;
    }
}
