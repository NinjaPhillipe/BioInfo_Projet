package root;

public class Overlap 
{
    private byte[] data;
    private int size;

    // size of field in bits 
    private static final int bitsL = 2;
    // number of slot in one byte
    private static final int nslot = 8/bitsL;

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
        afin d eviter de devoir allouer plusieur fois la memoire
        */
        set_size_needed(simi,y_cur,x_cur);
        System.out.println("size "+size);
       
        // alloue la memoire dont ont a besoin
        if(this.size*bitsL%8 > 0 )
        {
            this.data = new byte[this.size*bitsL/8 +1];
        }
        else
        {
            this.data = new byte[this.size*bitsL/8];
        }

        // car on part de la fin
        int i = this.size-1; 
        while (y_cur>0 && x_cur>0)
        {
            int haut = simi[y_cur-1][x_cur];
            int gauche  = simi[y_cur][x_cur-1];
            int diag =  simi[y_cur-1][x_cur-1];
            // int maxTmp = max(haut, max(gauche, diag));
            
            if (diag >= 0)
            { // diag
                x_cur-=1;
                y_cur-=1;
                set(i, 0b00);
            }
            else if (haut > gauche)
            { // haut
                y_cur-=1;
                set(i, 0b01);
                // on monte dans le tableau 
                // gap en x
            }
            else 
            { // gauche
                x_cur-=1;
                set(i, 0b10);
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
            int gauche  = simi[y_cur][x_cur-1];
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

    // GET / SET
    /*
        i/nslot : l'octet dans lequel se trouve le champ
        (i%nslot)*bitsL : le nombre de bits dont ont doit decaler pour trouver le champ
    */

    /**
     * Mets le champ a la valeur donnee
     * @param i     le champ 
     * @param data  la valeur
     */
    private void set(int i,int data)
    {
        this.data[i/nslot] |= data << (i%nslot)*bitsL;
    }

    public byte get(int i)
    {
        return (byte) ( (this.data[i/nslot] >> (i%nslot)*bitsL ) & 0b11 );
    }


    public int size()
    {
        return this.size;
    }

    public String toString()
    {
        String res = "";
        for (int i = 0 ; i < this.size ; i++)
        {
            System.out.println("i : "+i);
            switch (this.get(i)) 
            {
                case 0b00:
                    res+= 'B';
                    break;
                case 0b01:
                    res+= '1';
                    break;
                case 0b10:
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
