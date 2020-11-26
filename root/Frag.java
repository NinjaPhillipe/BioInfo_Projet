package root;

import root.Utils;

public class Frag 
{
    private byte[] data;
    private int size;
    private static int idCount = 0;
    private int id = 0;

    // size of field in bits 
    private static final int bitsL = 2;
    // number of slot in one byte
    private static final int nslot = 8/bitsL;

    private Frag(char[] chars,int id)
    {
        this.size = chars.length;

        if(this.size*bitsL%8 > 0 )
        {
            this.data = new byte[this.size*bitsL/8 +1];
        }
        else
        {
            this.data = new byte[this.size*bitsL/8];
        }
        

        for (int i = 0 ; i < this.size ;i++)
        {
            switch (chars[i]) 
            {
                case 'A':
                    set(i,0b00);
                    break;
                case 'C':
                    set(i,0b01);
                    break;
                case 'G':
                    set(i,0b10);
                    break;
                case 'T':
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
