package root;

import java.util.ArrayList;

public class Graph 
{

    private class Arc //implements Comparable<Arc>
    {
        public int src,dest,weight;
        public Arc(int src,int dest,int weight)
        {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        // @Override
        // public boolean equals(Object arc)
        // {
            
        //     return this.weight == arc.weight;
        // }

        // @Override 
        // public int compareTo(Arc arc)
        // {
            
        // }
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
        ArrayList<Arc> sortedArc = new ArrayList<>(n_node);
        // pour chaque noeud dans la liste d arcs
        for(Arc arc : arcs)
        {
            sortedArc.add(arc);
        }


        ArrayList<Arc> res = new ArrayList<>();
        // 
        for(Arc arc: sortedArc)
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
    {   int longueur = suffix.length();
        int max_curr = 0;
        int match_curr = 0;
        while (longueur > 0)
        {
            for (int i = 0 ; i<= suffix.length() - longueur; i++)
            {   
                System.out.println(longueur);
                System.out.println(prefix.charAt(i));
                System.out.println(suffix.charAt(suffix.length() -i -1));
                if (prefix.charAt(i)==suffix.charAt(suffix.length() -i -1))
                {

                    match_curr++;
                    if (match_curr > max_curr)
                    {
                        max_curr = match_curr;
                    }
                }
                else
                {
                    if (match_curr > max_curr)
                    {
                        max_curr = match_curr;
                    }
                    match_curr = 0;
                }
            }
            longueur --;
        }
        return max_curr;
    }

}
