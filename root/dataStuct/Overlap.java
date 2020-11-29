package root.dataStuct;

public class Overlap extends BitsData
{
    public static final byte B = (byte) 0b00;
    public static final byte G1 = (byte) 0b01;
    public static final byte G2 = (byte) 0b10;

    public Overlap(int[][] simi,boolean invert)
    {
        int max,y_cur,x_cur;
        if(invert)
        {
            y_cur = simi.length-1;
            max = simi[y_cur][0];
            x_cur = 0;
            for(int i = 1 ; i < simi[0].length ; i++ )
            {
                if (simi[y_cur][i] > max )
                {
                    max = simi[y_cur][i];
                    x_cur = i;
                }
            }
        }else
        {
            x_cur = simi[0].length-1;
            max = simi[0][x_cur];
            y_cur = 0;
            for(int i = 1 ; i < simi.length ; i++ )
            {
                if (simi[i][x_cur] > max )
                {
                    max = simi[i][x_cur];
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
        while (y_cur>0 && x_cur>0)
        {
            int haut = simi[y_cur-1][x_cur];
            int gauche = simi[y_cur][x_cur-1];
            int diag =  simi[y_cur-1][x_cur-1];
            // int maxTmp = max(haut, max(gauche, diag));
            
            if (diag >= 0)
            { // diag
                x_cur-=1;
                y_cur-=1;
                set(i,B);
            }
            else if (haut > gauche)
            { // haut
                y_cur-=1;
                set(i,G2);
                // on monte dans le tableau 
                // gap en x
            }
            else 
            { // gauche
                x_cur-=1;
                set(i,G1);
                // on va a gauche dans le tableau 
                // gap en y
            }
            i--;
        }
    }

    private void set_size_needed(int[][] simi,int y_cur,int x_cur)
    {
        while (y_cur>0 && x_cur>0)
        {
            int haut = simi[y_cur-1][x_cur];
            int gauche = simi[y_cur][x_cur-1];
            int diag =  simi[y_cur-1][x_cur-1];
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
