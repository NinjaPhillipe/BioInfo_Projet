package root.dataStuct;

import java.util.ArrayList;
import java.util.Collections;

import root.MultiThreadAlign;

/**
 * Classe qui represente le graphe des fragments
 */
public class Graph 
{
    private int n_node;
    private Frag[][] node_data;
    private ArrayList<Arc> arcs; 

    public Graph(Frag[][] frags)
    {
        this.node_data = frags;
        n_node = frags.length;
        arcs = new ArrayList<>(frags.length*(frags.length-1)*2);

        computeArc();/* calcul tout les arcs*/
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
        int end = (prob_size/cores)-1;

        System.out.println("FragSize : "+node_data.length);

        for(int i = 0 ; i < cores ; i++)
        {
            threads[i] = new MultiThreadAlign(i,start,end,node_data);
            start = end+1;
            if(i == cores-2)
                end = prob_size-1;
            else 
                end  = end+(prob_size/cores); 
            threads[i].start();
        }

        /* attendre la fin des threads */
        for(MultiThreadAlign t : threads)
        {
            t.join();
        }
        for(MultiThreadAlign t : threads)
        {
            arcs.addAll(t.getArcs());
        }
        System.out.println("NUMBER ARC : "+arcs.size());
    }
    
    /**
     * Methode qui retourne l'ensemble dans lequel se trouve le noeud a
     * @param make_set ArrayList contenant l'ensemble des ensembles des noeuds.
     * @param a noeud 
     * @return L'indice de l'ensemble
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
        throw new RuntimeException("Aucun ensemble ne contient le noeud "+a);
    }

    /**
     * Retourne le chemin hamiltonien du graphe
     * @return chemin hamiltonien
     */
    public HamiltonianPath get_hamiltonian()
    {
        /* Initialise une liste de taille n (le nombre de fragments)*/
        HamiltonianPath path = new HamiltonianPath(node_data.length);

        /* tri arc par ordre decroissant */
        Collections.sort(this.arcs,Collections.reverseOrder());
        int in[]  = new int[n_node];
        int out[] = new int[n_node];

        /* SET */
        ArrayList<ArrayList<Integer>> make_set = new ArrayList<>(n_node); 
        for(int i = 0 ; i < n_node ; i++ )
        {
            in[i]=0;
            out[i]=0;
            /* MAKESET */
            make_set.add(new ArrayList<Integer>());
            make_set.get(i).add(i);
        }

        for(Arc arc: this.arcs)
        {
            /* && FINDSET(f)!=FINDSET(g) */
            int set_src = findSet(make_set, arc.src);
            int set_dst = findSet(make_set, arc.dest);
            if(in[arc.dest]==0 && out[arc.src]==0 && set_src!=set_dst )
            {
                /* SELECT(f,g)*/
                path.set(arc);/* On ajoute l'arc au chemin */

                in[arc.dest] = 1;
                out[arc.src] = 1;

                /* UNION(FINDSET(f),FINDSET(g)) */
                make_set.get(set_src).addAll(make_set.get(set_dst));
                make_set.remove(set_dst);
            }
            if(make_set.size() == 1)
            {
                /* prends le premier arc du chemin hamiltonien */
                for(int i = 0 ; i < in.length ; i++)
                {
                    /* premier arc est celui dont aucun ne rentre dedans */
                    if(in[i] == 0)
                    {
                        path.set_start(i);/* fixe le noeud du initial du chemin hamiltonien */
                        return path;
                    }
                }
                throw new RuntimeException("Pas de debut au chemin hamiltonien (cycle)");
            }
        }
        throw new RuntimeException("Impossible de creer un chemin hamiltonien");
    }
}
