package root;

import root.dataStuct.Frag;
import root.dataStuct.HamiltonianPath;
import root.dataStuct.Overlap;
import root.dataStuct.Arc;

/**
 * Classe qui s'occupe d'effectuer le vote de consensus
 */
public class LListCons
{
    private Node head;
    private Node tail;
    private int size;

    public String resS = "";

    private class Node 
    {
        /* ON SUPPOSE QUE LE TABLEAU PREND LA FORME ACGT */
        public int [] acgt= {0,0,0,0};
        
        public Node next;

        public boolean gapG2;

        /**
         * Cree un noeud et le place dans le tableau
         */
        public Node (byte data)
        {
            add_data(data);
            this.next = null;
            this.gapG2 = false;
        }

        /**
         * Rajoute actg dans le tableau
         * @param data
         */
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
    }

    /**
     * Effectue le vote de consensus 
     * @param frags matrice ou sont stocker tout les fragments
     * @param arcs chemin hamiltonian triee selon l'ordre des arcs
     */
    public LListCons(Frag[][] frags,HamiltonianPath path)
    {
        /* prend le premier fragments inverser ou non */
        Arc arc = path.get_next();
        Frag firstFrag = arc.src_ci ? frags[arc.src][1] : frags[arc.src][0];

        /* ajoute le fragment a la liste chainne */
        for (int i = 0 ; i < firstFrag.size() ; i++)
            this.add_to_end(firstFrag.get(i));

        /* ajoute les autres fragments */
        while(arc != null)
        {
            this.add_frag(get_frag_dst(frags,arc),arc.overlap);
            arc = path.get_next();
        }
    }

    /**
     * Retourne le fragment destination de l'arc en fonction de si il est complementer et inverser ou non
     * @param frags matrice des fragments
     * @param arc arc 
     * @return fragment destination de l'arc
     */
    public Frag get_frag_dst(Frag[][] frags,Arc arc)
    {
        return arc.dst_ci ? frags[arc.dest][1] : frags[arc.dest][0];
    }

    public  Node getHead()
    {
        return this.head;
    }

    public Node getTail()
    {
        return this.tail;
    }

    /**
     * Retire le premier element de la linked list
     */
    public void removeHead()
    {
        if(this.head != null)
        {
            this.head = head.next;
            this.size --;
        }
    }

    /**
     * Ajoute l'element b a la position i 
     * @param b
     * @param i
     */
    public void insert(Node b,int i)
    {
        if( i > this.size ) return;

        if(i==0) /** si on ajoute en premiere position */
        {
            b.next = this.head;
            this.head = b;
        }else
        {
            Node tmp = this.head; /* prend le premier element */
            while(i > 1)
            {
                tmp = tmp.next;
                i--;
            }
            Node follow = tmp.next;
            tmp.next = b;
            b.next = follow; 
        }
        this.size++;
    }

    public int getSize(){ return size; }

    /**
     * Ajoute un noeud a la fin de la liste
     * @param data donnee avec laquelle on initialise le noeud
     */
    public void add_to_end(byte data)
    {
        if (this.head == null)
        {
            this.head = new Node (data);
            this.tail = head;
            this.size = 1;
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
     * 
     * FRAG2 est ajouter
     * @param frag fragments a ajouter 
     * @param overlap superposition entre le fragment dans la liste et le fragment que l'on ajoute
     */
    public void add_frag(Frag frag, Overlap overlap)
    {
        /* pretraite la partie des octets avant l'overlap */
        {
            String acgt = "acgt";
            for(int pretrait = this.size - overlap.get_frag1_overlap_size(); pretrait > 0;pretrait--)
            {
                /* cherche la lettre la plus frequente */
                int max = head.acgt[0];
                int index = 0;
                for (int i = 1 ; i < head.acgt.length; i++)
                {
                    if (head.acgt[i] > max)
                    {
                        max = head.acgt[i];
                        index = i;
                    }
                }

                resS += acgt.charAt(index);

                removeHead();
            }
        }
        
        /* partie overlap */
        {
            Node tmp = this.head;

            /* Indice des fragments au debut de l'overlap */
            int frag2_id = 0;

            /* Pour chaque element de l'overlap */
            for(int i = 0 ; i < overlap.size() ; i++)
            {   
                switch (overlap.get(i))
                {
                    case Overlap.B:/* cas superposition */
                        if(!tmp.gapG2 )/* si le gap est dans le mots qu on veut ajouter. */
                        {
                            tmp.add_data(frag.get(frag2_id));

                            frag2_id ++; /* on passe un element de frag2 */
                            tmp = tmp.next;
                        }
                        break;
                    case Overlap.G1: /* gap dans le premier mot */
                        if(!tmp.gapG2 )/* si le gap est dans le mots qu on veut ajouter. */
                        {
                            /* on ajoute un noeud avec la donnee */
                            insert(new Node(frag.get(frag2_id)), i); /* QUESTION ? : l'insertion doit se faire juste avant le noeud actuel */
                            /* On ajoute avant le noeud courant donc pas de passage au suivant */
                            frag2_id ++; /* on passe un element de frag2 */
                        }else
                        { /* cas ou gap plus gap  pour pas doublon*/ 
                            tmp.add_data(frag.get(frag2_id));
                            frag2_id ++; /* on passe un element de frag2 */
                        }
                        break;
                    case Overlap.G2: /* gap dans le deuxieme mot */
                        if(!tmp.gapG2 )/* si le gap est dans le mots qu on veut ajouter. */
                        {
                            /* On ne doit rien rajouter */
                            // tmp.add_data(frag.get(frag2_id));
                        }
                        tmp.gapG2 = true;
                        break;
                }
            }
        }

        /* partie de fin */
        for(int i = overlap.get_frag2_overlap_size() ; i < frag.size() ; i++)
            this.add_to_end(frag.get(i));
    }
}
