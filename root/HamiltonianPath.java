package root;

import root.dataStuct.Arc;

public class HamiltonianPath 
{
    private Arc[] data;

    private int start;

    private int counter = 0;
    private int actual;

    public HamiltonianPath(int size)
    {
        data = new Arc[size];
    }

    public void set_start(int i)
    {
        start  = i;
        actual = i;
    }

    public void set(Arc a)
    {
        data[a.src] = a;
    }

    public Arc get_next()
    {
        System.out.println(counter);
        counter++;
        int res = actual;
        if(data[actual] != null)
        {
            actual = data[actual].dest;
            return data[res];
        }
        return null;
    }

    public void reset(){ actual = start; }

}
