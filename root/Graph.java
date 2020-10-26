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
            return this.weight - arc.weight;
        }
    }

    private int n_node;
    // private ArrayList<ArrayList<Arc>> arcs;
    private ArrayList<Arc> arcs; 

    public Graph()
    {
        n_node = 0;
        arcs = new ArrayList<>();
    }

    /**
    /* return id of node
    /*
    **/
    public int addNode()
    {
        n_node++;
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

    private ArrayList<Integer> findSet(ArrayList<ArrayList<Integer>> make_set ,int a)
    {
        for(int i = 0; i < make_set.size() ; i++)
        {
            if(make_set.get(i).contains(a))
            {
                return make_set.get(i);
            }
        }
        // ON DOIT PAS ARRIVER LA SINON C EST DE LA MERDE
        return null;
    } 

    public static int min( int a,int b)
    {
        if (a >b )
        {
            return b;
        }
        return a;
    }
    public ArrayList<Arc> travel_graph()
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
            make_set.set(i, new ArrayList<Integer>());
            make_set.get(i).add(i);
        }

        // tri arc
        // a opti 
        Collections.sort(this.arcs);
        // pour chaque noeud dans la liste d arcs
        // for(Arc arc : arcs)
        // {
        //     sortedArc.add(arc);
        // }


        ArrayList<Arc> res = new ArrayList<>();
        // 
        for(Arc arc: this.arcs)
        {
            /* && FINDSET(f)!=FINDSET(g) */
            if(in[arc.dest]==0 && out[arc.src]==0 && !findSet(make_set, arc.src).equals(findSet(make_set, arc.dest)) )
            {
                // SELECT(f,g)
                res.add(arc);

                in[arc.dest] = 1;
                out[arc.src] = 1;

                // UNION(FINDSET(f),FINDSET(g))
                {
                    ArrayList<Integer> a = findSet(make_set, arc.src);
                    ArrayList<Integer> b = findSet(make_set, arc.dest);
                    a.addAll(b);
                    make_set.remove(b);
                }
            }
            if(make_set.size() == 1)
            {
                break;
            }
        }
        return res;
    }


    public static int prefix_suffixe(String prefix, String suffix)
    {   
        int tmp_match=0;
        int max_match=0;
        for (int i = 0; i < min(prefix.length(), suffix.length()); i++)
        {
            for (int j = 0;j<= i //si j<i
                &&  // and the characters at the calculated position are equals
                (prefix.charAt(j) == suffix.charAt(suffix.length()-1-i+j )) ; j++)
            {
                tmp_match++;
                if (i == j && tmp_match > max_match ) // if the prefix and suffix fully match
                    max_match = tmp_match;
            }   
            tmp_match = 0;
        }
        return max_match;
    }
}
