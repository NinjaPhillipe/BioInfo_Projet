package root.dataStuct;

import root.Simi;

/**
 * Classe qui represente la partie de superposition pour l'alignement semiglobal
 */
public class Overlap extends BitsData
{
    public static final byte B  = (byte) 0b00;
    public static final byte G1 = (byte) 0b01; /* Gap sur le fragment 1 */
    public static final byte G2 = (byte) 0b10; /* Gap sur le fragment 2 */

    public int weight = 0;

    /* taille de l'overlap sur le fragment 1 */
    private int frag1_overlap_size = 0;
    private int frag2_overlap_size = 0;

    public Overlap(Simi simi,boolean invert,Frag f1,Frag f2)
    {
        int max,y_cur,x_cur;
        if(invert)
        {
            y_cur = simi.getEnd_i()-1;
            max = simi.getData(y_cur,0);
            x_cur = 0;
            for(int i = 1 ; i < simi.getEnd_j() ; i++ )
            {
                if (simi.getData(y_cur,i) > max )
                {
                    max = simi.getData(y_cur,i);
                    x_cur = i;
                }
            }
        }else
        {
            x_cur = simi.getEnd_j()-1;
            max = simi.getData(0,x_cur);
            y_cur = 0;
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
        afin d eviter de devoir allouer plusieurs fois la memoire
        */
        set_size_needed(simi,y_cur,x_cur);
       
        // alloue la memoire dont ont a besoin
        alloc_data();

        // car on part de la fin
        int i = this.size-1; 
        int haut,gauche,diag;
        while (y_cur>0 && x_cur>0)
        {
            haut = simi.getData(y_cur-1,x_cur);
            gauche = simi.getData(y_cur,x_cur-1);
            diag =  simi.getData(y_cur-1,x_cur-1);
            // int maxTmp = max(haut, max(gauche, diag));
            
            if (diag >= 0)
            { // diag
                x_cur-=1;
                y_cur-=1;
                set(i,B);
                this.frag1_overlap_size ++;
                this.frag2_overlap_size ++;

                /* si egale weight +1 sinon weight -1 */
                if(f1.get(y_cur) == f2.get(x_cur) )
                {
                    weight++;
                }else
                {
                    weight--;
                }
            }
            else if (haut > gauche)
            { // haut
                y_cur-=1;
                set(i,G2);
                // on monte dans le tableau 
                // gap en x
                this.frag1_overlap_size ++; 
                weight-=2;
            }
            else 
            { // gauche
                x_cur-=1;
                set(i,G1);
                // on va a gauche dans le tableau 
                // gap en y
                this.frag2_overlap_size ++;
                weight-=2;
            }
            i--;
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
            // int maxTmp = max(haut, max(gauche, diag));
            
            if (diag >= 0)
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

    public int get_value()
    {
        int res = 0;
        for (int i = 0 ; i < this.size ; i++)
        {
            switch (this.get(i)) 
            {
                case B:
                    res+= 1;
                    break;
                case G2:
                    res+= 1;
                    break;
            }
        }
        return res;
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
        return res;
    }
}
