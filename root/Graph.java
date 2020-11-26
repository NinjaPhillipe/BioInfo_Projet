package root;

import java.util.ArrayList;
import java.util.Collections;

public class Graph 
{
    private class Arc implements Comparable<Arc>
    {
        public int src,dest,weight;
        public Arc(int src,int dest,int weight)
        {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override 
        public int compareTo(Arc arc)
        {
            // reversed
            return arc.weight - this.weight;
        }
    }

    private int n_node;
    private ArrayList<String> node_data;
    // private ArrayList<ArrayList<Arc>> arcs;
    private ArrayList<Arc> arcs; 

    public Graph()
    {
        n_node = 0;
        arcs = new ArrayList<>();
        node_data = new ArrayList<>();
    }

    /**
    /* return id of node
    /*
    **/
    public int addNode(String data)
    {
        n_node++;
        node_data.add(data);
        // arcs.add(new ArrayList<>());
        return n_node;
    }

    public int add_arc(int i, int j,int weight)
    {
        if( i < n_node && j < n_node)
        {
            arcs.add(new Arc(i,j,weight));
        }
        return 0;
    }

    public void computeArc()
    {
        // pour chaque noeud 
        for(int pref = 0; pref < node_data.size() ; pref++ )
        {
            // pour chaque autre noeud
            for(int suff = 0; suff < node_data.size() ; suff++ )
            {
                if( pref!= suff)
                {
                    // System.out.println(node_data.get(i)+"\n"+node_data.get(j)+"\n\n");
                    // int weight = prefix_suffixe(node_data.get(pref), node_data.get(suff));
                    int weight = 0; // DEBUG
                    this.add_arc(suff, pref, weight);
                }
            }
        }
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

        System.out.println("nbr arc "+ arcs.size());
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
        return null;
    }

    private ArrayList<Arc> sortHamiltonian(ArrayList<Arc> chemin)
    {
        ArrayList<Arc> sorted = new ArrayList<>();

        sorted.add(chemin.get(0));
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

    public ArrayList<String> hamiltonian()
    {


        ArrayList<Arc> chemin =  this.get_hamiltonian();
        System.out.println("arc in chemin "+chemin.size()); // nbr noeud-1

        //  // print arc 
        // for (Arc arc : chemin )
        // {
        //     System.out.println("SRC: "+arc.src+"  W: "+arc.weight+"  DST: "+arc.dest);
        // }

        ArrayList<Arc> res = sortHamiltonian(chemin);

        // print arc 
        // for (Arc arc : res )
        // {
        //     System.out.println("SRC: "+arc.src+"  W: "+arc.weight+"  DST: "+arc.dest);
        // }


        // System.out.println("SIZE RES "+res.size());
        // System.out.println(res);


        LListCons consensus = new LListCons(node_data.get(res.get(0).src));
        // System.out.println("first  : "+node_data.get(res.get(0).src));
        // System.out.println("second : "+node_data.get(res.get(0).dest));
        System.out.println("con s "+consensus.getSize());

        String chaine = "";

        for (Arc arc : res )
        {
            // System.out.println("\nSRC: "+arc.src+"  W: "+arc.weight+"  DST: "+arc.dest);
            System.out.println("\nSRC: "+node_data.get(arc.src).length()+"  W: "+arc.weight+"  DST: "+node_data.get(arc.dest).length());
            chaine+=consensus.add(node_data.get(arc.dest),arc.weight);
        }

        System.out.println("\n"+chaine.length());


        return null;
    }


    // public static int prefix_suffixe(String prefix, String suffix)
    // {   
    //     int tmp_match=0;
    //     int max_match=0;
    //     for (int i = 0; i < min(prefix.length(), suffix.length()); i++)
    //     {
    //         for (int j = 0;j<= i //si j<i
    //             &&  // and the characters at the calculated position are equals
    //             (prefix.charAt(j) == suffix.charAt(suffix.length()-1-i+j ) 
    //             || prefix.charAt(j) == '_' || suffix.charAt(suffix.length()-1-i+j) == '_'
    //             ) 
    //             ; j++) 
    //         {
    //             tmp_match++;
    //             if (i == j && tmp_match > max_match ) // if the prefix and suffix fully match
    //                 max_match = tmp_match;
    //         }   
    //         tmp_match = 0;
    //     }
    //     return max_match;
    // }
}
