package root;

import java.util.ArrayList;
import root.dataStuct.Frag;
import root.dataStuct.Overlap;
import root.dataStuct.Arc;

public class MultiThreadAlign implements Runnable
{
    private int id,start,end;
    private Thread thread;
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
        Frag f1,g1,fp,gp;
        // pour chaque noeud 
        System.out.println("Thread : "+id+" s->e "+start+" -> "+end);
        for(int f = start; f <= end ; f++ )
        {
            System.out.println("Thread "+ id +" "+f+"/"+end);
            // pour chaque autre noeud
            for(int g = f+1; g < node_data.length ; g++ )
            {
                f1 = node_data[f][0];
                g1 = node_data[g][0];
                fp = node_data[f][1];
                gp = node_data[g][1];
                /* generer les 4 matrices */
                /* genere le cas f g */
                simi.loadSimiGLo(f1,g1);
                arcs.add(new Arc(g,false,f,false,simi.get_normal(),new Overlap(simi,false)));   /*f->g*/ 
                arcs.add(new Arc(f,false,g,false,simi.get_invert(),new Overlap(simi,true)));    /*g->f*/

                /* genere le cas f gp */
                simi.loadSimiGLo(f1, gp);
                arcs.add(new Arc(f,false,g,true,simi.get_normal(),new Overlap(simi,false)));  /*f->gp*/ 
                arcs.add(new Arc(g,false,f,true,simi.get_invert(),new Overlap(simi,true)));   /*gp->f*/

                /* genere le cas fp g */
                simi.loadSimiGLo(fp, g1);
                arcs.add(new Arc(f,true,g,false,simi.get_normal(),new Overlap(simi,false)));  /*fp->g*/ 
                arcs.add(new Arc(g,true,f,false,simi.get_invert(),new Overlap(simi,true)));   /*g->fp*/

                /* genere le cas fp gp */
                simi.loadSimiGLo(fp,gp);
                arcs.add(new Arc(f,true,g,true,simi.get_normal(),new Overlap(simi,false)));   /*fp->gp*/ 
                arcs.add(new Arc(g,true,f,true,simi.get_invert(),new Overlap(simi,true)));    /*gp->fp*/
            }
        }
    }

    public ArrayList<Arc> getArcs() 
    {
        return arcs;
    }
    
}
