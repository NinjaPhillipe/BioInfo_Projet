package root.dataStuct;

/**
 *  Structure qui permet de representer un fragment en memoire
 */
public class Frag extends BitsData
{
    /* declaration des convention pour ACGT */
    public static final byte A = (byte) 0b00;
    public static final byte C = (byte) 0b01;
    public static final byte G = (byte) 0b10;
    public static final byte T = (byte) 0b11;
    
    private static int idCount = 0; /* idnetifiant compteur */
    private int id = 0;             /* identifiant */

    /**
     * Constructeur qui initialise le fragment a partir d'une chaine de caractere
     * @param chars Liste de caractere representant le fragment
     */
    public Frag(char[] chars)
    {   
        this(chars,idCount);
        idCount++;
    }
    /**
     * Constructeur prive qui permet de forcer l'id a un id different du compteur
     * 
     * Utiliser pour pouvoir mettre le meme identifiant au fragment et son complementaire inverser
     * 
     * @param chars Liste de caractere representant le fragment
     * @param id    Identifiant du fragment
     */
    private Frag(char[] chars,int id)
    {
        this.size = chars.length;

        alloc_data();

        for (int i = 0 ; i < this.size ;i++)
        {
            switch (chars[i]) 
            {
                case 'a':
                    set(i,A);
                    break;
                case 'c':
                    set(i,C);
                    break;
                case 'g':
                    set(i,G);
                    break;
                case 't':
                    set(i,T);
                    break;
                default:
                    System.out.println("ERROR "+chars[i]+ " unknow");
                    break;
            }
        }
    }

    /**
     * Cree le complementaire inverser d'un fragment
     */
    private Frag(Frag f)
    {
        this.size = f.size;
        this.alloc_data();

        /* COMPL_INVERSE */
        for(int i = 0 ; i < f.size/2 ; i++)
        {
            this.set(i,compl(f.get(f.size-1-i)));
            this.set(this.size-1-i,compl(f.get(i)));
        }

        /* Si est impair*/
        if(f.size%2 > 0)
        {
            /* ajouter le complementaire inverser du milieu */
            int i = (f.size/2);
            this.set(i,compl(f.get(i)));
        }

    }

    /**
     * Retourne le complementaire inverser d'un octet
     * @param b l'octet a complementer inverser 
     * @return  l'octet complementer inverser 
     */
    private byte compl(byte b)
    {
        switch(b)
        {
            case A:
                return T;
            case C:
                return G;
            case G:
                return C;
            case T:
                return A;
        }
        System.out.println("Impossible un byte n'est pas de la convention acgt");
        System.exit(1);
        return 0;
    }

    /**
     * Methode qui initialise le fragment et son complementaire inverser a partir
     * d'une liste de caractere.
     * @param chars liste de caractere
     * @return  Une liste de taille 2 contenant le fragment et son complementaire.
     */
    public static Frag[] init_f_and_fprime(char[] chars)
    {
        Frag[] res = new Frag[2];
        res[0] = new Frag(chars);
        res[1] = new Frag(res[0]);
        return res;
    }

    // GET / SET
    /*
        i/nslot : l'octet dans lequel se trouve le champ
        (i%nslot)*bitsL : le nombre de bits dont ont doit decaler pour trouver le champ
    */

    public int get_id()
    {
        return this.id;
    }
    public static int get_idCount()
    {
        return idCount;
    }

    public String toString()
    {
        String res = "";
        for (int i = 0 ; i < this.size ; i++)
        {
            switch (this.get(i)) 
            {
                case A:
                    res+= 'a';
                    break;
                case C:
                    res+= 'c';
                    break;
                case G:
                    res+= 'g';
                    break;
                case T:
                    res+= 't';
                    break;
                default:
                    System.out.println("ERROR "+this.get(i));
                    break;
            }
        }
        return res;
    }
}
