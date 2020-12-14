package root;

import root.dataStuct.Frag;

/**
 * Tableau de taille variable
 */
public class Simi 
{
    private int[][] a;
    private int[][] b;
    private int[][] c;
    private int[][] simi;
    private int end_i;
    private int end_j;

    private final int g = 1;
    private final int h = 2;

    /**
     * Alloue Une matrice carree de taille max
     * @param max
     */
    public Simi(int max)
    {
        simi= new int[max][max];
        a   = new int[max][max];
        b   = new int[max][max];
        c   = new int[max][max];
    }

    public static int pcost(byte a,byte b)
    {
        return (a == b) ? 1 : -1;
    }
    public static int max(int a, int b)
    {
        return (a > b) ? a : b;
    }
    public static int max(int a, int b,int c)
    {
        return a>b?(a>c?a:c):(b>c?b:c);
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

        /* les bornes du tableau */
        this.end_i = frag1.size()+1;
        this.end_j = frag2.size()+1;

        /* 
        Initialise a: 
            a_{0,0} = 0
            a_{i,0} = -inf
            a_{0,j} = -inf
        */
        a[0][0] = 0;
        for (int i = 1 ; i < end_i ; i ++)
            a[i][0] = Integer.MIN_VALUE;  
        for (int i = 1 ; i < end_j ; i ++)
            a[0][i] = Integer.MIN_VALUE;

        /*
        Initialise b:
            b_{i,0} = -inf
            b_{0,j} = -(h+g*i)
        */
        for (int i = 1 ; i < end_i ; i ++)
            b[i][0] = Integer.MIN_VALUE;  
        for (int i = 1 ; i < end_j ; i ++)
            b[0][i] = -(h+g*i);

        /*
        Initialise c:
            c_{i,0} = -(h+g*i)
            c_{0,j} = -inf
        */
        for (int i = 1 ; i < end_i ; i ++)
            c[i][0] = -(h+g*i);  
        for (int i = 1 ; i < end_j ; i ++)
            c[0][i] = Integer.MIN_VALUE;
        

        
        

        /* applique la formule vu en cours fonctions gap afine */
        for (int i = 1 ; i < end_i ; i++)
        {
            for (int j = 1 ; j < end_j ; j++)
            {
                a[i][j] = pcost(frag1.get(i-1), frag2.get(j-1)) + max(  a[i-1][j-1],
                                                                    b[i-1][j-1],
                                                                    c[i-1][j-1]);

                b[i][j] = max(  -(h+g)+ a[i][j-1],
                                -g+b[i][j-1],
                                -(h+g)+c[i][j-1]);
                
                c[i][j] = max(  -(h+g)+a[i-1][j],
                                -(h+g)+b[i-1][j],
                                -g+c[i-1][j]);

                simi[i][j] = max(a[i][j],b[i][j],c[i][j]);
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
                System.out.format("%3d",simi[i][j]);
            }
            System.out.print("\n");
        }
    }

    public int get_normal()
    {
        int max = simi[0][end_j-1];
        for(int i = 1 ; i < end_i ; i++ )
        {
            if (simi[i][end_j-1] > max )
                max = simi[i][end_j-1];
        }
        return max;
    }

    public int get_invert()
    {
        int max = simi[end_i-1][0];
        for(int i = 1 ; i < end_j ; i++ )
        {
            if (simi[end_i-1][i] > max )
                max = simi[end_i-1][i];
        }
        return max;
    }

    public int getEnd_i() { return end_i; }
    public int getEnd_j() { return end_j; }
    public int getData(int i,int j) 
    {
        if(i <= end_i && j<= end_j) 
            return simi[i][j];
        return 0; /* ERROR */
    }
}
