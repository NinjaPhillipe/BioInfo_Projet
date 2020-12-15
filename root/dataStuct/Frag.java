package root.dataStuct;

import root.Utils;

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
     * Methode qui initialise le fragment et son complementaire inverser a partir
     * d'une liste de caractere.
     * @param chars liste de caractere
     * @return  Une liste de taille 2 contenant le fragment et son complementaire.
     */
    public static Frag[] init_f_and_fprime(char[] chars)
    {
        Frag[] res = new Frag[2];
        res[0] = new Frag(chars);
        char[] invert = Utils.compl_inverse(chars);
        res[1] = new Frag(invert,idCount);
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
                    res+= 'A';
                    break;
                case C:
                    res+= 'C';
                    break;
                case G:
                    res+= 'G';
                    break;
                case T:
                    res+= 'T';
                    break;
                default:
                    System.out.println("ERROR "+this.get(i));
                    break;
            }
        }
        return res;
    }
}
