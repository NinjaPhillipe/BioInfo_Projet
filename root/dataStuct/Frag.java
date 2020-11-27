package root.dataStuct;

import root.Utils;

public class Frag extends BitsData
{
    private static int idCount = 0;
    private int id = 0;

    private Frag(char[] chars,int id)
    {
        this.size = chars.length;

        alloc_data();

        for (int i = 0 ; i < this.size ;i++)
        {
            switch (chars[i]) 
            {
                case 'a':
                    set(i,0b00);
                    break;
                case 'c':
                    set(i,0b01);
                    break;
                case 'g':
                    set(i,0b10);
                    break;
                case 't':
                    set(i,0b11);
                    break;
                default:
                    System.out.println("ERROR "+chars[i]+ " unknow");
                    break;
            }
        }
    }
    public Frag(char[] chars)
    {   
        this(chars,idCount);
        idCount++;
    }

    public static Frag[] init_f_and_fprime(char[] chars)
    {
        Frag[] res = new Frag[2];
        res[0] = new Frag(chars);
        Utils.compl_inverse(chars);
        res[1] = new Frag(chars,idCount);
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
                case 0b00:
                    res+= 'A';
                    break;
                case 0b01:
                    res+= 'C';
                    break;
                case 0b10:
                    res+= 'G';
                    break;
                case 0b11:
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
