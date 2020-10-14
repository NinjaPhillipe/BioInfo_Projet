package root;

import java.util.ArrayList;

public class Graph 
{
    private int n_node;
    private ArrayList<ArrayList<Integer>> adj; 

    public Graph()
    {
        n_node = 0;
        adj = new ArrayList<>();
    }

    /**
    /* return id of node
    /*
    **/
    public int addNode()
    {
        n_node++;
        adj.add(new ArrayList<>());
        return n_node;
    }

    public int add_arc(int i, int j)
    {
        if( i < n_node && j < n_node)
        {
            adj.get(i).add(new Integer(j));
        }
        return 0;
    }



}
