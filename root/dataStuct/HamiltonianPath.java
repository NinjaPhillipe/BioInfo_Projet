package root.dataStuct;

/**
 * Classe representant un chemin hamiltonien
 */
public class HamiltonianPath 
{
    private Arc[] data;
    private int start;
    private int actual;

    /**
     * Initialise le chemin hamiltonien de n noeud
     */
    public HamiltonianPath(int size)
    {
        data = new Arc[size];
    }

    public void set_start(int i)
    {
        start  = i;
        actual = i;
    }

    /**
     * Place l'arc dans la liste 
     * @param a arc a placer
     */
    public void set(Arc a)
    {
        data[a.src] = a;
    }

    /**
     * Retourne l'arc suivant 
     * Si c'est la fin retourne null
     */
    public Arc get_next()
    {
        int res = actual;
        if(data[actual] != null)
        {
            actual = data[actual].dest;
            return data[res];
        }
        reset();
        return null;
    }

    /**
     * Remets le compteur sur le noeud de depart du chemin hamiltonien
     */
    public void reset(){ actual = start; }

}
