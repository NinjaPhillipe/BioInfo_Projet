package root.dataStuct;

/**
 *  Structure qui premet de representer des variables plus petite que l'octet
 *  Ici partage l'octet en champ de 2 bits
 */
public class BitsData 
{
    protected byte[] data;
    protected int size;

    /* taille en bits d'un champ */
    protected static final int bitsL = 2;
    /* nombre de champ par octet */
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
    protected void set(int i,byte data)
    {
        this.data[i/nslot] |= data << (i%nslot)*bitsL;
    }

    public byte get(int i)
    {
        return (byte) ( (this.data[i/nslot] >> (i%nslot)*bitsL ) & 0b11 );
    }
    
    public int size() { return this.size; }
}
