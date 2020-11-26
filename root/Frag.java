package root;

public class Frag 
{
    private byte[] data;
    private int size;

    // size of field in bits 
    private static final int bitsL = 2;
    // number of slot in one byte
    private static final int nslot = 8/bitsL;
    public Frag(char[] chars)
    {    
        
        this.size = chars.length;
        this.data = new byte[this.size*bitsL/8];

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

    private int get(int i)
    {
        return (this.data[i/nslot] >> (i%nslot)*bitsL ) & 0b11;
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
