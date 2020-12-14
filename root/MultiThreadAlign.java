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
        Frag f1,g1,fp,gp;
        // pour chaque noeud 
        System.out.println("Thread : "+id+" s->e "+start+" -> "+end);
        for(int f = start; f <= end ; f++ )
        {
            System.out.println("Thread "+ id +" "+f+"/"+end);
            // pour chaque autre noeud
            for(int g = start+1; g < node_data.length ; g++ )
            {
                if(f != g)
                {
                    f1 = node_data[f][0];
                    g1 = node_data[g][0];
                    fp = node_data[f][1];
                    gp = node_data[g][1];
                    /* generer les 4 matrices */
                    /* genere le cas f g */
                    simi.loadSimiGLo(f1,g1); /* LE POIDS C EST VRAIMENT DU CACA MVA MANGER TES MORTS */
                    arcs.add(new Arc(f,false,g,false,simi.get_normal(),new Overlap(simi,false,f1,g1))); /*f->g*/ 
                    arcs.add(new Arc(g,false,f,false,simi.get_invert(),new Overlap(simi,true,f1,g1)));  /*g->f*/

                    /* genere le cas f gp */
                    simi.loadSimiGLo(f1, gp);
                    arcs.add(new Arc(f,false,g,true,simi.get_normal(),new Overlap(simi,false,f1,gp)));  /*f->gp*/ 
                    arcs.add(new Arc(g,false,f,true,simi.get_invert(),new Overlap(simi,true,f1,gp)));   /*gp->f*/

                    /* genere le cas fp g */
                    simi.loadSimiGLo(fp, g1);
                    arcs.add(new Arc(f,true,g,false,simi.get_normal(),new Overlap(simi,false,fp,g1)));  /*fp->g*/ 
                    arcs.add(new Arc(g,true,f,false,simi.get_invert(),new Overlap(simi,true,fp,g1)));   /*g->fp*/

                    /* genere le cas fp gp */
                    simi.loadSimiGLo(fp,gp);
                    arcs.add(new Arc(f,true,g,true,simi.get_normal(),new Overlap(simi,false,fp,gp)));   /*fp->gp*/ 
                    arcs.add(new Arc(g,true,f,true,simi.get_invert(),new Overlap(simi,true,fp,gp)));    /*gp->fp*/
                }
            }
        }
        thread.stop();
    }

    public ArrayList<Arc> getArcs() 
    {
        return arcs;
    }
    
}
