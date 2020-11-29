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

        public boolean gap = false;

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
        // prend le premier fragments inverser ou non
        Frag firstFrag = arcs.get(0).src_ci ? frags[arcs.get(0).src][1] : frags[arcs.get(0).src][0];

        // ajoute le fragment a la liste chainnee
        for (int i = 0 ; i < firstFrag.size() ; i++)
            this.add_to_end(firstFrag.get(i));

        // ajoute les autres fragments
        for(Graph.Arc a : arcs)
            this.add_frag(get_frag_dst(frags,a),a.overlap);
    }

    public Frag get_frag_dst(Frag[][] frags,Graph.Arc arc) // DOIT PAS ETRE LA
    {
        return arc.src_ci ? frags[arc.dest][1] : frags[arc.dest][0];
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

    public void insert(Node b,int i)
    {
        if(i==0)
        {
            b.next = head;
            head = b;
        }else
        {
            // non safe
            Node tmp = head;
            while(i > 1)
            {
                tmp = tmp.next;
                i--;
            }
            Node next = tmp.next;
            tmp.next = b;
            b.next = next; 
        }
        this.size++;
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
     * Ajoute un fragments dans la liste de consensus
     * @param frag
     * @param overlap
     */
    public void add_frag(Frag frag, Overlap overlap)
    {
        
        // pretraite la partie des octets avant l'overlap
        int pretrait = this.size - overlap.size();  

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
        
       
        System.out.println("add "+overlap.size()+ " "+size );
        // partie overlap 
        // marche pas besoin d ajouter un mecanisme de propagation des gap entre les paires de comparaison 
        {
            Node tmp = head;
            for(int i = 0 ; i < overlap.size() ; i++)
            {
                // System.out.println(""+i);
                if(!tmp.gap) // SERT A RIEN POUR L INSTANT
                {
                    switch (overlap.get(i))
                    {
                        case Overlap.B:
                            tmp.add_data(frag.get(i));
                            break;
                        case Overlap.G1: // gap dans le premier mot
                            insert(new Node(frag.get(i)), i);
                            tmp = tmp.next;
                            tmp.gap = true;
                            i++; // car on passe deux case lors d un gap
                            break;
                        case Overlap.G2: // gap dans le deuxieme mot
                            tmp.gap = true;
                            tmp = tmp.next;
                            tmp.add_data(frag.get(i));
                            i++; // car on passe deux case lors d un gap
                            break;
                    }
                }
                tmp = tmp.next;
            }
        }

        // partie de fin
        for(int i = overlap.size() ; i < frag.size() ; i++)
        {  
            this.add_to_end(frag.get(i));
        }
    }
}
