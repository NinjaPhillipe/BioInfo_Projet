package root.dataStuct;

public class Arc implements Comparable<Arc>
{
    public int src,dest;
    public boolean src_ci,dst_ci; /* boolean si c est un complementaire inverser */
    public Overlap overlap;
    public Arc(int src,boolean src_ci,int dest,boolean dst_ci,Overlap overlap)
    {
        this.src     = src;
        this.src_ci  = src_ci;
        this.dst_ci  = dst_ci;
        this.dest    = dest;
        this.overlap = overlap;
    }

    @Override 
    public int compareTo(Arc arc)
    {
        /* reversed */
        return arc.overlap.weight - this.overlap.weight;
    }

    public String toString()
    {
        return this.src+" -> "+this.dest+" inv("+this.src_ci+"|"+this.dst_ci+") weigth "+this.overlap.weight+" frag1over "+overlap.get_frag1_overlap_size()+" frag2over "+overlap.get_frag2_overlap_size();
    }
}