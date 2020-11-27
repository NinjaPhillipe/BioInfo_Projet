package root.dataStuct;

public class BitsData 
{
    protected byte[] data;
    protected int size;
    // size of field in bits 
    protected static final int bitsL = 2;
    // number of slot in one byte
    protected static final int nslot = 8/bitsL;

    /**
     * Alloue l'espace memoire necessaire en fonction de la taille 
     * renseignee dans la variable size
     */
    protected void alloc_data()
    {
        if(this.size*bitsL%8 > 0 )
        {
            this.data = new byte[this.size*bitsL/8 +1];
        }
        else
        {
            this.data = new byte[this.size*bitsL/8];
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
    protected void set(int i,int data)
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
}
