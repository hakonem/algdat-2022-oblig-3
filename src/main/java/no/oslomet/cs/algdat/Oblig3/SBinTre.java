package no.oslomet.cs.algdat.Oblig3;


import javax.sound.midi.SysexMessage;
import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    //Programkode 5.2.3 a) fra kompendiet
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");
    
        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel
    
        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }
    
        // p er nå null, dvs. ute av treet, q er den siste vi passerte
    
        p = new Node<>(verdi, q);         // oppretter en ny node
    
        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q
    
        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }
    
    //Basert på programkode 5.2 8 d) fra kompendiet
    public boolean fjern(T verdi) {
        if (verdi == null) return false;  // treet har ingen nullverdier
    
        Node<T> p = rot, q = null;   // q skal være forelder til p
    
        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,p.verdi);      // sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
            else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi
        
        if (p.venstre == null && p.høyre == null) {
            if (p == rot) rot = null;
            else if (p == q.venstre) q.venstre = null;
            else q.høyre = null;
        }
    
        else if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            if (p == q.venstre) {
                q.venstre = b;
                b.forelder = q;
            }
            else {
                q.høyre = b;
                b.forelder = q;
            }
        }
        else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null)
            {
                s = r;    // s er forelder til r
                r = r.venstre;
            }
        
            p.verdi = r.verdi;   // kopierer verdien i r til p
        
            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }
    
        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        Node<T> p = rot;                // p starter i roten
        int antall = 0;                 // initialiserer teller
    
        while (p != null) {             // fortsetter til p er ute av treet
            int cmp = comp.compare(verdi, p.verdi);         // sammenligner søkeverdien med verdien i noden
            if (cmp < 0) p = p.venstre;         // hvis søkverdien er mindre enn verdien i noden, flytter til venstre barn
            else {
                if (cmp == 0) antall++;         // øker telleren hvis vi finner søkeverdien OG
                p = p.høyre;                    // flytter til høyre barn
            }
        }
        return antall;                          // returnerer antall
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        while (p != null) {                             // fortsetter til p er ute av treet
            if (p.venstre != null) p = p.venstre;       // flytter p først til venstre
            else if (p.høyre != null) p = p.høyre;      // hvis venstre barn finnes ikke, flytter til høyre
            else return p;                              // bladnode
        }
        return p;                                       // returnerer p
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> q = p.forelder;                          // q er forelder til p
        while (p != null) {                             // fortsetter til p er ute av treet
            if (q == null) return null;                 // returnerer null hvis p er rotnode
            if (p == q.høyre) {                         // hvis p er høyre barn, er neste node forelder
                p = q;
                break;
            }
            else {
                if (q.høyre == null) p = q;             // hvis p er venstre barn og alenebarn, er neste node forelder
                else p = førstePostorden(q.høyre);      // ellers er neste node den første noden i subtreet med q.høyre som rot
                break;
            }
        }
        return p;                                       // returnerer p
    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
