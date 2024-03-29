package root.dataStuct;

/**
 * Representation d'un arc du graphe
 */
public class Arc implements Comparable<Arc>
{
    public int src,dest,weight;     /* id de l'arc source et destination plus son poids */
    public boolean src_ci,dst_ci;   /* boolean si c est un complementaire inverser */
    public Overlap overlap;         /* Representation du chevauchement entre deux fragments */

    /**
     * Constructeur de l'arc 
     * @param src       id du noeud source 
     * @param src_ci    boolean qui indique si le noeud est un noeud source est complementaire inverser
     * @param dest      id du noeud destination
     * @param dst_ci    boolean qui indique si le noeud est un noeud destination est complementaire inverser
     * @param weight    poid de l'arc
     * @param overlap   chevauchement entre le fragment source et destination
     */
    public Arc(int src,boolean src_ci,int dest,boolean dst_ci,int weight,Overlap overlap)
    {
        this.src     = src;
        this.src_ci  = src_ci;
        this.dest    = dest;
        this.dst_ci  = dst_ci;
        this.weight  = weight;
        this.overlap = overlap;
    }

    @Override 
    public int compareTo(Arc arc)
    {
        /* reversed */
        return  this.weight - arc.weight;
    }

    public String toString()
    {
        return this.src+" -> "+this.dest+" inv("+this.src_ci+"|"+this.dst_ci+") weigth "+this.weight+" frag1over "+overlap.get_frag1_overlap_size()+" frag2over "+overlap.get_frag2_overlap_size();
    }
}
