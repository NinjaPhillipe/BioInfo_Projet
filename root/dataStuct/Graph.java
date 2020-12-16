package root.dataStuct;

import java.util.ArrayList;
import java.util.Collections;

import root.HamiltonianPath;
import root.MultiThreadAlign;

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


        


        // // pour allouer une seule fois la memoire
        // Simi simi = new Simi(700);
        // Frag f1,g1,fp,gp;
        // // pour chaque noeud 
        // for(int f = 0; f < node_data.length ; f++ )
        // {
        //     System.out.println(f+"/"+node_data.length);
        //     // pour chaque autre noeud
        //     for(int g = f+1; g < node_data.length ; g++ )
        //     {
        //         f1 = node_data[f][0];
        //         g1 = node_data[g][0];
        //         fp = node_data[f][1];
        //         gp = node_data[g][1];
        //         /* generer les 4 matrices */
        //         /* genere le cas f g */
        //         simi.loadSimiGLo(f1,g1); /* LE POIDS C EST VRAIMENT DU CACA MVA MANGER TES MORTS */
        //         arcs.add(new Arc(f,false,g,false,simi.get_normal(),new Overlap(simi,false,f1,g1))); /*f->g*/ 
        //         arcs.add(new Arc(g,false,f,false,simi.get_invert(),new Overlap(simi,true,f1,g1)));  /*g->f*/

        //         /* genere le cas f gp */
        //         simi.loadSimiGLo(f1, gp);
        //         arcs.add(new Arc(f,false,g,true,simi.get_normal(),new Overlap(simi,false,f1,gp)));  /*f->gp*/ 
        //         arcs.add(new Arc(g,false,f,true,simi.get_invert(),new Overlap(simi,true,f1,gp)));   /*gp->f*/

        //         /* genere le cas fp g */
        //         simi.loadSimiGLo(fp, g1);
        //         arcs.add(new Arc(f,true,g,false,simi.get_normal(),new Overlap(simi,false,fp,g1)));  /*fp->g*/ 
        //         arcs.add(new Arc(g,true,f,false,simi.get_invert(),new Overlap(simi,true,fp,g1)));   /*g->fp*/

        //         /* genere le cas fp gp */
        //         simi.loadSimiGLo(fp,gp);
        //         arcs.add(new Arc(f,true,g,true,simi.get_normal(),new Overlap(simi,false,fp,gp)));   /*fp->gp*/ 
        //         arcs.add(new Arc(g,true,f,true,simi.get_invert(),new Overlap(simi,true,fp,gp)));    /*gp->fp*/
        //     }
        // }

        System.out.println("NUMBER ARC : "+arcs.size());
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
        System.out.println("AUCUN SET TROUVER");
        System.exit(1);
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

        // System.out.println("nbr arc "+ arcs.size());
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
                System.out.println("PAS DE DEBUT AU CHEMIN HAMILTONIEN POSSIBLE");
                System.exit(1);
            }
        }
        /* ERROR */
        System.out.println("Can't get hamiltonian path");
        return null;
    }
}
