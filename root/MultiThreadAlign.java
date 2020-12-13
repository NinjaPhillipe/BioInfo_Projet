package root;

import java.util.ArrayList;
import root.dataStuct.Frag;
import root.dataStuct.Overlap;
import root.dataStuct.Arc;

public class MultiThreadAlign implements Runnable
{
    private int id,start,end;
    private Thread thread;
    private boolean finish = false;
    private ArrayList<Arc> arcs; 
    private Frag[][] node_data;

    public MultiThreadAlign(int id,int start,int end,Frag[][] node_data)
    {
        this.start = start;
        this.end = end;
        this.id = id;
        this.arcs = new ArrayList<>();
        this.node_data = node_data;
    }

    public void start()
    {
        if(thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop()
    {
        thread.stop();
    }
    public void join()
    {
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {}
    }

    public void run()
    {

        // pour allouer une seule fois la memoire
        Simi simi = new Simi(700);
        Frag f1,f2,f1p,f2p;
        // pour chaque noeud 
        for(int f = start; f < end ; f++ )
        {
            System.out.println("Thread "+ id +" "+f+"/"+end);
            // pour chaque autre noeud
            for(int g = 0; g < node_data.length ; g++ )
            {
                if(f != g)
                {
                    f1 = node_data[f][0];
                    f2 = node_data[g][0];
                    f1p = node_data[f][1];
                    f2p = node_data[g][1];
                    /* generer les 4 matrices */
                    /* genere le cas f g */
                    simi.loadSimiGLo(f1,f2); /* LE POIDS C EST VRAIMENT DU CACA MVA MANGER TES MORTS */
                    arcs.add(new Arc(f,false,g,false,new Overlap(simi, false,f1,f2)));
                    arcs.add(new Arc(f,false,g,false,new Overlap(simi, true,f1,f2)));

                    /* genere le cas fp g */
                    simi.loadSimiGLo(f1p, f2);
                    arcs.add(new Arc(f,true,g,false,new Overlap(simi, false,f1p,f2)));
                    arcs.add(new Arc(f,true,g,false,new Overlap(simi, true,f1p,f2)));

                    /* genere le cas f gp */
                    simi.loadSimiGLo(f1, f2p);
                    arcs.add(new Arc(f,false,g,true,new Overlap(simi, false,f1,f2p)));
                    arcs.add(new Arc(f,false,g,true,new Overlap(simi, true,f1,f2p)));

                    /* genere le cas fp gp */
                    simi.loadSimiGLo(f1p,f2p);
                    arcs.add(new Arc(f,true,g,true,new Overlap(simi, false,f1p,f2p)));
                    arcs.add(new Arc(f,true,g,true,new Overlap(simi, true,f1p,f2p)));
                }
            }
        }
        this.finish = true;
        thread.stop();
    }

    public ArrayList<Arc> getArcs() 
    {
        return arcs;
    }
    
}
