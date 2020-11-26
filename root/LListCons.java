package root;

public class LListCons
{
    private Node head;
    private Node tail;
    private int size;

    public LListCons(String str)
    {
        head = null;
        size = 0;

        // creer 
        head  = new Node(str.charAt(0));
        tail = head;
        for (int i = 0 ; i < str.length();i++)
        {
            this.add(str.charAt(i));
        }
    }

    public  Node getHead()
    {
        return this.head;
    }

    public Node getTail()
    {
        return this.tail;
    }

    public int getSize(){ return size; }

    public void add(char data)
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

    public class Node 
    {
        // ON SUPPOSE QUE LE TABLEAU PREND SUIT LA FORME A C G T
        public int [] acgt= {0,0,0,0};
        
        public Node next;

        /**
         * Cree un noeud et le place la donnee dans un dictionnaire
         */
        public Node (char data)
        {
            add_data(data);
            this.next = null;
        }

        public void add_data(char data)
        {
            switch(data)
            {   
                case 'a' :
                    acgt[0]++;
                    break;
                case 'c':
                    acgt[1]++;
                    break;
                case 'g':
                    acgt[2]++;
                    break;
                case 't':
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
     * Ajoute un mot a la comparaison
     * @param str       mot a ajouter
     * @param weight    taille de prefix==suffix
     * @return  lettre traitee
     */
    public String add(String str, int weight)
    {

        int pretrait = this.size-weight +1;  // fait comme un inge 
        System.out.println("LLS "+this.getSize());
        System.out.println("PRETRAIT SIZE "+pretrait);

        if( pretrait < 0)
        {
            // raise error
            System.exit(23);
        }

        String res = "";

        // ABCCD
        //   CCDF
        //   +  
        while(pretrait > 0)
        {
            // System.out.print("acgt: "+head.acgt[0]);
            // System.out.print(head.acgt[1]);
            // System.out.print(head.acgt[2]);
            // System.out.print(head.acgt[3]+"\n");


            // System.out.println(head);
            // get dic  pretraitre 
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
            char added = acgt.charAt(index);
            res = res + added;
            head = head.next;
            pretrait--;
            this.size --;
        }
        // partie 
        Node tmp = head;
        for(int i = 0 ; i < str.length() ; i++)
        {  
            if(tmp != null) // ajoute a la liste
            {
                head.add_data(str.charAt(i));
                tmp = tmp.next; // passe au noeud suivant
            }
            else 
            {   // cree un nouveau noeud
                this.add(str.charAt(i));
                // comme dernier noeud tmp reste null
            }
        }
        return res;
    }
}
