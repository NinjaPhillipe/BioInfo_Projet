package root;

import root.dataStuct.Frag;

/**
 * Tableau de taille variable
 */
public class Simi 
{
    private int[][] data;
    private int end_i;
    private int end_j;

    /**
     * Alloue Une matrice carree de taille max
     * @param max
     */
    public Simi(int max)
    {
        data = new int[max][max];
    }

    public static int pcost(byte a,byte b)
    {
        return (a == b) ? 1 : -1;
    }
    public static int max(int a, int b)
    {
        return (a > b) ? a : b;
    }


    /**
     * Calcul le tableau semiglobal
     */
    public void loadSimiGLo(Frag frag1, Frag frag2)
    {
        //      f  r  a  g  2
        //  f
        //  r
        //  a
        //  g
        //  1

        /* mets les bornes du tableau */
        this.end_i = frag1.size()+1;
        this.end_j = frag2.size()+1;


        /* initialise la premiere ligne et la premiere colonne a 0 */
        data[0][0] = 0;
        for (int i = 1 ; i < end_i ; i ++)
            data[i][0] = 0;
        for (int i = 1 ; i < end_j ; i ++)
            data[0][i] = 0;


        /* applique la formule vu en cours */
        for (int i = 1 ; i < end_i ; i++)
        {
            for (int j = 1 ; j < end_j ; j++)
            {
                data[i][j] = max(
                                data[i][j-1]-2,                                    
                                max( /* CHANGER EN UN SEUL MAX */
                                    data[i-1][j-1]+pcost(frag1.get(i-1),frag2.get(j-1)),
                                    data[i-1][j]-2
                                    )     
                                );
            }
        }
        // printSimi(ok);
    }
    
    public void printSimi()
    {
        for (int i = 0 ; i < end_i ; i++)
        {
            for (int j = 0 ; j < end_j ; j++)
            {
                System.out.format("%3d",data[i][j]);
            }
            System.out.print("\n");
        }
    }

    public int get_invert()
    {
        int max = data[0][end_j-1];
        for(int i = 1 ; i < end_i ; i++ )
        {
            if (data[i][end_j-1] > max )
                max = data[i][end_j-1];
        }
        return max;
    }

    public int get_normal()
    {
        int max = data[end_i-1][0];
        for(int i = 1 ; i < end_j ; i++ )
        {
            if (data[end_i-1][i] > max )
                max = data[end_i-1][i];
        }
        return max;
    }

    public int getEnd_i() { return end_i; }
    public int getEnd_j() { return end_j; }
    public int getData(int i,int j) 
    {
        return data[i][j];
    }
}