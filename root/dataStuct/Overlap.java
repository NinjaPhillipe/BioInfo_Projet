package root.dataStuct;

import root.Simi;
import root.Utils;

/**
 * Classe qui represente la partie de superposition pour l'alignement semiglobal
 */
public class Overlap extends BitsData
{
    public static final byte B  = (byte) 0b00;
    public static final byte G1 = (byte) 0b01; /* Gap sur le fragment 1 */
    public static final byte G2 = (byte) 0b10; /* Gap sur le fragment 2 */

    /* taille de l'overlap sur le fragment 1 */
    private int frag1_overlap_size = 0;
    private int frag2_overlap_size = 0;

    public Overlap(Simi simi,boolean invert)
    {
        /* place les curseurs dans la matrice */
        int max,y_cur,x_cur;
        if(!invert) /* f->g derniere ligne */
        {
            y_cur = simi.getEnd_i()-1;
            max = simi.getData(y_cur,0);
            x_cur = 0;
            for(int j = 1 ; j < simi.getEnd_j() ; j++ )
            {
                if (simi.getData(y_cur,j) > max )
                {
                    max = simi.getData(y_cur,j);
                    x_cur = j;
                }
            }
        }else/* g->f derniere colonne */
        {
            x_cur = simi.getEnd_j()-1;
            y_cur = 0;
            max = simi.getData(y_cur,x_cur);
            
            for(int i = 1 ; i < simi.getEnd_i() ; i++ )
            {
                if (simi.getData(i,x_cur) > max )
                {
                    max = simi.getData(i,x_cur);
                    y_cur = i;
                }
            }
        }
        
        /* 
        Iteration afin de determiner la taille de structure de donnees 
        afin d'eviter de devoir allouer plusieurs fois la memoire
        */
        set_size_needed(simi,y_cur,x_cur);
       
        /* alloue la memoire dont ont a besoin */
        alloc_data();

        /* car on part de la fin */
        int bitsCurs = this.size-1; 
        int haut,gauche,diag;
        while (y_cur>0 && x_cur>0)
        {
            haut = simi.getData(y_cur-1,x_cur);
            gauche = simi.getData(y_cur,x_cur-1);
            diag =  simi.getData(y_cur-1,x_cur-1);
            int maxTmp = Utils.max(haut, gauche, diag);
            
            if (diag == maxTmp)
            { // diag
                x_cur-=1;
                y_cur-=1;
                set(bitsCurs,B);
                this.frag1_overlap_size ++;
                this.frag2_overlap_size ++;
            }
            else if (haut > gauche)
            { // haut
                y_cur-=1;
                set(bitsCurs,G2);
                // on monte dans le tableau 
                // gap en x
                this.frag1_overlap_size ++; 
            }
            else 
            { // gauche
                x_cur-=1;
                set(bitsCurs,G1);
                // on va a gauche dans le tableau 
                // gap en y
                this.frag2_overlap_size ++;
            }
            bitsCurs--;
        }
    }

    /**
     * Fait un parcours du tableau afin de savoir la taille 
     * de la structure necessaire a allouer afin d'eviter 
     * les allocations multiple de memoire
     * @param simi
     * @param y_cur
     * @param x_cur
     */
    private void set_size_needed(Simi simi,int y_cur,int x_cur)
    {
        int haut,gauche,diag;
        while (y_cur>0 && x_cur>0)
        {
            haut = simi.getData(y_cur-1,x_cur);
            gauche = simi.getData(y_cur,x_cur-1);
            diag =  simi.getData(y_cur-1,x_cur-1);
            int maxTmp = Utils.max(haut,gauche, diag);
            
            if (diag == maxTmp)
            { // diag
                x_cur-=1;
                y_cur-=1;
                this.size++;
            }
            else if (haut > gauche)
            { // haut
                y_cur-=1;
                this.size++;
            }
            else 
            { // gauche
                x_cur-=1;
                this.size++;
            }
        }
    }

    public int get_frag1_overlap_size()
    {
        return this.frag1_overlap_size;
    }
    public int get_frag2_overlap_size()
    {
        return this.frag2_overlap_size;
    }

    public String toString()
    {
        String res = "";
        for (int i = 0 ; i < this.size ; i++)
        {
            System.out.println("i : "+i);
            switch (this.get(i)) 
            {
                case B:
                    res+= 'B';
                    break;
                case G1:
                    res+= '1';
                    break;
                case G2:
                    res+= '2';
                    break;
                default:
                    System.out.println("ERROR "+this.get(i));
                    break;
            }
        }
        return res+"\n "+frag1_overlap_size+" "+frag2_overlap_size+" "+size();
    }
}
