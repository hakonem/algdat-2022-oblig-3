# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Emma Louise Håkonsen, S362073, s362073@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så gikk jeg frem ved å kopiere Programkode 5.2 3 a) fra kompendiet, men i tillegg la jeg inn en ekstra 
parameter 'q' in linje 101, slik at hver node blir opprettet med en forelder-referanse.

I oppgave 2 så brukte jeg en teller til å lagre antall treff og en komparator til å flytte rundt treet. Vi starter i 
roten og hvis søkeverdien er mindre enn verdien i roten, flytter vi til venstre; hvis søkeverdien er lik eller større enn
roten, flytter vi til høyre. Hver gang vi finner søkeverdien i treet økes telleren med 1.

I oppgave 3 må førstePostorden(p) finne bladnoden som er lengst nede til venstre i treet. Dette gjorde jeg ved å begynne 
i rotnoden og, i en while-løkke, flytte p til venstrebarn så lenge det er mulig og til høyrebarn ellers. Når vi kommer til 
en bladnode, returneres p. Metoden nestePostorden(p) finner den neste noden i postorden, avhengig av om p er høyrebarn, 
venstre- og alenebarn eller venstre barn der forelderen også ha høyrebarn. I siste tilfellet brukte jeg førstePostorden
for å finne neste node i postorden i forelderens høyre gren.

Jeg brukte begge metodene fra oppgave 3 for å løse oppgave 4. I den public postorden-metoden startet jeg i rotnoden og
kaller så på førstePostorden(p) for å finne den første noden i postorden, og deretter oppgave.utførOppgave(p.verdi) for å
utføre oppgaven. I en while-løkke kaller jeg på nestePostorden(p) og oppgave.utførOppgave(p.verdi), som utfører samme 
oppgave helt frem til vi kommer tilbake til roten. postordenRecursive(p, oppgave) er en private metode som kaller en public  
metode rekursivt på rotens venstre og høyre grener.

Jeg gjorde ikke oppgave 5.

I oppgave 6 kopierte jeg Programkode 5.2 8 d) fra kompendiet for å lage fjern(T verdi), men jeg måtte håndtere tilfelle
1 (når p er bladnode) og tilfelle 2 (når p har ett barn) hver for seg for å få satt riktige forelder-pekere. Jeg brukte
løsningsforslaget til oppgave 4 i Avsnitt 5.2.8 i kompendiet som utgangspunktet for fjernAlle(T verdi), men gjorde en del 
endringer, blant annet å legge inn håndtering av tomme trær og trær som bare inneholder en rotnode. Metoden nullstill()
lagde jeg ved å benytte meg av førstePostorden(p) og nestePostorden(p) for å traversere treet i postorden, og så kaller 
jeg fjern(p.verdi) på hver bladnode.
Jeg ble dessverre ikke helt ferdig med denne oppgaven, og den feiler fortsatt test 6u.

