package root;

import java.util.ArrayList;

import root.dataStuct.Frag;
import root.dataStuct.Graph;
import root.dataStuct.Overlap;

public class LListCons
{
    private Node head;
    private Node tail;
    private int size;

    public String resS = "";

    private class Node 
    {
        // ON SUPPOSE QUE LE TABLEAU PREND SUIT LA FORME A C G T
        public int [] acgt= {0,0,0,0};
        
        public Node next;

        /**
         * Cree un noeud et le place la donnee dans un dictionnaire
         */
        public Node (byte data)
        {
            add_data(data);
            this.next = null;
        }

        public void add_data(byte data)
        {
            switch(data)
            {   
                case Frag.A :
                    acgt[0]++;
                    break;
                case Frag.C:
                    acgt[1]++;
                    break;
                case Frag.G:
                    acgt[2]++;
                    break;
                case Frag.T:
                    acgt[3]++;
                    break;
            }
        }

        public boolean hasNext()
        {
            return this.next != null;
        }
        public Node getNext()
        {
            return this.next;
        }
    }

    /**
     * 
     * @param frags 
     * @param arcs chemin hamiltonian triee selon l'ordre des arcs
     */
    public LListCons(Frag[][] frags,ArrayList<Graph.Arc> arcs)
    {
        head = null;
        size = 0;

        // si inverser
        Frag firstFrag = get_frag(frags, arcs.get(0));

        // creer 
        head  = new Node(firstFrag.get(0));
        tail = head;
        for (int i = 0 ; i < firstFrag.size() ; i++)
        {
            this.add_to_end(firstFrag.get(i));
        }

        for(Graph.Arc a : arcs)
        {
            this.add(get_frag(frags,a),a.overlap);
        }
    }

    public Frag get_frag(Frag[][] frags,Graph.Arc arc) // DOIT PAS ETRE LA
    {
        return arc.src_ci ? frags[arc.src][1] : frags[arc.src][0];
    }

    public  Node getHead()
    {
        return this.head;
    }

    public Node getTail()
    {
        return this.tail;
    }

    public void removeHead()
    {
        head = head.next;
        this.size --;
    }

    public int getSize(){ return size; }

    /**
     * Ajoute un noeud a la fin de la liste
     * @param data 
     */
    public void add_to_end(byte data)
    {
        if (this.head == null)
        {
            this.head = new Node (data);
            this.tail = head;
            this.size++;
        }
        else
        {
            tail.next = new Node(data);
            tail = tail.next;
            this.size++;
        }
    }

    /**
     * Ajoute un mot a la comparaison
     * @param str       mot a ajouter
     * @param weight    taille de prefix==suffix
     * @return  lettre traitee
     */
    public void add(Frag frag, Overlap overlap)
    {
        // pretraite la partie des byte avant l'overlap
        int pretrait = this.size - overlap.size() + 1;  

        if( pretrait < 0)
        {
            // raise error
            System.exit(23);
        }

        // ABCCD
        //   CCDF
        //   +  
        while(pretrait > 0)
        {
            // get Array pretraitre 
            int [] tmp_acgt = head.acgt;
            String acgt = "ACGT";

            int max = 0;
            int index = 0;
            for (int i = 0 ; i < tmp_acgt.length; i++)
            {
                if (tmp_acgt[i] > max)
                {
                    max = tmp_acgt[i];
                    index = i;
                }
            }

            resS += acgt.charAt(index);

            removeHead();
            pretrait--;
        }
        
        Node tmp = head;

        // partie overlap 
        // marche pas besoin d ajouter un mecanisme de propagation des gap entre les paires de comparaison 
        for(int i = 0 ; i < overlap.size() ; i++)
        {
            tmp.add_data(frag.get(i));
            tmp = tmp.next;
        }

        // partie de fin
        for(int i = overlap.size() ; i < frag.size() ; i++)
        {  
            this.add_to_end(frag.get(i));
        }
    }
}
